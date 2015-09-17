package io.dehaas.chesser;


import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Thread that runs myKingInCheckmate.
 */
public class MyKingInCheckmateThread extends Thread {

    Activity activity;

    public MyKingInCheckmateThread(Activity a){
        activity = a;
    }

    Utils u = new Utils(activity);

    public void run() {
        if(Game.kingInCheck) {

            System.out.println(Game.kingInCheck);

            if (u.myKingInCheckmate()) {
                activity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                // After a delay show the checkmate lost sign
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        Game.myTurn = false;
                                        LinearLayout lostLayout = (LinearLayout) activity.findViewById(R.id.lostLayout);
                                        lostLayout.setVisibility(View.VISIBLE);
                                        lostLayout.bringToFront();

                                        TextView turnNotifier = (TextView)activity.findViewById(R.id.turnNotifier);
                                        if (Game.color == 1) {
                                            turnNotifier.setText(R.string.white_you_is_mated);
                                        } else {
                                            turnNotifier.setText(R.string.black_you_is_mated);
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