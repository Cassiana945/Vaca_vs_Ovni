package com.example.vacavsovni.model;

import android.graphics.Bitmap;

public class Fundo {

    private int velocidade = -12;
    private int x;
    private Bitmap bitmap;

    public Fundo(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void update(){
        x += velocidade;
        if(x <= - bitmap.getWidth()){
            x = 0;
        }
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = - velocidade;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public int getX() {
        return x;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
