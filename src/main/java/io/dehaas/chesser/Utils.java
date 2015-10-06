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

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Collection of utility methods.
 */
public class Utils {
    Activity activity;

    public Utils(Activity a) {
        activity = a;
    }


    public double dp(double dp) {

        float density = activity.getApplicationContext().getResources().getDisplayMetrics().density;
        return (dp * density);
    }

    public RelativeLayout.LayoutParams getPlaceParams(int x, int y) {
        int color = Game.color;

        final DisplayMetrics metrics;
        DisplayMetrics _metrics = activity.getApplicationContext().getResources().getDisplayMetrics();
        metrics = _metrics;

        float density = metrics.density;
//        System.out.println(density);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;


//        double d = 36.9*density;
        double d = ((width - dp(32)) * 43 / 386);
//        System.out.println(d);
//        System.out.println(width - dp(32));

        double X;
//        if(color==1){X = 17.4;})
        if (color == 1) {
            X = ((width - dp(32)) * 22 / 386);
//            System.out.println(X);
        }
//        else {X = 273.9;}
        else {
            X = ((width - dp(32)) * 22 / 386) + d * 7;
        }

        double Y;
//        if(color==1){Y = 18.4;}
        if (color == 1) {
            Y = ((width - dp(32)) * 23 / 388);
        } else {
            Y = ((width - dp(32)) * 23 / 388) + d * 7;
        }


        RelativeLayout.LayoutParams L = new RelativeLayout.LayoutParams(0,0);
        L.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        L.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        switch (x) {
            case 1:
                L.leftMargin =(int)( X + 0 * d * color);
                break;
            case 2:
                L.leftMargin = (int)(X + 1 * d * color);
                break;
            case 3:
                L.leftMargin = (int)(X + 2 * d * color);
                break;
            case 4:
                L.leftMargin = (int)(X + 3 * d * color);
                break;
            case 5:
                L.leftMargin = (int)(X + 4 * d * color);
                break;
            case 6:
                L.leftMargin = (int)(X + 5 * d * color);
                break;
            case 7:
                L.leftMargin = (int)(X + 6 * d * color);
                break;
            case 8:
                L.leftMargin = (int)(X + 7 * d * color);
                break;
            case 0:
                L.leftMargin = 0;
                break;

        }

        switch (y) {
            case 1:
                L.bottomMargin = (int)(Y + 0 * d * color);
                break;
            case 2:
                L.bottomMargin = (int)(Y + 1 * d * color);
                break;
            case 3:
                L.bottomMargin = (int)(Y + 2 * d * color);
                break;
            case 4:
                L.bottomMargin = (int)(Y + 3 * d * color);
                break;
            case 5:
                L.bottomMargin = (int)(Y + 4 * d * color);
                break;
            case 6:
                L.bottomMargin = (int)(Y + 5 * d * color);
                break;
            case 7:
                L.bottomMargin = (int)(Y + 6 * d * color);
                break;
            case 8:
                L.bottomMargin = (int)(Y + 7 * d * color);
                break;
            case 0:
                L.leftMargin = 0;
                break;

        }

        L.height = (int)d;
        L.width = (int)d;

        return L;
    }

    /**
     * Get a Piece object by its coordinate String.
     */
    public Piece findPieceByCoordinates(String c) {
        int color = Game.color;
        // If the player is white
        if (color == 1) {
            // Black pieces
            if (c.equals(Game.br1.c())) {
                return Game.br1;
            }
            if (c.equals(Game.br2.c())) {
                return Game.br2;
            }
            if (c.equals(Game.bn1.c())) {
                return Game.bn1;
            }
            if (c.equals(Game.bn2.c())) {
                return Game.bn2;
            }
            if (c.equals(Game.bb1.c())) {
                return Game.bb1;
            }
            if (c.equals(Game.bb2.c())) {
                return Game.bb2;
            }
            if (c.equals(Game.bk.c())) {
                return Game.bk;
            }
            if (c.equals(Game.bq.c())) {
                return Game.bq;
            }
            if (c.equals(Game.bp1.c())) {
                return Game.bp1;
            }
            if (c.equals(Game.bp2.c())) {
                return Game.bp2;
            }
            if (c.equals(Game.bp3.c())) {
                return Game.bp3;
            }
            if (c.equals(Game.bp4.c())) {
                return Game.bp4;
            }
            if (c.equals(Game.bp5.c())) {
                return Game.bp5;
            }
            if (c.equals(Game.bp6.c())) {
                return Game.bp6;
            }
            if (c.equals(Game.bp7.c())) {
                return Game.bp7;
            }
            if (c.equals(Game.bp8.c())) {
                return Game.bp8;
            } else {
                return null;
            }
        }
        // If the player is black
        else {
            // White pieces
            if (c.equals(Game.wr1.c())) {
                return Game.wr1;
            }
            if (c.equals(Game.wr2.c())) {
                return Game.wr2;
            }
            if (c.equals(Game.wn1.c())) {
                return Game.wn1;
            }
            if (c.equals(Game.wn2.c())) {
                return Game.wn2;
            }
            if (c.equals(Game.wb1.c())) {
                return Game.wb1;
            }
            if (c.equals(Game.wb2.c())) {
                return Game.wb2;
            }
            if (c.equals(Game.wk.c())) {
                return Game.wk;
            }
            if (c.equals(Game.wq.c())) {
                return Game.wq;
            }
            if (c.equals(Game.wp1.c())) {
                return Game.wp1;
            }
            if (c.equals(Game.wp2.c())) {
                return Game.wp2;
            }
            if (c.equals(Game.wp3.c())) {
                return Game.wp3;
            }
            if (c.equals(Game.wp4.c())) {
                return Game.wp4;
            }
            if (c.equals(Game.wp5.c())) {
                return Game.wp5;
            }
            if (c.equals(Game.wp6.c())) {
                return Game.wp6;
            }
            if (c.equals(Game.wp7.c())) {
                return Game.wp7;
            }
            if (c.equals(Game.wp8.c())) {
                return Game.wp8;
            } else {
                return null;
            }
        }
    }

    /**
     * Get a Piece object by its ID int.
     */
    public Piece findPieceById(int id) {
//            System.out.println("find piece by ID");
//            System.out.println("id:" + id);
        // Black pieces
        if (id == Game.br1.getId()) {
            return Game.br1;
        }
        if (id == Game.br2.getId()) {
            return Game.br2;
        }
        if (id == Game.bn1.getId()) {
            return Game.bn1;
        }
        if (id == Game.bn2.getId()) {
            return Game.bn2;
        }
        if (id == Game.bb1.getId()) {
            return Game.bb1;
        }
        if (id == Game.bb2.getId()) {
            return Game.bb2;
        }
        if (id == Game.bk.getId()) {
            return Game.bk;
        }
        if (id == Game.bq.getId()) {
            return Game.bq;
        }
        if (id == Game.bp1.getId()) {
            return Game.bp1;
        }
        if (id == Game.bp2.getId()) {
            return Game.bp2;
        }
        if (id == Game.bp3.getId()) {
            return Game.bp3;
        }
        if (id == Game.bp4.getId()) {
            return Game.bp4;
        }
        if (id == Game.bp5.getId()) {
            return Game.bp5;
        }
        if (id == Game.bp6.getId()) {
            return Game.bp6;
        }
        if (id == Game.bp7.getId()) {
            return Game.bp7;
        }
        if (id == Game.bp8.getId()) {
            return Game.bp8;
        }
        // White pieces
        if (id == Game.wr1.getId()) {
            return Game.wr1;
        }
        if (id == Game.wr2.getId()) {
            return Game.wr2;
        }
        if (id == Game.wn1.getId()) {
            return Game.wn1;
        }
        if (id == Game.wn2.getId()) {
            return Game.wn2;
        }
        if (id == Game.wb1.getId()) {
            return Game.wb1;
        }
        if (id == Game.wb2.getId()) {
            return Game.wb2;
        }
        if (id == Game.wk.getId()) {
            return Game.wk;
        }
        if (id == Game.wq.getId()) {
            return Game.wq;
        }
        if (id == Game.wp1.getId()) {
            return Game.wp1;
        }
        if (id == Game.wp2.getId()) {
            return Game.wp2;
        }
        if (id == Game.wp3.getId()) {
            return Game.wp3;
        }
        if (id == Game.wp4.getId()) {
            return Game.wp4;
        }
        if (id == Game.wp5.getId()) {
            return Game.wp5;
        }
        if (id == Game.wp6.getId()) {
            return Game.wp6;
        }
        if (id == Game.wp7.getId()) {
            return Game.wp7;
        }
        if (id == Game.wp8.getId()) {
            return Game.wp8;
        }
        // If canot find piece matching the id it is a pawn that promoted id.
        // Devide by 1000 and try again.
        else {
//            System.out.println("pawn id:" + id/1000);
//            return null;

            // Set the new image to the promoted pawn.
            if (id / 1000 != 0) {
                if (id % 1000 == 1) {
                    findPieceById(id / 1000).setImageResource(R.drawable.wrook);
                }
                if (id % 1000 == 2) {
                    findPieceById(id / 1000).setImageResource(R.drawable.wknight);
                }
                if (id % 1000 == 3) {
                    findPieceById(id / 1000).setImageResource(R.drawable.wbishop);
                }
                if (id % 1000 == 4) {
                    findPieceById(id / 1000).setImageResource(R.drawable.wqueen);
                }
                if (id % 1000 == 17) {
                    findPieceById(id / 1000).setImageResource(R.drawable.brook);
                }
                if (id % 1000 == 18) {
                    findPieceById(id / 1000).setImageResource(R.drawable.bknight);
                }
                if (id % 1000 == 19) {
                    findPieceById(id / 1000).setImageResource(R.drawable.bbishop);
                }
                if (id % 1000 == 20) {
                    findPieceById(id / 1000).setImageResource(R.drawable.bqueen);
                }
            }

            return findPieceById(id / 1000);
        }
    }

    /**
     * Check if own king is in check change.
     */
    public boolean myKingInCheck() {

        boolean b = false;
        Piece[] allOpponentsPieces = getAllOpponentsPieces();

        for (Piece piece : allOpponentsPieces) {
//            Piece piece = findPieceById(i);
//            System.out.println(piece.moves()+" : "+i);
//            piece.movesCoor = "";

            String[] cmoves = piece.moves().split(",");
            for (String coor : cmoves) {
//                System.out.println(coor);
                if ((coor.equals(Game.wk.c()) && Game.color == 1) || (coor.equals(Game.bk.c()) && Game.color == -1)) {
                    b = true;
                    break;
                }
            }

//          System.out.println(b);

        }
        return b;
    }

    /**
     * Check if opponent's king is in check.
     */
    public boolean opponentKingInCheck() {

        boolean b = false;
        Piece[] allPieces = getAllPieces();

        for (Piece piece : allPieces) {
//            Piece piece = findPieceById(i);
            String[] cmoves = piece.moves().split(",");
            for (String coor : cmoves) {
                if ((coor.equals(Game.bk.c()) && Game.color == 1) || (coor.equals(Game.wk.c()) && Game.color == -1)) {
                    b = true;
                    break;
                }
            }
        }

        return b;
    }

    /**
     * Checks if own king in check mate and if so ends game .
     */
    public boolean myKingInCheckmate() {
//        Game.kingInCheckmate = true;
        int count = 0;

        boolean kingInCheckMate = true;

        Piece[] pieces = getAllPlayersPieces();
        int tmpx;
        int tmpy;
        int tmpxRemovedPiece = 0;
        int tmpyRemovedPiece = 0;
        Piece tmpRemovedPiece;

        // Scan all players pieces
        mainLoop:
        for (Piece piece : pieces) {
            String[] cmoves = piece.moves().split(",");

            // Scan all coor moves of the piece
            for (String coor : cmoves) {

                System.out.println(cmoves.length);
                count++;

                if (!coor.equals("")) {

                    // Store the current piece coor
                    tmpx = piece.x;
                    tmpy = piece.y;

                    // Change piece coor too move coor
                    piece.x = coor.charAt(0) - 48;
                    piece.y = coor.charAt(1) - 48;

                    tmpRemovedPiece = null;

                    // If the moved piece collides with one of opponents pieces remove it
                    for(Piece opPiece : getAllOpponentsPieces()){
                        if(opPiece.c().equals(piece.c())){
                            tmpRemovedPiece = opPiece;
                            tmpxRemovedPiece = opPiece.x;
                            tmpyRemovedPiece = opPiece.y;
                            opPiece.x = 0;
                            opPiece.y = 0;
                        }
                    }


                    // Check if the king is still in check, if not the king is not in checkmate
                    if (!myKingInCheck()) {
//                        Game.kingInCheckmate = false;

                        kingInCheckMate = false;

                        System.out.println("Not checkmate");

                        System.out.println("x,y:  " + piece.x + "," + piece.y);

                        if(tmpRemovedPiece!=null) {
                            tmpRemovedPiece.x = tmpxRemovedPiece;
                            tmpRemovedPiece.y = tmpyRemovedPiece;
                        }

                        piece.x = tmpx;
                        piece.y = tmpy;

                        break mainLoop;

                    }


                    if(tmpRemovedPiece!=null) {
                        tmpRemovedPiece.x = tmpxRemovedPiece;
                        tmpRemovedPiece.y = tmpyRemovedPiece;
                    }

                    piece.x = tmpx;
                    piece.y = tmpy;
                }
            }

        }

        System.out.println("count: " + count);
        return kingInCheckMate;

    }

    /**
     * Checks if opponent king in check mate and if so ends game .
     */
    public boolean opponentKingInCheckmate() {
//        Game.kingInCheckmate = true;
        int count = 0;

        boolean kingInCheckMate = true;

        Piece[] pieces = getAllOpponentsPieces();
        int tmpx;
        int tmpy;
        int tmpxRemovedPiece = 0;
        int tmpyRemovedPiece = 0;
        Piece tmpRemovedPiece;

        // Scan all players pieces
        mainLoop:
        for (Piece piece : pieces) {
            String[] cmoves = piece.moves().split(",");

            // Scan all coor moves of the piece
            for (String coor : cmoves) {

                System.out.println(cmoves.length);
                count++;

                if (!coor.equals("")) {
//                System.out.println("coo: " + coor);

                    // Store the current piece coor
                    tmpx = piece.x;
                    tmpy = piece.y;
//                    tmpPressedPiece = Game.pressedPiece;

                    // Change piece coor too move coor
                    piece.x = coor.charAt(0) - 48;
                    piece.y = coor.charAt(1) - 48;
//                    Game.pressedPiece = piece;

                    tmpRemovedPiece = null;


                    // If the moved piece collides with one of players pieces remove it
                    for(Piece pPiece : getAllPlayersPieces()){
                        if(pPiece.c().equals(piece.c())){
                            tmpRemovedPiece = pPiece;
                            tmpxRemovedPiece = pPiece.x;
                            tmpyRemovedPiece = pPiece.y;
                            pPiece.x = 0;
                            pPiece.y = 0;
                        }

                    }


                    // Check if the king is still in check, if not the king is not in checkmate
                    if (!opponentKingInCheck()) {
//                        Game.kingInCheckmate = false;

                        kingInCheckMate = false;

                        System.out.println("Not checkmate");

                        System.out.println("x,y:  " + piece.x + "," + piece.y);

                        if(tmpRemovedPiece!=null) {
                            tmpRemovedPiece.x = tmpxRemovedPiece;
                            tmpRemovedPiece.y = tmpyRemovedPiece;
                        }

                        piece.x = tmpx;
                        piece.y = tmpy;

                        break mainLoop;

                    }


                    if(tmpRemovedPiece!=null) {
                        tmpRemovedPiece.x = tmpxRemovedPiece;
                        tmpRemovedPiece.y = tmpyRemovedPiece;
                    }

                    piece.x = tmpx;
                    piece.y = tmpy;
                }
            }

        }

        System.out.println("count: " + count);
        return kingInCheckMate;

    }

    /**
     * Returns an array of all the pieces.
     */
    public Piece[] getAllPieces() {
        Piece[] pieceArray = {
                Game.wr1,
                Game.wr2,
                Game.wn1,
                Game.wn2,
                Game.wb1,
                Game.wb2,
                Game.wk,
                Game.wq,
                Game.wp1,
                Game.wp2,
                Game.wp3,
                Game.wp4,
                Game.wp5,
                Game.wp6,
                Game.wp7,
                Game.wp8,

                Game.br1,
                Game.br2,
                Game.bn1,
                Game.bn2,
                Game.bb1,
                Game.bb2,
                Game.bk,
                Game.bq,
                Game.bp1,
                Game.bp2,
                Game.bp3,
                Game.bp4,
                Game.bp5,
                Game.bp6,
                Game.bp7,
                Game.bp8,
        };

        return pieceArray;
    }

    /**
     * Returns an array of all the players pieces.
     */
    public Piece[] getAllPlayersPieces() {
        if (Game.color == 1) {
            Piece[] pieceArray = {
                    Game.wr1,
                    Game.wr2,
                    Game.wn1,
                    Game.wn2,
                    Game.wb1,
                    Game.wb2,
                    Game.wk,
                    Game.wq,
                    Game.wp1,
                    Game.wp2,
                    Game.wp3,
                    Game.wp4,
                    Game.wp5,
                    Game.wp6,
                    Game.wp7,
                    Game.wp8
            };

            return pieceArray;
        } else {

            Piece[] pieceArray = {
                    Game.br1,
                    Game.br2,
                    Game.bn1,
                    Game.bn2,
                    Game.bb1,
                    Game.bb2,
                    Game.bk,
                    Game.bq,
                    Game.bp1,
                    Game.bp2,
                    Game.bp3,
                    Game.bp4,
                    Game.bp5,
                    Game.bp6,
                    Game.bp7,
                    Game.bp8
            };

            return pieceArray;
        }
    }

    /**
     * Returns an array of all the opponents pieces.
     */
    public Piece[] getAllOpponentsPieces() {
        if (Game.color == 1) {
            Piece[] pieceArray = {
                    Game.br1,
                    Game.br2,
                    Game.bn1,
                    Game.bn2,
                    Game.bb1,
                    Game.bb2,
                    Game.bk,
                    Game.bq,
                    Game.bp1,
                    Game.bp2,
                    Game.bp3,
                    Game.bp4,
                    Game.bp5,
                    Game.bp6,
                    Game.bp7,
                    Game.bp8
            };

            return pieceArray;
        } else {

            Piece[] pieceArray = {
                    Game.wr1,
                    Game.wr2,
                    Game.wn1,
                    Game.wn2,
                    Game.wb1,
                    Game.wb2,
                    Game.wk,
                    Game.wq,
                    Game.wp1,
                    Game.wp2,
                    Game.wp3,
                    Game.wp4,
                    Game.wp5,
                    Game.wp6,
                    Game.wp7,
                    Game.wp8
            };

            return pieceArray;
        }
    }

    /** Remove opponents that have been atacked by one piece. */
    public void removeOpponent(){

        String[] opponentc;

        // If the player is white
        if(Game.color==1) {
            opponentc = new String[] {Game.br1.c(), Game.br2.c(), Game.bn1.c(), Game.bn2.c(), Game.bb1.c(), Game.bb2.c(), Game.bk.c(), Game.bq.c(),
                    Game.bp1.c(), Game.bp2.c(), Game.bp3.c(), Game.bp4.c(), Game.bp5.c(), Game.bp6.c(), Game.bp7.c(), Game.bp8.c()};
        }
        // If the player is black
        else {
            opponentc = new String[] {Game.wr1.c(), Game.wr2.c(), Game.wn1.c(), Game.wn2.c(), Game.wb1.c(), Game.wb2.c(), Game.wk.c(), Game.wq.c(),
                    Game.wp1.c(), Game.wp2.c(), Game.wp3.c(), Game.wp4.c(), Game.wp5.c(), Game.wp6.c(), Game.wp7.c(), Game.wp8.c()};
        }

        String s = Game.pressedPiece.c();
        for(int t=0; t<opponentc.length; t++){
            if (s.equals(opponentc[t])){
                Piece p = findPieceByCoordinates(opponentc[t]);
                Game.removedPieceX = p.x;
                Game.removedPieceY = p.y;
                Game.removedPiece = p;

                p.x = 0;
                p.y = 0;
//                RelativeLayout rl = (RelativeLayout)activity.findViewById(R.id.fragment);
//                rl.removeView(p);
                p.setVisibility(View.INVISIBLE);

            }
        }
    }

    /** Return last opponent that has been removed. */
    public void returnOpponent(){
        if (Game.removedPiece != null) {
            Game.removedPiece.x = Game.removedPieceX;
            Game.removedPiece.y = Game.removedPieceY;

            RelativeLayout rl = (RelativeLayout) activity.findViewById(R.id.fragment);
//                        rl.addView(removedPiece);
            Game.removedPiece.setVisibility(View.VISIBLE);

            Game.removedPiece = null;
        }
    }

    public boolean squareIsUnsafe(int x, int y){
        int kingx=0;
        int kingy=0;

        if(Game.color==1) {
            kingx = Game.wk.x;
            kingy = Game.wk.y;

            Game.wk.x = x;
            Game.wk.y = y;
        }
        if(Game.color==-1) {
            kingx = Game.bk.x;
            kingy = Game.bk.y;

            Game.bk.x = x;
            Game.bk.y = y;
        }

        boolean check = myKingInCheck();

        if(Game.color==1) {
            Game.wk.x = kingx;
            Game.wk.y = kingy;
        }
        if(Game.color==-1) {
            Game.bk.x = kingx;
            Game.bk.y = kingy;
        }

        return check;
    }

}
