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


public class OpenServer extends ActionBarActivity {

    public static BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_server);
    }


    AcceptThread acceptThread = new AcceptThread(this);

    public void startServer(View v) {
        acceptThread.start();
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.VISIBLE);
    }



    public void sendMessage(View v) {
        ConnectedThread ct = new ConnectedThread(socket);
        String s = "Hello :_: hellO";
        ct.stringWrite(s);
    }
}