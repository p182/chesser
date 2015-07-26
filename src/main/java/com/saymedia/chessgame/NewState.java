package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;

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

        System.out.println(activity.getApplicationContext().getResources().getDisplayMetrics());
        System.out.println(u);
    }

    /** Moves a piece by its cId. */
    void movePiece(String cId){
        String[] cIdArray = cId.split(":");

        int x = cIdArray[0].charAt(0) - 48;
        int y = cIdArray[0].charAt(1) - 48;

        Piece piece = u.findPieceById(Integer.parseInt(cIdArray[1]));

//        Game.

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

        for (int i = 0; i < array.length; i++) {

            System.out.println(array[i]);

            movePiece(array[i]);
        }
    }

/*
     void createPiece(int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)activity.findViewById(R.id.fragment);
        Piece p = new Piece(activity.getApplicationContext(), x, y, id);

        p.setOnClickListener(new Piece.OnClickListener() {
            public void onClick(View v) {
                Game.piecesOnClick(v);
            }
        });
        p.setLayoutParams(u.getPlaceParams(x, y));
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
*/

}
