package io.dehaas.chesser;


import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

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

                                    }

                                }, 500); // delay
                            }
                        }
                );
            }

    }
}