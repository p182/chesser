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

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * UI to connect to a Bluetooth server.
 */
public class ConnectToServer extends ActionBarActivity {
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_server);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        loadActivity();
    }

    void loadActivity(){
        Button turnOnButton = (Button) findViewById(R.id.turnOnBluetooth);
        TextView turnOnButtonTxt = (TextView) findViewById(R.id.textView15);
        TextView bluetoothSettings = (TextView) findViewById(R.id.textView17);
        TextView textView18 = (TextView) findViewById(R.id.textView18);
        turnOnButton.setVisibility(View.INVISIBLE);
        turnOnButtonTxt.setVisibility(View.INVISIBLE);
        bluetoothSettings.setVisibility(View.VISIBLE);
        textView18.setVisibility(View.VISIBLE);

        final ListView listview = (ListView) findViewById(R.id.listView);

        final ArrayList<String> list = new ArrayList<>();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if(mBluetoothAdapter.isEnabled()) {
            for (BluetoothDevice device : pairedDevices) {
                String btName = device.getName();
                if (device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.PHONE) {
//                    list.add(btName+"  --  "+device.toString());
                    list.add(btName);
                }
            }
            final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_list_item, list);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new ListView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                    itemOnClick(position);
                }
            });
        }
        else {
            turnOnButton.setVisibility(View.VISIBLE);
            turnOnButtonTxt.setVisibility(View.VISIBLE);
            bluetoothSettings.setVisibility(View.INVISIBLE);
            textView18.setVisibility(View.INVISIBLE);
        }
    }

    public void itemOnClick(int i) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        BluetoothDevice[] btarray = new BluetoothDevice[pairedDevices.size()];

        if (pairedDevices.size() > 0) {
            int in = 0;
            for (BluetoothDevice device : pairedDevices) {
                if (device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.PHONE) {
                    btarray[in] = device;
                    in++;
                }
            }
        }

        BluetoothDevice device = btarray[i];

        ConnectThread connectThread = new ConnectThread(device, this);
        connectThread.start();
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
            loadActivity();
        }
    }

    public void openBluetoothSettings(View v){
        Intent settingsIntent = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(settingsIntent);
    }
}
