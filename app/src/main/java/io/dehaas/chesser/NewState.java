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
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Helps generate a new game state.
 */
public class NewState{
    String s;
    Activity activity;
    Utils u;

    public NewState(String string, Activity a){
        activity = a;
        s=string;
    }

    /** Moves a piece by its cId. */
    void movePiece(String cId){
        String[] cIdArray = cId.split(":");

        int x = cIdArray[0].charAt(0) - 48;
        int y = cIdArray[0].charAt(1) - 48;

        Piece piece;
        if(u.findPieceById(Integer.parseInt(cIdArray[1])) != null){
            piece = u.findPieceById(Integer.parseInt(cIdArray[1]));
        }
        else {
            piece = u.findPieceById(u.currentId(Integer.parseInt(cIdArray[1])));
        }
        piece.setId(Integer.parseInt(cIdArray[1]));


        // If the id is not standart then
/*        if(piece.getId()/100!=0){
            if(piece.getId()%1000==1){piece.setImageResource(R.drawable.wrook);}
            if(piece.getId()%1000==2){piece.setImageResource(R.drawable.wknight);}
            if(piece.getId()%1000==3){piece.setImageResource(R.drawable.wbishop);}
            if(piece.getId()%1000==4){piece.setImageResource(R.drawable.wqueen);}
            if(piece.getId()%1000==17){piece.setImageResource(R.drawable.brook);}
            if(piece.getId()%1000==18){piece.setImageResource(R.drawable.bknight);}
            if(piece.getId()%1000==19){piece.setImageResource(R.drawable.bbishop);}
            if(piece.getId()%1000==20){piece.setImageResource(R.drawable.bqueen);}
        }
*/

/*
        // To be able to load a game from a game where pieces have been removed.
        rl.removeView(piece);
        rl.addView(piece);
*/
        piece.x = x;
        piece.y = y;
        if(x==0&&y==0){
//            rl.removeView(piece);
            piece.setVisibility(View.INVISIBLE);
        }
        else {
            piece.setLayoutParams(u.getPlaceParams(x, y));
            piece.setVisibility(View.VISIBLE);
        }
    }

    void createNewState(){

        TextView turnNotifier = (TextView)activity.findViewById(R.id.turnNotifier);

        Vibrator v = (Vibrator) activity.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        // If vibrate is on
        if(Game.vibrate) {
            // Vibrate for 500 milliseconds
            v.vibrate(50);
        }


        RelativeLayout rl = (RelativeLayout) activity.findViewById(R.id.fragment);
        for (int id = 100; id < 7000; id += 100) {
            rl.removeView(activity.findViewById(id));
        }

        u= new Utils(activity);

        String[] state = s.split(";");

        if(state[1].equals("white")){
            Game.color = 1;
        }
        if(state[1].equals("black")){
            Game.color = -1;
        }

        if(state[2].equals("myTurn")){
            Game.myTurn = true;
        }
        if(state[2].equals("opTurn")){
            Game.myTurn = false;
        }

        if(state[3].equals("false")) Game.castlingRook1 = false;
        if(state[3].equals("true")) Game.castlingRook1 = true;
        if(state[4].equals("false")) Game.castlingRook2 = false;
        if(state[4].equals("true")) Game.castlingRook2 = true;

//        System.out.println("state: " + s);

        if(state.length>5 && !state[5].equals(".")){
            String[] ids = state[5].split(",");
            Game.enPassant1 = Integer.parseInt(ids[0]);
            if(ids.length==2) Game.enPassant2 = Integer.parseInt(ids[1]);
        }

        if(state.length>6 && !state[6].equals("0")){
            Game.enPassantXCoor = Integer.parseInt(state[6]);
        }

        String[] array = state[0].split(",");
//        System.out.println(state[0]);

        for (int i = 0; i < array.length; i++) {
            movePiece(array[i]);
//            System.out.println(array[i]);
        }

//        System.out.println("Game.myTurn: " + Game.myTurn);
        // Notify turn and in-check status.
        if(Game.myTurn) {
//            System.out.println("my turn");
            if (u.myKingInCheck()) {
 //               System.out.println("my turn: in check");
                // Player's king in check - show that is player's turn and that the king is in check.
                Game.kingInCheck = true;
                if (Game.color == 1) {
//                    System.out.println("game color: white");
                    try {
                        turnNotifier.setText(R.string.white_you_in_check);
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
//                    System.out.println("notifier text has been set");
                } else {
                    turnNotifier.setText(R.string.black_you_in_check);
                }
            } else {
//                System.out.println("my turn: not in check");
                // Player's king not in check - show that it is player's turn.
                turnNotifier.setText(R.string.player_turn);
            }
        }
        else {
            // Notify opponent's turn and whether opponent is in check.
            if (u.opponentKingInCheck()) {
                Game.kingInCheck = true;
                if (Game.color == 1) {
                    turnNotifier.setText(R.string.black_opponent_in_check);
                } else {
                    turnNotifier.setText(R.string.white_opponent_in_check);
                }
            } else {
                // Opponent not in check.
                turnNotifier.setText(R.string.opponent_turn);
            }
        }

        // Run the MyKingInCheckmate thread in case its chake mate
        MyKingInMateThread myKingInMateThreadThread = new MyKingInMateThread(activity);
        myKingInMateThreadThread.start();


/*
        // If its a turn received and not a game that is being loaded.
        if(state[1].equals("null")&&state[2].equals("null")) {
            // Run the OpponentKingInCheckmate thread in case its chake mate
            OpponentKingInMateThread opponentKingInCheckmateThreadThread = new OpponentKingInMateThread(activity);
            opponentKingInCheckmateThreadThread.start();
        }
*/

    }
}
