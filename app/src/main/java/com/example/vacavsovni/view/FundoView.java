package com.example.vacavsovni.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.vacavsovni.R;
import com.example.vacavsovni.model.Fundo;


public class FundoView extends SurfaceView implements Runnable {

    Fundo bg_fazenda;
    Thread renderThread;
    SurfaceHolder holder;
    volatile boolean running;

    public FundoView(Context context){
        super(context);
        this.holder = getHolder();
    }


    private Bitmap scaleCenterCrop(Bitmap source, int screenWidth, int screenHeight) {

        float scale = Math.max(
                (float) screenWidth / source.getWidth(),
                (float) screenHeight / source.getHeight()
        );

        int scaledWidth = Math.round(source.getWidth() * scale);
        int scaledHeight = Math.round(source.getHeight() * scale);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, true);

        return scaledBitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Bitmap original = BitmapFactory.decodeResource(getResources(), R.drawable.farm);
        Bitmap scaled = scaleCenterCrop(original, w, h);
        bg_fazenda = new Fundo(scaled);
    }

    public void run(){

        long startTime = System.nanoTime();
        while (running){

            if (bg_fazenda == null) {
                continue;
            }

            if(!holder.getSurface().isValid()) continue;
            Canvas canvas = null;

            try {
                canvas = holder.lockCanvas(null);
                synchronized ( bg_fazenda){
                    if ( bg_fazenda.getX()+  bg_fazenda.getBitmap().getWidth() >= 0 || ( bg_fazenda.getX() >= 0 &&  bg_fazenda.getX() <= getWidth()))
                        canvas.drawBitmap( bg_fazenda.getBitmap(),  bg_fazenda.getX(), 0, null);

                    if ( bg_fazenda.getX()+ bg_fazenda.getBitmap().getWidth()+ bg_fazenda.getBitmap().getWidth() >= 0 || ( bg_fazenda.getX()+ bg_fazenda.getBitmap().getWidth() >= 0 &&  bg_fazenda.getX() <= getWidth()))
                        canvas.drawBitmap( bg_fazenda.getBitmap(), ( bg_fazenda.getX() +  bg_fazenda.getBitmap().getWidth()),0, null);


                    if (getDeltaTime(startTime) > 0.1) {
                        bg_fazenda.update();
                        startTime = System.nanoTime();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if(canvas != null) holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    private float getDeltaTime(long startTime) {
        return (System.nanoTime() - startTime) / 100000000f;
    }

    public void pause(){
        running = false;
        while (true){
            try {
                renderThread.join();
                break;
            } catch (InterruptedException e){

            }
        }
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public Fundo getFundoModel(){ return  bg_fazenda;}

}
