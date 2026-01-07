package com.example.juegomemoria;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class Memoria {
    ImageButton[] ibArray;
    int[] imgArray;
    int backImg;
    int[] posArray;

    private int firstPos = -1;
    private int secondPos = -1;
    private boolean isFirst = true;

    private boolean lock = false;

    public Memoria(ImageButton[] arrayImageButton, int[] identificadores, int backIdentificador){
        this.ibArray = arrayImageButton;
        this.imgArray = identificadores;
        this.backImg = backIdentificador;
        this.posArray = new int[ibArray.length];
    }

    public void initGame(){
        for (int i = 0; i < ibArray.length; i++){
            ibArray[i].setAlpha(1f);
        }

        int total = ibArray.length;
        int numPairs = total / 2;

        ArrayList<Integer> temp = new ArrayList<>();

        for (int i = 0; i < numPairs; i++){
            temp.add(i);
            temp.add(i);
        }

        java.util.Collections.shuffle(temp);

        for (int i = 0; i < total; i++){
            posArray[i] = temp.get(i);
        }

        CleanImages();
    }

    public void CleanImages(){
        for (int i = 0; i < ibArray.length; i++) {
            if (posArray[i] == -1) {
                ibArray[i].setAlpha(0f);
            } else {
                ibArray[i].setImageResource(backImg);
            }
        }
    }

    public void DiscoverCard(int pos){
        if (lock){
            return;
        }
        if (posArray[pos] == -1){
            return;
        }
        if (pos == firstPos){
            return;
        }

        if (isFirst){
            firstPos = pos;
            isFirst = false;
        } else {
            secondPos = pos;
            isFirst = true;

            lock = true;

            new CountDownTimer(1000,1000){
                @Override
                public void onTick(long milisUntilFinished){

                }

                @Override
                public void onFinish(){
                    ibArray[pos].setImageResource(imgArray [posArray[pos]]);
                    checkPair();
                }
            }.start();
        }
    }

    public void checkPair(){
        if (posArray[firstPos] == posArray[secondPos]){
            posArray[firstPos] = -1;
            posArray[secondPos] = -1;
        }
        CleanImages();
        lock = false;
    }

    public boolean isFinished(){
        for (int v: posArray){
            if (v != -1){
                return false;
            }
        }
        return true;
    }
}