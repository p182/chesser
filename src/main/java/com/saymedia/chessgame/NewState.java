package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;

/**
 * Created by SayMedia on 13/07/2015.
 */
public class NewState{
    String s;
    Activity activity;
    Utils u;

    public NewState(String string, Activity a){
        activity = a;
        s=string;

        System.out.println(activity.getApplicationContext().getResources().getDisplayMetrics());
        System.out.println(u);
    }

    void movePiece(String c){
        int x = c.charAt(0) - 48;
        int y = c.charAt(1) - 48;

        Piece piece = u.findPieceByCoordinates(c);

        System.out.println(piece);
        piece.setLayoutParams(u.getPlaceParams(x, y));
    }

    void creatNewState(){
        u= new Utils(activity);
        System.out.println(s);

        String[] array = s.split(",");

        System.out.println(array[0]);
//        String[] warray = array[0].split(",");
//        String[] barray = array[1].split(",");
        for (int i = 0; i < array.length; i++) {
            movePiece(array[i]);
        }
//        for (int i = 0; i < barray.length; i++) {
//            movePiece(barray[i-1]);
//        }
    }
}
