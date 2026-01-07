package com.example.juegomemoria;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Memoria memoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<ImageButton> listaBotones = new ArrayList<>();

        int[] rows = {
                R.id.linearLayout1,
                R.id.linearLayout2,
                R.id.linearLayout3,
                R.id.linearLayout4
        };

        for (int layoutId : rows) {

            LinearLayout row = findViewById(layoutId);

            for (int i = 0; i < row.getChildCount(); i++) {
                if (row.getChildAt(i) instanceof ImageButton) {
                    listaBotones.add((ImageButton) row.getChildAt(i));
                }
            }
        }

        ImageButton[] botones = listaBotones.toArray(new ImageButton[0]);

        int[] imagenes = {
                R.drawable.coconut,
                R.drawable.pumpkin,
                R.drawable.tomato,
                R.drawable.watermelon
        };

        memoria = new Memoria(botones, imagenes, R.drawable.carta_detras);
        memoria.initGame();

        for (int i = 0; i < botones.length; i++) {

            final int pos = i;

            botones[i].setOnClickListener(v -> {
                if (memoria.isFinished()) {
                    Toast.makeText(this, "¡Has ganado!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int imagen = memoria.imgArray[memoria.posArray[pos]];
                pulsaGirar(botones[pos], imagen);
                memoria.DiscoverCard(pos);
            });
        }

        Button reiniciar = findViewById(R.id.button);

        reiniciar.setOnClickListener(v -> {
            memoria.initGame();
            Toast.makeText(this, "Nuevo juego", Toast.LENGTH_SHORT).show();
        });
    }

    public void pulsaGirar(ImageButton imageButton, int nuevaImagen){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageButton, "rotationY", 0f, 90f);
        animator1.setDuration(300);
        animator1.setInterpolator(new LinearInterpolator());

        animator1.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                imageButton.setImageResource(nuevaImagen);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageButton, "rotationY", 90f, 180f);
                animator2.setDuration(300);
                animator2.setInterpolator(new LinearInterpolator());
                animator2.start();
            }
        });
        animator1.start();
    }
}