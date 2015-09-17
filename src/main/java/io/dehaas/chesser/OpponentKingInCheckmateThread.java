package io.dehaas.chesser;


import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Thread that runs opponentKingInCheckmate.
 */
public class OpponentKingInCheckmateThread extends Thread {

    Activity activity;

    public OpponentKingInCheckmateThread(Activity a){
        activity = a;
    }

    Utils u = new Utils(activity);

    public void run() {

        if(u.opponentKingInCheck()) {
            if (u.opponentKingInCheckmate()) {
                activity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                // After a delay show the checkmate won sign
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        LinearLayout wonLayout = (LinearLayout) activity.findViewById(R.id.wonLayout);
                                        wonLayout.setVisibility(View.VISIBLE);
                                        wonLayout.bringToFront();

                                        TextView turnNotifier = (TextView)activity.findViewById(R.id.turnNotifier);
                                        if (Game.color == 1) {
                                            turnNotifier.setText(R.string.black_opponent_is_mated);
                                        } else {
                                            turnNotifier.setText(R.string.white_opponent_is_mated);
                                        }


                                    }

                                }, 500); // delay
                            }
                        }
                );
            }
        }

    }
}