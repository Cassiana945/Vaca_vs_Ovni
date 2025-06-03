package com.example.vacavsovni.view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.vacavsovni.R;
import com.example.vacavsovni.activities.GameOverActivity;
import com.example.vacavsovni.database.PontosDatabase;
import com.example.vacavsovni.model.Ovni;

import java.util.ArrayList;
import java.util.Random;

import android.os.Handler;


public class GameView extends View {

    Bitmap chao, vaca;
    AnimationDrawable anim_fundo;
    Rect recAnim_Fundo, rectChao;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    float TEXT_SIZE = 120;
    int pontos = 0;
    private static int dWidth, dHeight;
    Random random;
    long startTime;
    float vacaX, vacaY;
    float oldX;
    float oldVacaX;
    ArrayList<Ovni> ovnis;
    MediaPlayer playerPerdeu, playerMusica;
    PontosDatabase bdPontos;

    public GameView(Context context) {
        super(context);
        this.context = context;

        this.startTime = System.currentTimeMillis();

        bdPontos = new PontosDatabase(context);

        setBackgroundResource(R.drawable.anim_background);
        anim_fundo = (AnimationDrawable) getBackground();
        anim_fundo.start();

        chao = BitmapFactory.decodeResource(getResources(), R.drawable.chao);

        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        vaca = BitmapFactory.decodeResource(getResources(), R.drawable.vaca);
        int novaLargura = dWidth /3;
        int novaAltura = (vaca.getHeight() * novaLargura) / vaca.getWidth(); // mantém proporção
        vaca = Bitmap.createScaledBitmap(vaca, novaLargura, novaAltura, true);


        recAnim_Fundo = new Rect(0, 0, dWidth, dHeight);
        rectChao = new Rect(0, dHeight - chao.getHeight(), dWidth, dHeight);


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };


        textPaint.setColor(Color.rgb(254, 241, 137));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.jersey_regular));


        playerMusica = MediaPlayer.create(context, R.raw.musica_tema);
        playerPerdeu = MediaPlayer.create(context, R.raw.som_perdeu);
        playerMusica.start();

        random = new Random();


        vacaX = dWidth / 2 - vaca.getWidth() / 2;
        vacaY = dHeight - chao.getHeight() - vaca.getHeight();


        ovnis = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Ovni ovni = new Ovni(context);
            ovnis.add(ovni);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);


        anim_fundo.draw(canvas);
        canvas.drawBitmap(chao, null, rectChao, null);
        canvas.drawBitmap(vaca, vacaX, vacaY, null);


        for (int i = 0; i < ovnis.size(); i++) {
            Ovni ovniAtual = ovnis.get(i);
            canvas.drawBitmap(ovniAtual.getOvni(ovniAtual.getOvniFrame()), ovniAtual.getOvniX(), ovniAtual.getOvniY(), null);

            ovniAtual.setOvniFrame(ovniAtual.getOvniFrame() + 1);
            if (ovniAtual.getOvniFrame() > 2) {
                ovniAtual.setOvniFrame(0);
            }

            ovniAtual.setOvniY(ovniAtual.getOvniY() + ovniAtual.getOvniVelocity());
            if (ovniAtual.getOvniY() + ovniAtual.getOvniHeight() >= dHeight - chao.getHeight()) {
                pontos += 10;
                ovnis.get(i).resetPosition();
            }
        }

        if (System.currentTimeMillis() - startTime > 2000) {
            for (int i = 0; i < ovnis.size(); i++) {
                Ovni ovniAtual = ovnis.get(i);
                if (ovniAtual.getOvniX() + ovniAtual.getOvniWidth() >= vacaX
                        && ovniAtual.getOvniX() <= vacaX + vaca.getWidth()
                        && ovniAtual.getOvniY() + ovniAtual.getOvniWidth() >= vacaY
                        && ovniAtual.getOvniY() + ovniAtual.getOvniWidth() <= vacaY + vaca.getHeight()) {


                    playerMusica.stop();
                    playerPerdeu.start();
                    bdPontos.addPontuacao(pontos);
                    ovnis.get(i).resetPosition();


                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra("pontos", pontos);
                    context.startActivity(intent);
                   ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                   ((Activity) context).finish();
                }
            }
        }


        canvas.drawText(" " + pontos, 20, TEXT_SIZE, textPaint);

        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();


        if (touchY >= vacaY) {
            int action = event.getAction();


            if (action == MotionEvent.ACTION_DOWN) {
                oldX = event.getX();
            }


            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newVacaX = oldVacaX - shift;

                if (newVacaX <= 0)
                    vacaX = 0;
                else if (newVacaX >= dWidth - vaca.getWidth()) {
                    vacaX = dWidth - vaca.getWidth();
                } else {
                    vacaX = newVacaX;
                }
            }
        }
        return true;
    }

    public static int getDWidth() {
        return dWidth;
    }

    public static int getDHeight() {
        return dHeight;
    }




}
