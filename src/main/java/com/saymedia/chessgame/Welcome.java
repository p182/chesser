package com.saymedia.chessgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        System.out.println("onCreate completed");
    }

    public void buttonOnClick(View v){ startActivity(new Intent("chess.game")); }



    public void buttonOnClick2(View v){ startActivity(new Intent("chess.pairedDevices")); }


    public void changeColor(View v){
        ImageView image = (ImageView)v;
        if(Game.color == 1){
            Game.color=-1;
            image.setImageResource(R.drawable.bpawn);
        }
        else{
            Game.color=1;
            image.setImageResource(R.drawable.wpawn);
        }
    }
}
