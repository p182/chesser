package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Vibrator;
import android.widget.RelativeLayout;

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

    void movePiece(String cId){
        String[] cIdArray = cId.split(":");

        int x = cIdArray[0].charAt(0) - 48;
        int y = cIdArray[0].charAt(1) - 48;

        Piece piece = u.findPieceById(Integer.parseInt(cIdArray[1]));

        System.out.println(piece);
        piece.x = x;
        piece.y = y;
        if(x==0&&y==0){

            RelativeLayout rl = (RelativeLayout)activity.findViewById(R.id.fragment);
            rl.removeView(piece);
        }
        else {
            piece.setLayoutParams(u.getPlaceParams(x, y));
        }
    }

    void creatNewState(){
        Vibrator v = (Vibrator) activity.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(50);
        u= new Utils(activity);
        System.out.println(s);

        String[] array = s.split(",");

        System.out.println(array[0]);
//        String[] warray = array[0].split(",");
//        String[] barray = array[1].split(",");
        for (int i = 0; i < array.length; i++) {

            System.out.println(array[i]);

            movePiece(array[i]);
        }
//        for (int i = 0; i < barray.length; i++) {
//            movePiece(barray[i-1]);
//        }
    }
}
