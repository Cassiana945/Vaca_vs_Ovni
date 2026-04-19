package com.example.vacavsovni.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vacavsovni.R;
import com.example.vacavsovni.database.PontosDatabase;

public class GameOverActivity extends AppCompatActivity {

    TextView txtPontos, txtMaiorPonto;
    ImageView btnNovoJogo, btnSair;
    ImageView imgTrofeu;
    MediaPlayer playerGanhou, playerBotaoClick;
    PontosDatabase bdPontos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        txtPontos = findViewById(R.id.pontos);
        txtMaiorPonto = findViewById(R.id.maior_ponto);
        imgTrofeu = findViewById(R.id.trofeu);
        btnNovoJogo = findViewById(R.id.btnNovoJogo);
        btnSair = findViewById(R.id.btnSair);
        playerGanhou = MediaPlayer.create(this, R.raw.som_ganhou);
        playerBotaoClick = MediaPlayer.create(this, R.raw.som_clicar_botao);


        Intent i = getIntent();
        int pontos = i.getIntExtra("pontos", 0);
        txtPontos.setText(String.valueOf(pontos));

        bdPontos = new PontosDatabase(this);

        int maiorPontuacao = bdPontos.maiorPontuacao();

        if (pontos >=  maiorPontuacao) {
            imgTrofeu.setVisibility(View.VISIBLE);
            playerGanhou.start();
        }
        txtMaiorPonto.setText(String.valueOf(maiorPontuacao));



        btnNovoJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerBotaoClick.start();
                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerBotaoClick.start();
                finishAffinity();
            }
        });
    }

}
