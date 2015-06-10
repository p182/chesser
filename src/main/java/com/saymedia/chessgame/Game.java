package com.saymedia.chessgame;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Game extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void backButton(View v){
        startActivity(new Intent("first.activity"));
    }


    private int dp(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public RelativeLayout.LayoutParams Place(){
        RelativeLayout.LayoutParams A2 = new RelativeLayout.LayoutParams(100,100);
        A2.setMargins(2, 0, dp(2000), 0);

        RelativeLayout.LayoutParams A1 = new RelativeLayout.LayoutParams(0,0);
        A1.setMargins(24, 415, 0, 132);

        return A2;
    }

    public void PiecesOnClick(View v){
        ImageView wking = (ImageView) findViewById(R.id.wking);
        RelativeLayout.LayoutParams A2 = new RelativeLayout.LayoutParams(135,135);
        A2.setMargins(700, 500, 0, 0);

        wking.setLayoutParams(A2);
    }
}
