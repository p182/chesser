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
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * UI to connect to a Bluetooth server.
 */
public class ConnectToServer extends ActionBarActivity {

    public static BluetoothSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_server);

        final ListView listview = (ListView) findViewById(R.id.listView);



        final ArrayList<String> list = new ArrayList<>();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String btName = device.getName();
                if (device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.PHONE) {
//                    list.add(btName+"  --  "+device.toString());
                    list.add(btName);
                }
            }
        }
        else{
            Toast.makeText(ConnectToServer.this, R.string.bluetooth_off, Toast.LENGTH_LONG).show();
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.custom_list_item, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                itemOnClick(position);
            }
        });

    }

    public  void itemOnClick(int i){
//        System.out.println(i);

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        BluetoothDevice[] btarray = new BluetoothDevice[pairedDevices.size()];

        if (pairedDevices.size() > 0) {
            int in=0;
            for (BluetoothDevice device : pairedDevices) {
                if (device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.PHONE) {
                    btarray[in] = device;
                    in++;
                }
            }
        }

        BluetoothDevice device = btarray[i];

        ConnectThread connectThread = new ConnectThread(device,this);
        connectThread.start();

//        System.out.println(device.getName());

    }


}
