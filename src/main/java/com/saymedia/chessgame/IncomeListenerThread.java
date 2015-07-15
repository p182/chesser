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

        while(true){
            if(!s.equals(string)){
                System.out.println(s);
                activity.runOnUiThread(
                        new Runnable () {
                            public void run() {
                                NewState state = new NewState(s,activity);
                                state.creatNewState();
                            }
                        }
                );
                break;
            }
        }

    }
}
