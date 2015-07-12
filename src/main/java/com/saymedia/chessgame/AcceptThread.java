package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by SayMedia on 07/07/2015.
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
            // MY_UUID is the app's UUID string, also used by the client code
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
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                System.out.println(e);
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                OpenServer.socket=socket;

                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        TextView textView2 = (TextView) activity.findViewById(R.id.textView2);
                        textView2.setVisibility(View.VISIBLE);
                        Button button = (Button) activity.findViewById(R.id.button4);
                        button.setVisibility(View.VISIBLE);
                    }
                });

                System.out.println("got socket");
                Game.socket=socket;
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