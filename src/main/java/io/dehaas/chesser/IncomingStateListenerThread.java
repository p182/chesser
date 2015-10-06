// Copyright 2015 Roni Harel
//
// This file is part of Chesser.
//
// Chesser is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Chesser is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Chesser.  If not, see <http://www.gnu.org/licenses/>.

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

                                    // Save the current state in auto save received.
                                    ChesserDbOperations mDbHelper = new ChesserDbOperations(activity);
                                    mDbHelper.autoSave(activity.getResources().getText(R.string.autosave_received).toString());
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
