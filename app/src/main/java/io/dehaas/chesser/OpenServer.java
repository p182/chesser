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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 *  UI to activate the listener on AcceptThread server.
 */
public class OpenServer extends Activity {

    public static AcceptThread acceptThread;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_server);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        acceptThread = new AcceptThread(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Button openServer = (Button) findViewById(R.id.button3);
        Button turnBluetoothOn = (Button) findViewById(R.id.button6);
        TextView turnBluetoothOnTxt = (TextView) findViewById(R.id.textView16);
        if(!mBluetoothAdapter.isEnabled()){
            openServer.setVisibility(View.INVISIBLE);
            turnBluetoothOn.setVisibility(View.VISIBLE);
            turnBluetoothOnTxt.setVisibility(View.VISIBLE);
        }
    }

    public void startServer(View v) {
        System.out.println(acceptThread);
//        if(acceptThread==null)

        //acceptThread = new AcceptThread(this);
        if(acceptThread.getState() == Thread.State.NEW) {
            System.out.println("Starting");
            acceptThread.start();
        }
    }

    @Override
    public void onBackPressed() {
        if(acceptThread!=null) acceptThread.cancel();
        super.onBackPressed();
    }

    public void turnOnBluetooth(View v) {
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);

            System.out.println("enabling bluetooth");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if(requestCode == 1 && resultCode==RESULT_OK){
            while(!mBluetoothAdapter.isEnabled()){}
            Button openServer = (Button) findViewById(R.id.button3);
            Button turnBluetoothOn = (Button) findViewById(R.id.button6);
            TextView turnBluetoothOnTxt = (TextView) findViewById(R.id.textView16);
            openServer.setVisibility(View.VISIBLE);
            turnBluetoothOn.setVisibility(View.INVISIBLE);
            turnBluetoothOnTxt.setVisibility(View.INVISIBLE);
        }
    }
}