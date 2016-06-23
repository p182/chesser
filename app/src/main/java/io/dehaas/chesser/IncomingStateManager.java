package io.dehaas.chesser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;

/**
 * Manage incoming state
 */
public class IncomingStateManager {
    Activity activity;
    boolean isStopped;
    Utils utils;

    public IncomingStateManager(Activity a) {
        activity = a;
        isStopped = true;
        utils = new Utils(activity);
    }

    /* Identifies string and calls corresponding method. */
    void manageState(final String incoming) {
        utils.activity = activity;

        System.out.println("string received: " + incoming);

        String[] commands = incoming.split(":");
        // if incoming string is message
        if (commands[0].equals("message")) {
            switch (commands[1]) {
                case "restartRequest": restartRequest(); break;
                case "restartAccepted": restartAccepted(); break;
                case "undoRequest": undoRequest(); break;
                case "undoAccepted": undoAccepted(); break;
            }
        }
        // not message
        else {
            newState(incoming);
        }
    }

    void newState(final String stateString) {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        Game.myTurn = true;
                        Game.menu.findItem(R.id.action_undo).setEnabled(false);
                        NewState state = new NewState(stateString, activity);
                        state.createNewState();

                        // Save the current state in auto save received.
                        ChesserDbOperations mDbHelper = new ChesserDbOperations(activity);
                        mDbHelper.autoSave(activity.getResources().getText(R.string.autosave_received).toString());


                        Chronometer playerClock = (Chronometer) activity.findViewById(R.id.playerClock);
                        Chronometer oppClock = (Chronometer) activity.findViewById(R.id.oppClock);
                        playerClock.start();
                        oppClock.stop();
                        oppClock.setAlpha(0.4f);
                        playerClock.setAlpha(1f);
                    }
                }
        );
    }

    void restartRequest() {
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
    }

    void restartAccepted() {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        LinearLayout gameFinishedLayout = (LinearLayout) activity.findViewById(R.id.gameFinishedLayout);
                        gameFinishedLayout.setVisibility(View.INVISIBLE);
                        activity.startActivity(new Intent("chess.game"));
                    }
                }
        );
    }

    void undoRequest() {
        activity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(activity)
                                .setTitle("Do you want to grant your opponent permission to undo there move?")
                                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Game.connectedThread.stringWrite("message:undoAccepted");
                                        // set the previous state
                                        utils.setStateByName(":autosave:last_sent");
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
    }

    void undoAccepted(){
        // set the previous state
        utils.setStateByName(":autosave:last_received");
    }


}