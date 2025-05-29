package com.example.vacavsovni.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacavsovni.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton botaoJogar;
    private TextView tituloJogo;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        botaoJogar = findViewById(R.id.botaoJogar);
        tituloJogo = findViewById(R.id.tituloJogo);
        player = MediaPlayer.create(MainActivity.this, R.raw.som_clicar_botao);

        Typeface fonte = Typeface.createFromAsset(getAssets(), "font/kenney_blocks.ttf");
        tituloJogo.setTypeface(fonte);

        botaoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameView.class);
                startActivity(intent);
                player.start();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
