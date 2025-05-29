package com.example.vacavsovni.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.vacavsovni.activities.GameView;
import com.example.vacavsovni.R;

import java.util.Random;

public class Ovni {
    private final Bitmap[] ovni = new Bitmap[3];
    private int ovniFrame = 0;
    private int ovniX, ovniY, ovniVelocity;
    private final Random random;

    public Ovni(Context context) {
        ovni[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovni1);
        ovni[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovni2);
        ovni[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ovni3);
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
        ovniVelocity = 35 + random.nextInt(16);
    }
}
