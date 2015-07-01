package com.saymedia.chessgame;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by SayMedia on 21/06/2015.
 */
public class Piece extends ImageView{
    public Piece(Context c, int X, int Y, String pname, int id){
        super(c);

        switch (pname){

            case "bbishop": this.setImageResource(R.drawable.bbishop); break;
            case "bking": this.setImageResource(R.drawable.bking); break;
            case "bknight": this.setImageResource(R.drawable.bknight); break;
            case "bpawn": this.setImageResource(R.drawable.bpawn); break;
            case "bqueen": this.setImageResource(R.drawable.bqueen); break;
            case "brook": this.setImageResource(R.drawable.brook); break;

            case "wbishop": this.setImageResource(R.drawable.wbishop); break;
            case "wking": this.setImageResource(R.drawable.wking); break;
            case "wknight": this.setImageResource(R.drawable.wknight); break;
            case "wpawn": this.setImageResource(R.drawable.wpawn); break;
            case "wqueen": this.setImageResource(R.drawable.wqueen); break;
            case "wrook": this.setImageResource(R.drawable.wrook); break;
        }

        this.setVisibility(View.VISIBLE);
        this.setId(id);

        x = X;
        y = Y;

    };

    public int x = 0;
    public int y = 0;
    public String c(){String s = x+""+y; return s;}

    public static String s = "";

    void addDestination(int a, int b){
        if(a>0 && a<9 && b>0 && b<9){
            if(s.equals("")){s = a+""+b;}
            else {s =  s+","+a+""+b;}
        }
    }


    /** Returns a string array of all the coordinates a pawn can move to. */
    public String pawnMoves(){

        addDestination(x,y+1);

        if(y==2){addDestination(x,y+2);}

        return s;
    }

    /** Returns a string array of all the coordinates a rook can move to. */
    public String rookMoves(){
        for(int i = 1; i<9; i++){
            if(i!=y){addDestination(x,i);}
        }

        for(int i = 1; i<9; i++){
            if(i!=x){addDestination(i,y);}
        }

        return s;
    }

    /** Returns a string array of all the coordinates a knight can move to. */
    public String knightMoves(){
        addDestination(x-1, y+2);
        addDestination(x+1, y+2);
        addDestination(x-1, y-2);
        addDestination(x+1, y-2);
        addDestination(x-2, y+1);
        addDestination(x+2, y+1);
        addDestination(x-2, y-1);
        addDestination(x+2, y-1);

        System.out.println(s);
        return s;
    }

    /** Returns a string array of all the coordinates a bishop can move to. */
    public String bishopMoves(){
        int n = y -x;
        for(int i = 1; i<9; i++){
            if(i!=x){addDestination(i,i+n);}
        }

        n = y +x;
        for(int i = 1; i<9; i++){
            if(i!=x){addDestination(i,-i+n);}
        }

        return s;
    }

    /** Returns a string array of all the coordinates a king can move to. */
    public String kingMoves(){
        addDestination(x+1, y);
        addDestination(x-1, y);
        addDestination(x, y+1);
        addDestination(x, y-1);
        addDestination(x+1, y+1);
        addDestination(x+1, y-1);
        addDestination(x-1, y-1);
        addDestination(x - 1, y + 1);

        return s;
    }

    /** Returns a string array of all the coordinates a queen can move to. */
    public String queenMoves(){
        for(int i = 1; i<9; i++){
            if(i!=y){addDestination(x,i);}
        }

        for(int i = 1; i<9; i++){
            if(i!=x){addDestination(i,y);}
        }

        int n = y -x;
        for(int i = 1; i<9; i++){
            if(i!=x){addDestination(i,i+n);}
        }

        n = y +x;
        for(int i = 1; i<9; i++){
            if(i!=x){addDestination(i,-i+n);}
        }

        return s;
    }


}
