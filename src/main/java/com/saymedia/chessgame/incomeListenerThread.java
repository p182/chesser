package com.saymedia.chessgame;

import android.bluetooth.BluetoothSocket;

/**
 * Created by SayMedia on 12/07/2015.
 */
public class incomeListenerThread extends Thread {
    private final BluetoothSocket mmSocket;

    public incomeListenerThread(BluetoothSocket socket){
        mmSocket=socket;
    }

    public void run() {
        
    }
}
