package com.saymedia.chessgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class OpenServer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_server);
    }


    AcceptThread acceptThread = new AcceptThread(this);
    public static boolean b = false;

    public void startServer(View v){
        acceptThread.start();
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setVisibility(View.VISIBLE);
//        connected();
    }

    public void waitingForSocket(){
    }

    public void connected(){
        while (true){
            if(b){
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setVisibility(View.VISIBLE);
            }
        }
    }
}
