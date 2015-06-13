package com.saymedia.chessgame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class Game extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        creatPieces();

    }

    public void creatPieces(){

//        System.out.println("creatPieces v: " + v);

//        System.out.println("findViewById: " + findViewById(R.id.fragment));
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
//        do{rl = (RelativeLayout)findViewById(R.id.layout1); }
//        while(rl == null);
        System.out.println("rl: " + rl);
        ImageView wking = new ImageView(getApplicationContext());
        wking.setImageResource(R.drawable.wking);
        wking.setLayoutParams(place("A", 1));
        System.out.println("wking: " + wking);
        wking.setVisibility(View.VISIBLE);
        rl.addView(wking);


    }

    public void backButton(View v){
        startActivity(new Intent("first.activity"));
    }

    private int dp(double dp) {

        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public RelativeLayout.LayoutParams place(String x , int y){

        double X = 24;
        double Y = 25;
        double d = 36.9;

        RelativeLayout.LayoutParams L = new RelativeLayout.LayoutParams(dp(38),dp(38));
        L.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        L.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        switch (x){
            case "A": L.leftMargin = dp(X) + 0*dp(d); break;
            case "B": L.leftMargin = dp(X) + 1*dp(d); break;
            case "C": L.leftMargin = dp(X) + 2*dp(d); break;
            case "D": L.leftMargin = dp(X) + 3*dp(d); break;
            case "E": L.leftMargin = dp(X) + 4*dp(d); break;
            case "F": L.leftMargin = dp(X) + 5*dp(d); break;
            case "G": L.leftMargin = dp(X) + 6*dp(d); break;
            case "H": L.leftMargin = dp(X) + 7*dp(d); break;

        }

        switch (y){
            case 1: L.bottomMargin = dp(Y) + 0*dp(d); break;
            case 2: L.bottomMargin = dp(Y) + 1*dp(d); break;
            case 3: L.bottomMargin = dp(Y) + 2*dp(d); break;
            case 4: L.bottomMargin = dp(Y) + 3*dp(d); break;
            case 5: L.bottomMargin = dp(Y) + 4*dp(d); break;
            case 6: L.bottomMargin = dp(Y) + 5*dp(d); break;
            case 7: L.bottomMargin = dp(Y) + 6*dp(d); break;
            case 8: L.bottomMargin = dp(Y) + 7*dp(d); break;

        }

        return L;
    }

    public void piecesOnClick(View v){
        v.setLayoutParams(place("H" , 3));
    }
}
