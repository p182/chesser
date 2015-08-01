package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.widget.Button;

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
//                            System.out.println(movesCoor);
                            activity.runOnUiThread(
                               new Runnable() {
                                public void run() {

                                    Button turnNotifier = (Button)activity.findViewById(R.id.turnNotifier);

                                    turnNotifier.setText("Your Turn");
                                    turnNotifier.setClickable(false);
                                    turnNotifier.setEnabled(true);

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
