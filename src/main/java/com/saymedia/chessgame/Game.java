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
    public static int color = 1;

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

    public Piece findPieceByCoordinates(String c){
        if(c.equals(br1.c())){return br1;}
        if(c.equals(br2.c())){return br2;}
        if(c.equals(bn1.c())){return bn1;}
        if(c.equals(bn2.c())){return bn2;}
        if(c.equals(bb1.c())){return bb1;}
        if(c.equals(bb2.c())){return bb2;}
        if(c.equals(bk.c())){return bk;}
        if(c.equals(bq.c())){return bq;}
        if(c.equals(bp1.c())){return bp1;}
        if(c.equals(bp2.c())){return bp2;}
        if(c.equals(bp3.c())){return bp3;}
        if(c.equals(bp4.c())){return bp4;}
        if(c.equals(bp5.c())){return bp5;}
        if(c.equals(bp6.c())){return bp6;}
        if(c.equals(bp7.c())){return bp7;}
        if(c.equals(bp8.c())){return bp8;}
        else{return null;}
    }
    public void removeAponent(){
        String[] bc =  { br1.c(),br2.c(),bn1.c(),bn2.c(),bb1.c(),bb2.c(),bk.c(),bq.c(),
                bp1.c(),bp2.c(),bp3.c(),bp4.c(),bp5.c(),bp6.c(),bp7.c(),bp8.c()};

        String s = pressed.c();
        for(int t=0; t<bc.length; t++){
            if (s.equals(bc[t])){
                Piece p = findPieceByCoordinates(bc[t]);
                System.out.println(p);
                p.x = 0;
                System.out.println(p);
                p.y = 0;
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
                rl.removeView(p);

            }
        }
    }

    public static boolean[] checkCoordinate(String s){
        String[] wc =  { wr1.c(),wr2.c(),wn1.c(),wn2.c(),wb1.c(),wb2.c(),wk.c(),wq.c(),
                        wp1.c(),wp2.c(),wp3.c(),wp4.c(),wp5.c(),wp6.c(),wp7.c(),wp8.c()};

        String[] bc =  { br1.c(),br2.c(),bn1.c(),bn2.c(),bb1.c(),bb2.c(),bk.c(),bq.c(),
                bp1.c(),bp2.c(),bp3.c(),bp4.c(),bp5.c(),bp6.c(),bp7.c(),bp8.c()};

//        System.out.println(bc[12]);

        boolean w = true;
        boolean b = true;

        for(int t=0; t<wc.length; t++){
            if (s.equals(wc[t])){
                w=false;
            }
        }
        for(int t=0; t<bc.length; t++){
            if (s.equals(bc[t])){
                b=false;
            }
        }

        boolean[] B = {w,b};
        return B;
    }

    public void selectSquares(String s, Piece p){
        if(!s.equals("")) {
            String[] array = s.split(",");

            for (int i = 0; i < array.length; i++) {
                String pars = array[i];
                int x = pars.charAt(0) - 48;
                int y = pars.charAt(1) - 48;
                creatSelectedSquare(x, y, (i + 1) * 100);
            }
        }

        creatSelectedSquare(p.x, p.y, 65 * 100);
        creatSelectedSquare(p.x, p.y, 66 * 100);
        p.bringToFront();
    }

    public void setupPieces(){
        if(color==1) {
            creatPiece("wrook", 1, 1, 1);
            creatPiece("wknight", 2, 1, 2);
            creatPiece("wbishop", 3, 1, 3);
            creatPiece("wqueen", 4, 1, 4);
            creatPiece("wking", 5, 1, 5);
            creatPiece("wbishop", 6, 1, 6);
            creatPiece("wknight", 7, 1, 7);
            creatPiece("wrook", 8, 1, 8);
            for (int i = 1; i < 9; i++) {
                creatPiece("wpawn", i, 2, (i + 8));
            }


            creatPiece("brook", 1, 8, 17);
            creatPiece("bknight", 2, 8, 18);
            creatPiece("bbishop", 3, 8, 19);
            creatPiece("bqueen", 4, 8, 20);
            creatPiece("bking", 5, 8, 21);
            creatPiece("bbishop", 6, 8, 22);
            creatPiece("bknight", 7, 8, 23);
            creatPiece("brook", 8, 8, 24);
            for (int i = 1; i < 9; i++) {
                creatPiece("bpawn", i, 7, (i + 24));
            }
        }

        else {
            creatPiece("brook", 1, 8, 1);
            creatPiece("bknight", 2, 8, 2);
            creatPiece("bbishop", 3, 8, 3);
            creatPiece("bqueen", 4, 8, 4);
            creatPiece("bking", 5, 8, 5);
            creatPiece("bbishop", 6, 8, 6);
            creatPiece("bknight", 7, 8, 7);
            creatPiece("brook", 8, 8, 8);
            for (int i = 1; i < 9; i++) {
                creatPiece("bpawn", i, 7, (i + 8));
            }


            creatPiece("wrook", 1, 1, 17);
            creatPiece("wknight", 2, 1, 18);
            creatPiece("wbishop", 3, 1, 19);
            creatPiece("wqueen", 4, 1, 20);
            creatPiece("wking", 5, 1, 21);
            creatPiece("wbishop", 6, 1, 22);
            creatPiece("wknight", 7, 1, 23);
            creatPiece("wrook", 8, 1, 24);
            for (int i = 1; i < 9; i++) {
                creatPiece("wpawn", i, 2, (i + 24));
            }
        }

    }

    public void creatSelectedSquare(int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        SelectedSquare image = new SelectedSquare(getApplicationContext(), x, y, id);

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
        Piece p = new Piece(getApplicationContext(), x, y, pname, id);

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

        double X;
        if(color==1){X = 17.4;}
        else {X = 273.9;}

        double Y;
        if(color==1){Y = 18.4;}
        else {Y = 274.9;}

        double d = 36.9;

        RelativeLayout.LayoutParams L = new RelativeLayout.LayoutParams(dp(38),dp(38));
        L.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        L.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        switch (x){
            case 1: L.leftMargin = dp(X) + 0*dp(d)*color; break;
            case 2: L.leftMargin = dp(X) + 1*dp(d)*color; break;
            case 3: L.leftMargin = dp(X) + 2*dp(d)*color; break;
            case 4: L.leftMargin = dp(X) + 3*dp(d)*color; break;
            case 5: L.leftMargin = dp(X) + 4*dp(d)*color; break;
            case 6: L.leftMargin = dp(X) + 5*dp(d)*color; break;
            case 7: L.leftMargin = dp(X) + 6*dp(d)*color; break;
            case 8: L.leftMargin = dp(X) + 7*dp(d)*color; break;
            case 0: L.leftMargin = 0; break;

        }

        switch (y){
            case 1: L.bottomMargin = dp(Y) + 0*dp(d)*color; break;
            case 2: L.bottomMargin = dp(Y) + 1*dp(d)*color; break;
            case 3: L.bottomMargin = dp(Y) + 2*dp(d)*color; break;
            case 4: L.bottomMargin = dp(Y) + 3*dp(d)*color; break;
            case 5: L.bottomMargin = dp(Y) + 4*dp(d)*color; break;
            case 6: L.bottomMargin = dp(Y) + 5*dp(d)*color; break;
            case 7: L.bottomMargin = dp(Y) + 6*dp(d)*color; break;
            case 8: L.bottomMargin = dp(Y) + 7*dp(d)*color; break;
            case 0: L.leftMargin = 0; break;

        }

        L.height=dp(38);
        L.width=dp(38);

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
        if(id==3 || id==6){
            c = p.bishopMoves();
            selectSquares(c , p);
        }
        if(id==4){
            c = p.queenMoves();
            selectSquares(c , p);
        }
        if(id==5){
            c = p.kingMoves();
            selectSquares(c , p);
        }
        if(id==2 || id==7){
            c = p.knightMoves();
            selectSquares(c , p);
        }


//        System.out.println(Piece.s);

        Piece.s = "";


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


        removeAponent();
    }
}
