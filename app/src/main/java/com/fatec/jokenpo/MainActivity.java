package com.fatec.jokenpo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPadrao;
    private ImageView imgTesoura;
    private ImageView imgPedra;
    private ImageView imgPapel;
    private TextView txtResultado;
    private TextView txtPlacarUsuario;
    private TextView txtPlacarPC;
    private MaterialSwitch switchMelhorDe3;
    private Button btnLimpar;

    private int placarUsuario = 0;
    private int placarPC = 0;
    
    private int vitoriasUsuarioM3 = 0;
    private int vitoriasPCM3 = 0;
    private boolean jogoFinalizadoM3 = false;

    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Encontrando os componentes do XML
        imgPadrao = findViewById(R.id.imgPadrao);
        imgPapel = findViewById(R.id.imgPapel);
        imgPedra = findViewById(R.id.imgPedra);
        imgTesoura = findViewById(R.id.imgTesoura);
        txtResultado = findViewById(R.id.txtResultado);
        txtPlacarUsuario = findViewById(R.id.txtPlacarUsuario);
        txtPlacarPC = findViewById(R.id.txtPlacarPC);
        switchMelhorDe3 = findViewById(R.id.switchMelhorDe3);
        btnLimpar = findViewById(R.id.btnLimpar);

        // Clique no PAPEL
        imgPapel.setOnClickListener(view -> opcSelecionada("papel"));

        // Clique na PEDRA
        imgPedra.setOnClickListener(view -> opcSelecionada("pedra"));

        // Clique na TESOURA
        imgTesoura.setOnClickListener(view -> opcSelecionada("tesoura"));

        // Clique no Botão Limpar
        btnLimpar.setOnClickListener(view -> limparJogo());
        
        // Listener para o switch Melhor de 3
        switchMelhorDe3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            limparJogo();
            if (isChecked) {
                Toast.makeText(this, "Modo Melhor de 3 Ativado!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void opcSelecionada(String opcaoSelecionada) {
        if (jogoFinalizadoM3) {
            Toast.makeText(this, "A partida acabou. Clique em LIMPAR para jogar novamente.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Opções possíveis
        String[] opcoes = {"pedra", "papel", "tesoura"};

        // Computador escolhe aleatoriamente
        int numero = random.nextInt(3);
        String opcPC = opcoes[numero];

        // Mostra a escolha do computador
        switch (opcPC) {
            case "pedra":
                imgPadrao.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imgPadrao.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imgPadrao.setImageResource(R.drawable.tesoura);
                break;
        }

        // Lógica do jogo
        if ((opcPC.equals("tesoura") && opcaoSelecionada.equals("papel")) ||
            (opcPC.equals("papel") && opcaoSelecionada.equals("pedra")) ||
            (opcPC.equals("pedra") && opcaoSelecionada.equals("tesoura"))) {
            
            txtResultado.setText("Você PERDEU a rodada!");
            placarPC++;
            if (switchMelhorDe3.isChecked()) vitoriasPCM3++;

        } else if ((opcPC.equals("papel") && opcaoSelecionada.equals("tesoura")) ||
                   (opcPC.equals("pedra") && opcaoSelecionada.equals("papel")) ||
                   (opcPC.equals("tesoura") && opcaoSelecionada.equals("pedra"))) {
            
            txtResultado.setText("Você GANHOU a rodada!");
            placarUsuario++;
            if (switchMelhorDe3.isChecked()) vitoriasUsuarioM3++;

        } else {
            txtResultado.setText("EMPATE na rodada!");
        }

        atualizarPlacar();
        verificarVencedorMelhorDe3();
    }

    private void atualizarPlacar() {
        txtPlacarUsuario.setText("Usuário: " + placarUsuario);
        txtPlacarPC.setText("PC: " + placarPC);
    }

    private void verificarVencedorMelhorDe3() {
        if (switchMelhorDe3.isChecked()) {
            if (vitoriasUsuarioM3 == 2) {
                txtResultado.setText("PARABÉNS! VOCÊ VENCEU A PARTIDA!");
                jogoFinalizadoM3 = true;
            } else if (vitoriasPCM3 == 2) {
                txtResultado.setText("QUE PENA! O PC VENCEU A PARTIDA!");
                jogoFinalizadoM3 = true;
            }
        }
    }

    private void limparJogo() {
        // Limpar visual
        imgPadrao.setImageResource(R.drawable.padrao);
        txtResultado.setText("Escolha uma opção abaixo");
        
        // Resetar o estado do Melhor de 3
        vitoriasUsuarioM3 = 0;
        vitoriasPCM3 = 0;
        jogoFinalizadoM3 = false;

        // Se quiser resetar o placar geral ao clicar em Limpar:
        // placarUsuario = 0;
        // placarPC = 0;
        // atualizarPlacar();
    }
}
