package com.saymedia.chessgame;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;


public class Game extends ActionBarActivity {

    public static BluetoothSocket socket;
    ConnectedThread connectThread;
    IncomeListenerThread IncomeListenerThread = new IncomeListenerThread(this);
    Utils u = new Utils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ConnectedThread tmp = new ConnectedThread(socket);
        connectThread=tmp;
        connectThread.start();

        setupPieces();

        if(color==1){ myTurn=true; }
        else{ myTurn=false; IncomeListenerThread.start();}
    }
    public static int color = 1;

    Boolean myTurn;

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

    public void removeAponnent(){
        String[] bc =  { br1.c(),br2.c(),bn1.c(),bn2.c(),bb1.c(),bb2.c(),bk.c(),bq.c(),
                bp1.c(),bp2.c(),bp3.c(),bp4.c(),bp5.c(),bp6.c(),bp7.c(),bp8.c()};

        String s = pressed.c();
        for(int t=0; t<bc.length; t++){
            if (s.equals(bc[t])){
                Piece p = u.findPieceByCoordinates(bc[t]);
                System.out.println(p);
                p.x = 0;
                System.out.println(p);
                p.y = 0;
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
                rl.removeView(p);

            }
        }
    }

    /** Returns an array of two booleans witch indicates if there is a Piece on the given coordinate. */
    public static boolean[] checkCoordinate(String s){
        String[] playerCoordinates =  { wr1.c(),wr2.c(),wn1.c(),wn2.c(),wb1.c(),wb2.c(),wk.c(),wq.c(),
                        wp1.c(),wp2.c(),wp3.c(),wp4.c(),wp5.c(),wp6.c(),wp7.c(),wp8.c()};

        String[] opponentCoordinates =  { br1.c(),br2.c(),bn1.c(),bn2.c(),bb1.c(),bb2.c(),bk.c(),bq.c(),
                bp1.c(),bp2.c(),bp3.c(),bp4.c(),bp5.c(),bp6.c(),bp7.c(),bp8.c()};


        boolean p = true;
        boolean o = true;

        for(int t=0; t<playerCoordinates.length; t++){
            if (s.equals(playerCoordinates[t])){
                p=false;
            }
        }
        for(int t=0; t<opponentCoordinates.length; t++){
            if (s.equals(opponentCoordinates[t])){
                o=false;
            }
        }

        boolean[] B = {p,o};
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
        // If the player is white
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
        // If the player is black
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

        image.setLayoutParams(u.getPlaceParams(x, y));

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
        p.setLayoutParams(u.getPlaceParams(x, y));
        rl.addView(p);

        // If the player is white
        if(color==1) {
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
        // If the player is black
        else {
            switch (id){
                case 1: br1 = p; break;
                case 2: bn1 = p; break;
                case 3: bb1 = p; break;
                case 4: bq = p; break;
                case 5: bk = p; break;
                case 6: bb2 = p; break;
                case 7: bn2 = p; break;
                case 8: br2 = p; break;
                case 9: bp1 = p; break;
                case 10: bp2 = p; break;
                case 11: bp3 = p; break;
                case 12: bp4 = p; break;
                case 13: bp5 = p; break;
                case 14: bp6 = p; break;
                case 15: bp7 = p; break;
                case 16: bp8 = p; break;

                case 17: wr1 = p; break;
                case 18: wn1 = p; break;
                case 19: wb1 = p; break;
                case 20: wq = p; break;
                case 21: wk = p; break;
                case 22: wb2 = p; break;
                case 23: wn2 = p; break;
                case 24: wr2 = p; break;
                case 25: wp1 = p; break;
                case 26: wp2 = p; break;
                case 27: wp3 = p; break;
                case 28: wp4 = p; break;
                case 29: wp5 = p; break;
                case 30: wp6 = p; break;
                 case 31: wp7 = p; break;
                case 32: wp8 = p; break;
            }
        }

    }

    public void backButton(View v){
        startActivity(new Intent("first.activity"));
    }

    public void piecesOnClick(View v){
        if(myTurn) {

            RelativeLayout rl = (RelativeLayout) findViewById(R.id.fragment);
            for (int id = 100; id < 6700; id += 100) {
                rl.removeView(findViewById(id));
            }


            Piece p = (Piece) v;

            pressed = p;
            int id = pressed.getId();

            String c;


            if (id > 8 && id < 17) {
                c = p.pawnMoves();
                selectSquares(c, p);
            }
            if (id == 1 || id == 8) {
                c = p.rookMoves();
                selectSquares(c, p);
            }
            if (id == 3 || id == 6) {
                c = p.bishopMoves();
                selectSquares(c, p);
            }
            if (id == 4) {
                c = p.queenMoves();
                selectSquares(c, p);
            }
            if (id == 5) {
                c = p.kingMoves();
                selectSquares(c, p);
            }
            if (id == 2 || id == 7) {
                c = p.knightMoves();
                selectSquares(c, p);
            }


//        System.out.println(Piece.s);

            Piece.s = "";

        }
    }

    public void selectedOnClick(View v){

        myTurn=false;

        SelectedSquare s = (SelectedSquare)v;

        pressed.x = s.x;
        pressed.y = s.y;


        pressed.setLayoutParams(u.getPlaceParams(s.x , s.y));

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        for(int id=100; id<6700; id+=100){
            rl.removeView(findViewById(id));
        }


        removeAponnent();

        String wc = wr1.c()+","+wr2.c()+","+wn1.c()+","+wn2.c()+","+wb1.c()+","+wb2.c()+","+wk.c()+","+wq.c()+
                ","+wp1.c()+","+wp2.c()+","+wp3.c()+","+wp4.c()+","+wp5.c()+","+wp6.c()+","+wp7.c()+","+wp8.c();

        String bc = br1.c()+","+br2.c()+","+bn1.c()+","+bn2.c()+","+bb1.c()+","+bb2.c()+","+bk.c()+","+bq.c()+
                ","+bp1.c()+","+bp2.c()+","+bp3.c()+","+bp4.c()+","+bp5.c()+","+bp6.c()+","+bp7.c()+","+bp8.c();

        String c = wc +","+ bc;

        connectThread.stringWrite(c);

    }
}
