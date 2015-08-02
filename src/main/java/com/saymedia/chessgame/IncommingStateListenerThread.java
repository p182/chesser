package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.widget.Button;

/**
 * Checks if new state string arrived and passes it on to NewState.
 */
public class IncommingStateListenerThread extends Thread {

    public static String s = "";
    Activity activity;

    public IncommingStateListenerThread(Activity a){
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
/*

                                    Button turnNotifier = (Button)activity.findViewById(R.id.turnNotifier);

                                    turnNotifier.setText("Your Turn");
                                    turnNotifier.setClickable(false);
                                    turnNotifier.setEnabled(true);
*/

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
