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
import android.os.Bundle;
import android.view.View;

/**
 *  UI to activate the listener on AcceptThread server.
 */
public class OpenServer extends Activity {

    public static AcceptThread acceptThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_server);
        acceptThread = new AcceptThread(this);
    }



    public void startServer(View v) {
        System.out.println(acceptThread);
//        if(acceptThread==null)

        acceptThread = new AcceptThread(this);
        if(!acceptThread.isAlive()) {
            System.out.println("Starting");
            acceptThread.start();
        }
    }

    @Override
    public void onBackPressed() {
        if(acceptThread!=null) acceptThread.cancel();
        super.onBackPressed();
    }
}