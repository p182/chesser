// Copyright 2015 Roni Harel
//
// This file is part of Chesser.
//
// Chesser is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Chesser is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Chesser.  If not, see <http://www.gnu.org/licenses/>.

package io.dehaas.chesser;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Chess piece.
 */
public class Piece extends ImageView{
    public Piece(Context c, int X, int Y, int id){
        super(c);

        /** Set the image of the piece by its id. */
        switch (id){
            case 19: case 22: this.setImageResource(R.drawable.bbishop); break;
            case 21: this.setImageResource(R.drawable.bking); break;
            case 18: case 23: this.setImageResource(R.drawable.bknight); break;
            case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32:
                this.setImageResource(R.drawable.bpawn); break;
            case 20: this.setImageResource(R.drawable.bqueen); break;
            case 17: case 24: this.setImageResource(R.drawable.brook); break;

            case 3: case 6: this.setImageResource(R.drawable.wbishop); break;
            case 5: this.setImageResource(R.drawable.wking); break;
            case 2: case 7: this.setImageResource(R.drawable.wknight); break;
            case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16:
                this.setImageResource(R.drawable.wpawn); break;
            case 4: this.setImageResource(R.drawable.wqueen); break;
            case 1: case 8: this.setImageResource(R.drawable.wrook); break;
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

    String movesCoor = "";

    /** Adds safely coordinates to the string of move coordinates. */
    void addDestination(int a, int b){
        if(a>0 && a<9 && b>0 && b<9){
            if(movesCoor.equals("")){
                movesCoor = a+""+b;}
            else {
                movesCoor =  movesCoor +","+a+""+b;}
        }
    }

    /** Return a string of all the coordinates a this piece can move to. */
    public String moves(){
        String moves = "";
        movesCoor ="";

        // If the piece has been removed his c() will be "00"
        // If the piece hase been removed return ""
        if(!c().equals("00")){
            /** Get moves of the piece by its id. */
            switch (getId()){
                // for black pieces
                case 19: case 22: moves = bishopMoves(-1); break;
                case 21: moves = kingMoves(-1); break;
                case 18: case 23: moves = knightMoves(-1); break;
                case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 32:
                    moves = pawnMoves(-1); break;
                case 20: moves = queenMoves(-1); break;
                case 17:case 24: moves = rookMoves(-1); break;
                // for white pieces
                case 3: case 6: moves = bishopMoves(1); break;
                case 5: moves = kingMoves(1); break;
                case 2: case 7: moves = knightMoves(1); break;
                case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16:
                    moves = pawnMoves(1); break;
                case 4: moves = queenMoves(1); break;
                case 1: case 8: moves = rookMoves(1); break;

                default:
                    System.out.println("default");
                    if(getId()%1000==1)moves = rookMoves(1);
                    if(getId()%1000==2)moves = knightMoves(1);
                    if(getId()%1000==3)moves = bishopMoves(1);
                    if(getId()%1000==4)moves = queenMoves(1);
                    if(getId()%1000==17)moves = rookMoves(-1);
                    if(getId()%1000==18)moves = knightMoves(-1);
                    if(getId()%1000==19)moves = bishopMoves(-1);
                    if(getId()%1000==20)moves = queenMoves(-1); break;

            }
        }


        return moves;
    }

    /** Returns a string array  */
    public String pawnMoves(int pieceColor){

        String S = x+""+(y+pieceColor);
        boolean p = Game.checkCoordinate(S, pieceColor)[0];
        boolean o = Game.checkCoordinate(S, pieceColor)[1];
        if(p&&o){addDestination(x, y+pieceColor);}

        if((y==2 || y==7)&& p && o){
            S = x+""+(y+2*pieceColor);
            p = Game.checkCoordinate(S, pieceColor)[0];
            o = Game.checkCoordinate(S, pieceColor)[1];
            if(p&&o){addDestination(x, y+2*pieceColor);}
        }

        S = x-pieceColor+""+(y+pieceColor);
        o = Game.checkCoordinate(S, pieceColor)[1];
        if(!o){addDestination(x-pieceColor, y+pieceColor);}
        S = x+pieceColor+""+(y+pieceColor);
        o = Game.checkCoordinate(S, pieceColor)[1];
        if(!o){addDestination(x+pieceColor, y+pieceColor);}

//        System.out.println(movesCoor);
        return movesCoor;
    }

    /** Returns a string array of all the coordinates a rook can move to. */
    public String rookMoves(int pieceColor){
        for(int i = y+pieceColor; 0<i&&i<9; i=i+pieceColor) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }
        for(int i = y-pieceColor; 0<i&&i<9; i=i-pieceColor) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }

        for(int i = x+pieceColor; 0<i&&i<9; i=i+pieceColor){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }
        for(int i = x-pieceColor; 0<i&&i<9; i=i-pieceColor){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }

        return movesCoor;
    }

    /** Returns a string array of all the coordinates a knight can move to. */
    public String knightMoves(int pieceColor){
        String S = x-pieceColor+""+(y+2*pieceColor);
        boolean p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x-pieceColor, y+2*pieceColor);}

        S = x+pieceColor+""+(y+2*pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x+pieceColor, y+2*pieceColor);}

        S = x-pieceColor+""+(y-2*pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x-pieceColor, y-2*pieceColor);}

        S = x+pieceColor+""+(y-2*pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x+pieceColor, y-2*pieceColor);}

        S = x-2*pieceColor+""+(y+pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x-2*pieceColor, y+pieceColor);}

        S = x+2*pieceColor+""+(y+pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x+2*pieceColor, y+pieceColor);}

        S = x-2*pieceColor+""+(y-pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x-2*pieceColor, y-pieceColor);}

        S = x+2*pieceColor+""+(y-pieceColor);
        p = Game.checkCoordinate(S, pieceColor)[0];
        if(p){addDestination(x+2*pieceColor, y-pieceColor);}

        return movesCoor;
    }

    /** Returns a string array of all the coordinates a bishop can move to. */
    public String bishopMoves(int pieceColor){

        int n = y -x;
        for(int i = x+pieceColor; 0<i&&i<9; i=i+pieceColor) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }
        for(int i = x-pieceColor; 0<i&&i<9; i=i-pieceColor) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }

        n = y +x;
        for(int i = x+pieceColor; 0<i&&i<9; i=i+pieceColor) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }
        for(int i = x-pieceColor; 0<i&&i<9; i=i-pieceColor) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }

        return movesCoor;
    }

    /** Returns a string array of all the coordinates a king can move to. */
    public String kingMoves(int pieceColor){
        String coor; // Move coordinates.
        boolean p; // Coor does not overlap a same color piece.

        coor = x+pieceColor+""+y;
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x+pieceColor, y);}

        coor = x-pieceColor+""+y;
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x-pieceColor, y);}

        coor = x+""+(y+pieceColor);
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x, y+pieceColor);}

        coor = x+""+(y-pieceColor);
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x, y-pieceColor);}

        coor = x+pieceColor+""+(y+pieceColor);
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x+pieceColor, y+pieceColor);}

        coor = x+pieceColor+""+(y-pieceColor);
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x+pieceColor, y-pieceColor);}

        coor = x-pieceColor+""+(y+pieceColor);
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x-pieceColor, y+pieceColor);}

        coor = x-pieceColor+""+(y-pieceColor);
        p = Game.checkCoordinate(coor, pieceColor)[0];
        if(p){addDestination(x-pieceColor, y-pieceColor);}

        return movesCoor;
    }

    /** Returns a string array of all the coordinates a queen can move to. */
    public String queenMoves(int pieceColor){
        for(int i = y+pieceColor; 0<i&&i<9; i=i+pieceColor) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }
        for(int i = y-pieceColor; 0<i&&i<9; i=i-pieceColor) {
            String S = x + "" + i;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(x, i);
            if(!o){break;}
        }

        for(int i = x+pieceColor; 0<i&&i<9; i=i+pieceColor){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }
        for(int i = x-pieceColor; 0<i&&i<9; i=i-pieceColor){
            String S = i+""+y;
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i, y);
            if(!o){break;}
        }


        int n = y -x;
        for(int i = x+pieceColor; 0<i&&i<9; i=i+pieceColor) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }
        for(int i = x-pieceColor; 0<i&&i<9; i=i-pieceColor) {
            String S = i + "" + (i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,i+n);
            if(!o){break;}
        }

        n = y +x;
        for(int i = x+pieceColor; 0<i&&i<9; i=i+pieceColor) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }
        for(int i = x-pieceColor; 0<i&&i<9; i=i-pieceColor) {
            String S = i + "" + (-i+n);
            boolean p = Game.checkCoordinate(S, pieceColor)[0];
            boolean o = Game.checkCoordinate(S, pieceColor)[1];

            if(!p){break;}
            addDestination(i,-i+n);
            if(!o){break;}
        }

        return movesCoor;
    }


    //TODO: make a "type" field

}
