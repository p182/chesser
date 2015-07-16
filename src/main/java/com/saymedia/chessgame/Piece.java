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
    public String c(){ String s = x+""+y; return s; }

      /** Return a string of coordinates:ID of the piece. */
    public String cId(){ String s = c()+":"+getId(); return s; }

    public static String s = "";

    /** Adds safely a coordinate to the string. */
    void addDestination(int a, int b){
        if(a>0 && a<9 && b>0 && b<9){
            if(s.equals("")){s = a+""+b;}
            else {s =  s+","+a+""+b;}
        }
    }


    /** Returns a string array of all the coordinates a pawn can move to. */
    public String pawnMoves(){

        String S = x+""+(y+Game.color);
        boolean p = Game.checkCoordinate(S)[0];
        boolean o = Game.checkCoordinate(S)[1];
        if(p&&o){addDestination(x, y+Game.color);}

        if(y==2 || y==7){
            S = x+""+(y+2*Game.color);
            p = Game.checkCoordinate(S)[0];
            o = Game.checkCoordinate(S)[1];
            if(p&&o){addDestination(x, y+2*Game.color);}
        }

        S = x-Game.color+""+(y+Game.color);
        o = Game.checkCoordinate(S)[1];
        if(!o){addDestination(x-Game.color, y+Game.color);}
        S = x+Game.color+""+(y+Game.color);
        o = Game.checkCoordinate(S)[1];
        if(!o){addDestination(x+Game.color, y+Game.color);}

        return s;
    }

    /** Returns a string array of all the coordinates a rook can move to. */
    public String rookMoves(){
        for(int i = y+Game.color; 0<i&&i<9; i=i+Game.color) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }
        for(int i = y-Game.color; 0<i&&i<9; i=i-Game.color) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }

        for(int i = x+Game.color; 0<i&&i<9; i=i+Game.color){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }
        for(int i = x-Game.color; 0<i&&i<9; i=i-Game.color){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }

        return s;
    }

    /** Returns a string array of all the coordinates a knight can move to. */
    public String knightMoves(){
        String S = x-Game.color+""+(y+2*Game.color);
        boolean p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-Game.color, y+2*Game.color);}

        S = x+Game.color+""+(y+2*Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+Game.color, y+2*Game.color);}

        S = x-Game.color+""+(y-2*Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-Game.color, y-2*Game.color);}

        S = x+Game.color+""+(y-2*Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+Game.color, y-2*Game.color);}

        S = x-2*Game.color+""+(y+Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-2*Game.color, y+Game.color);}

        S = x+2*Game.color+""+(y+Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+2*Game.color, y+Game.color);}

        S = x-2*Game.color+""+(y-Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-2*Game.color, y-Game.color);}

        S = x+2*Game.color+""+(y-Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+2*Game.color, y-Game.color);}

        return s;
    }

    /** Returns a string array of all the coordinates a bishop can move to. */
    public String bishopMoves(){

        int n = y -x;
        for(int i = x+Game.color; 0<i&&i<9; i=i+Game.color) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }
        for(int i = x-Game.color; 0<i&&i<9; i=i-Game.color) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }

        n = y +x;
        for(int i = x+Game.color; 0<i&&i<9; i=i+Game.color) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }
        for(int i = x-Game.color; 0<i&&i<9; i=i-Game.color) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }

        return s;
    }

    /** Returns a string array of all the coordinates a king can move to. */
    public String kingMoves(){
        String S = x+Game.color+""+y;
        boolean p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+Game.color, y);}

        S = x-Game.color+""+y;
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-Game.color, y);}

        S = x+""+(y+Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x, y+Game.color);}

        S = x+""+(y-Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x, y-Game.color);}

        S = x+Game.color+""+(y+Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+Game.color, y+Game.color);}

        S = x+Game.color+""+(y-Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x+Game.color, y-Game.color);}

        S = x-Game.color+""+(y+Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-Game.color, y+Game.color);}

        S = x-Game.color+""+(y+Game.color);
        p = Game.checkCoordinate(S)[0];
        if(p){addDestination(x-Game.color, y-Game.color);}

        return s;
    }

    /** Returns a string array of all the coordinates a queen can move to. */
    public String queenMoves(){
        for(int i = y+Game.color; 0<i&&i<9; i=i+Game.color) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }
        for(int i = y-Game.color; 0<i&&i<9; i=i-Game.color) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }

        for(int i = x+Game.color; 0<i&&i<9; i=i+Game.color){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }
        for(int i = x-Game.color; 0<i&&i<9; i=i-Game.color){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }


        int n = y -x;
        for(int i = x+Game.color; 0<i&&i<9; i=i+Game.color) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }
        for(int i = x-Game.color; 0<i&&i<9; i=i-Game.color) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }

        n = y +x;
        for(int i = x+Game.color; 0<i&&i<9; i=i+Game.color) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }
        for(int i = x-Game.color; 0<i&&i<9; i=i-Game.color) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S)[0];
            boolean o = Game.checkCoordinate(S)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }

        return s;
    }


}
