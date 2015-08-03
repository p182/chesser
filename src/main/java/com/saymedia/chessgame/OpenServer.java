package com.saymedia.chessgame;

import android.bluetooth.BluetoothSocket;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *  UI to activate the listener on AcceptThread server.
 */
public class OpenServer extends ActionBarActivity {

    public static BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_server);
    }



    public void startServer(View v) {
        AcceptThread acceptThread = new AcceptThread(this);
        System.out.println("Starting");
        acceptThread.start();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.VISIBLE);
    }
}