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
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Waits for a Bluetooth socket call from a client and passes it on to Game.
 */

public class AcceptThread extends Thread {

    public Activity activity;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothServerSocket mmServerSocket;

    String NAME = "game";
    UUID MY_UUID = UUID.fromString("427c2fb7-c2a3-4d25-ac0d-81dc6a77525a");

    public AcceptThread(Activity _activity) {
        this.activity = _activity;

        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app'movesCoor UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public void run() {
        System.out.println("thread is running");

        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned

        while (true) {
            try {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        TextView textView = (TextView) activity.findViewById(R.id.textView);
                        textView.setVisibility(View.VISIBLE);
                    }
                });

                socket = mmServerSocket.accept();

            } catch (IOException e) {
                System.out.println(e);
                break;
            } catch (NullPointerException e) {

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(activity, R.string.bluetooth_off,
                                Toast.LENGTH_LONG).show();

                        TextView textView = (TextView) activity.findViewById(R.id.textView);
                        textView.setVisibility(View.INVISIBLE);
                    }
                });

                break;
            } catch (Exception e){
                Toast.makeText(activity, e.toString(),Toast.LENGTH_LONG).show();
            }

            // If a connection was accepted
            if (socket != null) {
                OpenServer.socket=socket;

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        TextView textView2 = (TextView) activity.findViewById(R.id.textView2);
                        textView2.setVisibility(View.VISIBLE);
                    }
                });

                System.out.println("got socket");
                Game.socket=socket;
                Game.color=1;
                activity.startActivity(new Intent("chess.game"));

                // Do work to manage the connection (in a separate thread)
                try{mmServerSocket.close();}
                catch (IOException e){}
                break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }

}