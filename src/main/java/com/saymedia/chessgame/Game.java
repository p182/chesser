package com.saymedia.chessgame;

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

        setupPieces();

    }

    public static Piece pressed;

    public static Piece wr1 , br1;
    public static Piece wr2 , br2;
    public static Piece wn1 , bn1;
    public static Piece wn2 , bn2;
    public static Piece wb1 , bb1;
    public static Piece wb2 , bb2;
    public static Piece wk , bk;
    public static Piece wq , bq;
    public static Piece wp1 , bp1;
    public static Piece wp2 , bp2;
    public static Piece wp3 , bp3;
    public static Piece wp4 , bp4;
    public static Piece wp5 , bp5;
    public static Piece wp6 , bp6;
    public static Piece wp7 , bp7;
    public static Piece wp8 , bp8;


    public void selectSquares(String c, Piece p){

/*
        if(c.equals("else")) {
            int j;
            int i;
            int l = 1;
            for (j = 1; j < 9; j++) {
                for (i = 1; i < 8; i++) {
                    creatSelectedSquare(i, j, l * 100);
                    l++;
                }
                creatSelectedSquare(i, j, l * 100);
                l++;
            }

            creatSelectedSquare(p.x, p.y, 65 * 100);
/*
            ImageView v = (ImageView) findViewById(pressedId);
            ViewGroup.LayoutParams rlp = v.getLayoutParams();
            ImageView s = (ImageView) findViewById(65 * 100);
            s.setLayoutParams(rlp);
*/
//        }


            String[] array = c.split(",");

            for(int i=0; i<array.length; i++){
                String pars = array[i];
                int x = pars.charAt(0) - 48;
                int y = pars.charAt(1) - 48;
                creatSelectedSquare(x, y, (i+1) * 100);
            }

/*
            int i=1;
            for(String pars : array) {
                int x = pars.charAt(0) - 48;
                int y = pars.charAt(1) - 48;
                creatSelectedSquare(x, y, i * 100);
                i++;
            }
*/

//            System.out.println(x); System.out.println(y);

            creatSelectedSquare(p.x, p.y, 65 * 100);
            creatSelectedSquare(p.x, p.y, 66 * 100);
            p.bringToFront();


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

    public void creatSelectedSquare(int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        SelectedSquare image = new SelectedSquare(getApplicationContext(), x, y, id);
/*
        ImageView image = new ImageView(getApplicationContext());

        switch (pname){
            case "selected": image.setImageResource(R.drawable.selected);break;
            case "nonselected": image.setImageResource(R.drawable.nonselected); break;
        }

        image.setLayoutParams(getPlaceParams(x, y));
        image.setVisibility(View.VISIBLE);
        image.setId(id);
*/
        image.setLayoutParams(getPlaceParams(x, y));

        image.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        selectedOnClick(v);
                    }
                }
        );

        rl.addView(image);
    }

    public void creatPiece(String pname, int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        Piece p = new Piece(getApplicationContext(), x, y, pname,id);

/*
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

        image.setVisibility(View.VISIBLE);

        image.bringToFront();
        image.setId(id);
*/

        p.setOnClickListener(new Piece.OnClickListener()  {
            public void onClick(View v){
                piecesOnClick(v);
            }
        });
        p.setLayoutParams(getPlaceParams(x, y));
        rl.addView(p);

        switch (id){
            case 1: wr1 = p; break;
            case 2: wn1 = p; break;
            case 3: wb1 = p; break;
            case 4: wq = p; break;
            case 5: wk = p; break;
            case 6: wb2 = p; break;
            case 7: wn2 = p; break;
            case 8: wr2 = p; break;
            case 9: wp1 = p; break;
            case 10: wp2 = p; break;
            case 11: wp3 = p; break;
            case 12: wp4 = p; break;
            case 13: wp5 = p; break;
            case 14: wp6 = p; break;
            case 15: wp7 = p; break;
            case 16: wp8 = p; break;


            case 17: br1 = p; break;
            case 18: bn1 = p; break;
            case 19: bb1 = p; break;
            case 20: bq = p; break;
            case 21: bk = p; break;
            case 22: bb2 = p; break;
            case 23: bn2 = p; break;
            case 24: br2 = p; break;
            case 25: bp1 = p; break;
            case 26: bp2 = p; break;
            case 27: bp3 = p; break;
            case 28: bp4 = p; break;
            case 29: bp5 = p; break;
            case 30: bp6 = p; break;
            case 31: bp7 = p; break;
            case 32: bp8 = p; break;
        }

    }

    public void backButton(View v){
        startActivity(new Intent("first.activity"));
    }

    private int dp(double dp) {

        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return (int)(dp * density);
    }

    public RelativeLayout.LayoutParams getPlaceParams(int x, int y){

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

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        for(int id=100; id<6700; id+=100){
            rl.removeView(findViewById(id));
        }


        Piece p = (Piece)v;

        pressed = p;
        int id = pressed.getId();

        String c;


        if(id>8 && id<17){
            c = p.pawnMoves();
            selectSquares(c , p);
        }
        if(id==1 || id==8){
            c = p.rookMoves();
            selectSquares(c , p);
        }
    }



    public void selectedOnClick(View v){

        SelectedSquare s = (SelectedSquare)v;

        pressed.x = s.x;
        pressed.y = s.y;

        pressed.setLayoutParams(getPlaceParams(s.x , s.y));

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        for(int id=100; id<6700; id+=100){
            rl.removeView(findViewById(id));
        }
    }
}
