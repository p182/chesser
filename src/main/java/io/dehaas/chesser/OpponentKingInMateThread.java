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
 * Thread that runs opponentKingInMate.
 */
public class OpponentKingInMateThread extends Thread {

    Activity activity;

    public OpponentKingInMateThread(Activity a){
        activity = a;
    }

    Utils u = new Utils(activity);

    public void run() {
        if (u.opponentKingInMate()) {
            activity.runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            // After a delay show the checkmate won sign
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    LinearLayout gameFinishedLayout = (LinearLayout) activity.findViewById(R.id.gameFinishedLayout);
                                    // Set the game finished dialog accordingly
                                    TextView title = (TextView) activity.findViewById(R.id.gameFinishedTitle);
                                    if (u.opponentKingInCheck()) title.setText(R.string.checkmate_won);
                                    else title.setText(R.string.stalemate);
                                    TextView text = (TextView) activity.findViewById(R.id.gameFinishedText);
                                    if (u.opponentKingInCheck()) text.setText(R.string.won);
                                    else text.setText(R.string.stalemate_won);

                                    gameFinishedLayout.setVisibility(View.VISIBLE);
                                    gameFinishedLayout.bringToFront();

                                    TextView turnNotifier = (TextView) activity.findViewById(R.id.turnNotifier);
                                    if (Game.color == 1) {
                                        if (u.opponentKingInCheck())
                                            turnNotifier.setText(R.string.black_opponent_is_mated);
                                        else
                                            turnNotifier.setText(R.string.black_opponent_is_stalemated);
                                    } else {
                                        if (u.opponentKingInCheck())
                                            turnNotifier.setText(R.string.white_opponent_is_mated);
                                        else
                                            turnNotifier.setText(R.string.white_opponent_is_stalemated);
                                    }


                                }

                            }, 500); // delay
                        }
                    }
            );
        }
    }
}