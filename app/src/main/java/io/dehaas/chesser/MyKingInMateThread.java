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
 * Thread that runs myKingMate.
 */
public class MyKingInMateThread extends Thread {

    Activity activity;

    public MyKingInMateThread(Activity a){
        activity = a;
    }

    Utils u = new Utils(activity);

    public void run() {
//        System.out.println("myKingInMate thread started");
        if (u.myKingInMate()) {
//            System.out.println("king is in mate");
            activity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            // After a delay show the checkmate lost sign
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
//                                    System.out.println("showing lost sign");
                                    Game.piecesClickable = false;
                                    LinearLayout gameFinishedLayout = (LinearLayout) activity.findViewById(R.id.gameFinishedLayout);
                                    // Set the game finished dialog accordingly
                                    TextView title = (TextView)activity.findViewById(R.id.gameFinishedTitle);
                                    if (u.myKingInCheck()) title.setText(R.string.checkmate_lost);
                                    else title.setText(R.string.stalemate);
                                    TextView text = (TextView)activity.findViewById(R.id.gameFinishedText);
                                    if (u.myKingInCheck()) text.setText(R.string.lost);
                                    else text.setText(R.string.stalemate_lost);

                                    gameFinishedLayout.setVisibility(View.VISIBLE);
                                    gameFinishedLayout.bringToFront();

                                    TextView turnNotifier = (TextView) activity.findViewById(R.id.turnNotifier);

//                                    System.out.println("setting notifier");
                                    if (Game.color == 1) {
                                        if (u.myKingInCheck()) turnNotifier.setText(R.string.white_you_is_mated);
                                        else turnNotifier.setText(R.string.white_you_is_stalemated);
                                    } else {
                                        if (u.myKingInCheck()) turnNotifier.setText(R.string.black_you_is_mated);
                                        else turnNotifier.setText(R.string.black_you_is_stalemated);
                                    }

                                }

                            }, 500); // delay
                        }
                    }
            );
        }
//        System.out.println("myKingInMate thread completed");
    }
}