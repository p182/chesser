package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;

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

        while(true) {
            final String string = s;
                    while (true) {
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
                        try {
                            this.sleep(400);
//                            System.out.println("waiting");
                        } catch (java.lang.InterruptedException e){
                            System.out.println("Exeption: " + e);
                        }
                    }
        }
    }
}
