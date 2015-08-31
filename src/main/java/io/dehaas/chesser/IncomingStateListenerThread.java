package io.dehaas.chesser;

import android.app.Activity;

import java.io.IOException;

/**
 * Checks if new state string arrived and passes it on to NewState.
 */
public class IncomingStateListenerThread extends Thread {

    public static String s = "";
    Activity activity;
    boolean run = true;

    public IncomingStateListenerThread(Activity a){
          activity = a;
    }

    public void run() {
        System.out.println("IncomingStateListenerThread: listening...");
        while(run) {
            final String string = s;
                    while (true) {
                        if (!s.equals(string)) {
                            Game.myTurn = true;
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
                                    state.createNewState();
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


    /** Will cancel the listening, and cause the thread to finish */
    public void cancel() {
        run = false;
    }
}
