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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Checks if new state string arrived and passes it on to NewState.
 */
public class IncomingStateListenerThread extends Thread {

    public static String s = "";
    Activity activity;
    boolean run = true;

    public IncomingStateListenerThread(Activity a){
          activity = a;
        System.out.println("IncomingStateListenerThread: " + this + "created");
        System.out.println("Activity: " + activity);
    }

    public void run() {
        System.out.println("IncomingStateListenerThread: listening...");
        while(run) {
            String string = s;
            System.out.println(s +" , "+ string);
            System.out.println(!s.equals(string));
            while (true) { // listen for incoming string
                if (!s.equals(string)) {
                    System.out.println("String incoming NOW!");
                    String[] incomingString = s.split(":");
                    // if incoming string is message
                    if(incomingString[0].equals("message")){
                        s = ""; // reset s for next incoming message
                        switch (incomingString[1]){
                            case "restartRequest":
                                System.out.println("Restart request");
                                activity.runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                                new AlertDialog.Builder(activity)
                                                        .setTitle("Do you want to start a new game?")
                                                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                LinearLayout gameFinishedLayout = (LinearLayout) activity.findViewById(R.id.gameFinishedLayout);
                                                                gameFinishedLayout.setVisibility(View.INVISIBLE);
                                                                Game.connectedThread.stringWrite("message:restartAccepted");
                                                                activity.startActivity(new Intent("chess.game"));
                                                            }
                                                        })
                                                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                // Do nothing
                                                            }
                                                        })
                                                        .create().show();
                                            }
                                        }
                                );
                                break;
                            case "restartAccepted":

                                activity.runOnUiThread(
                                        new Runnable() {
                                            public void run() {
                                                LinearLayout gameFinishedLayout = (LinearLayout) activity.findViewById(R.id.gameFinishedLayout);
                                                gameFinishedLayout.setVisibility(View.INVISIBLE);
                                                activity.startActivity(new Intent("chess.game"));
                                            }
                                        }
                                );
                                break;
                        }
                    }
                    // not message
                    else {
                        Game.myTurn = true;
                        activity.runOnUiThread(
                                new Runnable() {
                                    public void run() {
/*
                                        TextView turnNotifier = (TextView)activity.findViewById(R.id.turnNotifier);
                                        System.out.println("seting text for test");
                                        turnNotifier.setText("Why, shan't you work?");
*/
                                        NewState state = new NewState(s, activity);
                                        state.createNewState();
                                        //s = "";

                                        // Save the current state in auto save received.
                                        ChesserDbOperations mDbHelper = new ChesserDbOperations(activity);
                                        mDbHelper.autoSave(activity.getResources().getText(R.string.autosave_received).toString());
                                    }
                                }
                        );
                    }
                    break; // from listening while
                }
                try {
                    this.sleep(400);
//                    System.out.println("waiting");
                }
                catch (java.lang.InterruptedException e){
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
