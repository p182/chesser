package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;

/**
 * Created by SayMedia on 12/07/2015.
 */
public class IncomeListenerThread extends Thread {

    public static String s = "";
    Activity activity;

    public IncomeListenerThread(Activity a){
          activity = a;
    }

    public void run() {
        String string = s;

        while(true) {
            while (true) {
//                System.out.println("waiting");
                if (!s.equals(string)) {
                    Game.myTurn = true;
                    System.out.println(s);
                    activity.runOnUiThread(
                            new Runnable() {
                            public void run() {
                                    NewState state = new NewState(s, activity);
                                    state.creatNewState();
                                }
                        }
                    );
                    break;
                }
            }
            string = s;
        }
    }
}
