package com.saymedia.chessgame;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;


public class Game extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setupPieces();

    }

    public int pressedId;

    public void select(){
        int j;
        int i;
        int l=1;
        for(j=1; j<9; j++){
            for(i=1; i<8; i++){
                    creatSelected("selected", i, j, l*100);
                l++;
            }
            creatSelected("selected", i, j, l*100);
            l++;
        }

        creatSelected("selected", 1, 1, 65*100);
        ImageView v = (ImageView) findViewById(pressedId);
        ViewGroup.LayoutParams rlp = v.getLayoutParams();
        ImageView s = (ImageView) findViewById(65*100);
        s.setLayoutParams(rlp);
    }

    public void setupPieces(){
        creatPiece("wrook", 1, 1, 1);
        creatPiece("wknight", 2, 1, 2);
        creatPiece("wbishop", 3, 1, 3);
        creatPiece("wqueen", 4, 1, 4);
        creatPiece("wking", 5, 1, 5);
        creatPiece("wbishop", 6, 1, 6);
        creatPiece("wknight", 7, 1, 7);
        creatPiece("wrook", 8, 1, 8);
        for(int i=1; i<9; i++) {
            creatPiece("wpawn", i, 2, (i+8));
        }

        creatPiece("brook", 1, 8, 17);
        creatPiece("bknight", 2, 8, 18);
        creatPiece("bbishop", 3, 8, 19);
        creatPiece("bqueen", 4, 8, 20);
        creatPiece("bking", 5, 8, 21);
        creatPiece("bbishop", 6, 8, 22);
        creatPiece("bknight", 7, 8, 23);
        creatPiece("brook", 8, 8, 24);
        for(int i=1; i<9; i++) {
            creatPiece("bpawn", i, 7, (i+24));
        }
    }

    public void creatSelected(String pname, int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        ImageView image = new ImageView(getApplicationContext());

        switch (pname){
            case "selected": image.setImageResource(R.drawable.selected);break;
            case "nonselected": image.setImageResource(R.drawable.nonselected); break;
        }

        image.setLayoutParams(place(x, y));
        image.setVisibility(View.VISIBLE);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedOnClick(v);
            }
        });

        image.setId(id);
        rl.addView(image);
    }

    public void creatPiece(String pname, int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        ImageView image = new ImageView(getApplicationContext());

        switch (pname){
            case "bbishop": image.setImageResource(R.drawable.bbishop); break;
            case "bking": image.setImageResource(R.drawable.bking); break;
            case "bknight": image.setImageResource(R.drawable.bknight); break;
            case "bpawn": image.setImageResource(R.drawable.bpawn); break;
            case "bqueen": image.setImageResource(R.drawable.bqueen); break;
            case "brook": image.setImageResource(R.drawable.brook); break;
            case "wbishop": image.setImageResource(R.drawable.wbishop); break;
            case "wking": image.setImageResource(R.drawable.wking); break;
            case "wknight": image.setImageResource(R.drawable.wknight); break;
            case "wpawn": image.setImageResource(R.drawable.wpawn); break;
            case "wqueen": image.setImageResource(R.drawable.wqueen); break;
            case "wrook": image.setImageResource(R.drawable.wrook); break;
        }

        image.setLayoutParams(place(x, y));
        image.setVisibility(View.VISIBLE);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                piecesOnClick(v);
            }
        });

        image.bringToFront();
        image.setId(id);
        rl.addView(image);
    }

    public void backButton(View v){
        startActivity(new Intent("first.activity"));
    }

    private int dp(double dp) {

        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public RelativeLayout.LayoutParams place(int x , int y){

        double X = 24;
        double Y = 25;
        double d = 36.9;

        RelativeLayout.LayoutParams L = new RelativeLayout.LayoutParams(dp(38),dp(38));
        L.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        L.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        switch (x){
            case 1: L.leftMargin = dp(X) + 0*dp(d); break;
            case 2: L.leftMargin = dp(X) + 1*dp(d); break;
            case 3: L.leftMargin = dp(X) + 2*dp(d); break;
            case 4: L.leftMargin = dp(X) + 3*dp(d); break;
            case 5: L.leftMargin = dp(X) + 4*dp(d); break;
            case 6: L.leftMargin = dp(X) + 5*dp(d); break;
            case 7: L.leftMargin = dp(X) + 6*dp(d); break;
            case 8: L.leftMargin = dp(X) + 7*dp(d); break;

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
        pressedId = v.getId();
        select();
    }


    public void selectedOnClick(View s){
        ImageView v = (ImageView) findViewById(pressedId);

        ViewGroup.LayoutParams rlp = s.getLayoutParams();

        v.setLayoutParams(rlp);

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        int id = 0;
        for(id=100; id<6600; id+=100){;
            rl.removeView(findViewById(id));
        }
    }
}
