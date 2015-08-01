package com.saymedia.chessgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;

/**
 * Helps generate a new state.
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

        Vibrator v = (Vibrator) activity.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // If vibrate is on
        if(Game.vibrate) {
            // Vibrate for 500 milliseconds
            v.vibrate(50);
        }

        RelativeLayout rl = (RelativeLayout)activity.findViewById(R.id.fragment);

        String[] cIdArray = cId.split(":");

        int x = cIdArray[0].charAt(0) - 48;
        int y = cIdArray[0].charAt(1) - 48;

        Piece piece = u.findPieceById(Integer.parseInt(cIdArray[1]));

        // To be able to load a game from a game where pieces have been removed.
        rl.removeView(piece);
        rl.addView(piece);

        piece.x = x;
        piece.y = y;
        if(x==0&&y==0){
            rl.removeView(piece);
        }
        else {
            piece.setLayoutParams(u.getPlaceParams(x, y));
        }
    }

    void creatNewState(){

        u= new Utils(activity);
        System.out.println("movesCoor: " + s);

        String[] message = s.split(";");

//        System.out.println(array[0]);
/*
        // If both players a well synchronized
        if(array[0].equals(Game.getWPCIDs() +","+ Game.getBPCIDs())){
            movePiece(array[1]);
        }
        else{
            final AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("Error").setMessage("Opponent game not synchronized.").show();
        }
*/
        String[] array = message[0].split(",");

        for (int i = 0; i < array.length; i++) {

//

            movePiece(array[i]);
        }


        if(u.checkIfKingIsInDanger()){
            Game.kingInDanger=true;
            System.out.println("KINH IN DANGERRR");
        }


    }
}
