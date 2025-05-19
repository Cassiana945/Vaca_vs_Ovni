package com.example.vacavsovni.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vacavsovni.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton botaoJogar;
    private TextView tituloJogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoJogar = findViewById(R.id.botaoJogar);
        tituloJogo = findViewById(R.id.tituloJogo);

        Typeface fonte = Typeface.createFromAsset(getAssets(), "font/kenney_blocks.ttf");
        tituloJogo.setTypeface(fonte);

        botaoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }
}
