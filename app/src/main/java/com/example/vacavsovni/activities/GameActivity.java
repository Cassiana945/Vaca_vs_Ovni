package com.example.vacavsovni.activities;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vacavsovni.R;
import com.example.vacavsovni.view.FundoView;
import com.example.vacavsovni.view.GameView;

public class GameActivity extends AppCompatActivity {

    private FundoView fundoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameView gameView = new GameView(this);
        setContentView(R.layout.activity_game);

        RelativeLayout relativeLayout = findViewById(R.id.game_layout);

        fundoView = new FundoView(this);
        fundoView.resume();

        relativeLayout.addView(fundoView);
        relativeLayout.addView(gameView);

        RelativeLayout.LayoutParams fundoParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        fundoView.setLayoutParams(fundoParams);

        RelativeLayout.LayoutParams gameViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        gameView.setLayoutParams(gameViewParams);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (fundoView != null) {
            fundoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (fundoView != null) {
            fundoView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fundoView != null) {
            fundoView.pause();
        }
    }
}
