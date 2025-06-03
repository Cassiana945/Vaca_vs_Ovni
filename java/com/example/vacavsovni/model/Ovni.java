package com.example.vacavsovni.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.vacavsovni.view.GameView;
import com.example.vacavsovni.R;

import java.util.Random;

public class Ovni {
    private final Bitmap[] ovni = new Bitmap[3];
    private int ovniFrame = 0;
    private int ovniX, ovniY, ovniVelocity;
    private final Random random;

    public Ovni(Context context) {
        Bitmap original1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovni1);
        Bitmap original2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovni2);
        Bitmap original3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovni3);


        int novaLargura = GameView.getDWidth() / 6;
        int novaAltura1 = (original1.getHeight() * novaLargura) / original1.getWidth();
        int novaAltura2 = (original2.getHeight() * novaLargura) / original2.getWidth();
        int novaAltura3 = (original3.getHeight() * novaLargura) / original3.getWidth();

        ovni[0] = Bitmap.createScaledBitmap(original1, novaLargura, novaAltura1, true);
        ovni[1] = Bitmap.createScaledBitmap(original2, novaLargura, novaAltura2, true);
        ovni[2] = Bitmap.createScaledBitmap(original3, novaLargura, novaAltura3, true);

        random = new Random();
        resetPosition();
    }

    public Bitmap getOvni(int ovniFrame) {
        return ovni[ovniFrame];
    }

    public int getOvniWidth() {
        return ovni[0].getWidth();
    }

    public int getOvniHeight() {
        return ovni[0].getHeight();
    }

    public int getOvniX() {
        return ovniX;
    }

    public void setOvniX(int ovniX) {
        this.ovniX = ovniX;
    }

    public int getOvniY() {
        return ovniY;
    }

    public void setOvniY(int ovniY) {
        this.ovniY = ovniY;
    }

    public int getOvniVelocity() {
        return ovniVelocity;
    }

    public int getOvniFrame() {
        return ovniFrame;
    }

    public void setOvniFrame(int frame) {
        this.ovniFrame = frame;
    }
    public void resetPosition() {
        ovniX = random.nextInt(GameView.getDWidth() - getOvniWidth());
        ovniY = -200 + random.nextInt(600) * -1;
        ovniVelocity = 10 + random.nextInt(6);
    }
}

