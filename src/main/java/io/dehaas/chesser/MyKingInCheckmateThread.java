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