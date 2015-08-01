package com.saymedia.chessgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by SayMedia on 08/07/2015.
 */
public class ConnectThread extends Thread {
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    public Activity activity;

    UUID MY_UUID = UUID.fromString("427c2fb7-c2a3-4d25-ac0d-81dc6a77525a");

    public ConnectThread(BluetoothDevice device,Activity _activity) {
        this.activity = _activity;

        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;

        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app'movesCoor UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) { }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            System.out.println("Cannot connect");
            try {
                mmSocket.close();
            } catch (IOException closeException) { }
            return;
        }

        Game.socket=mmSocket;
        Game.color=-1;
        activity.startActivity(new Intent("chess.game"));


        // Do work to manage the connection (in a separate thread)
//        manageConnectedSocket(mmSocket);
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}