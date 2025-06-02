package com.example.vacavsovni.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacavsovni.view.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}

