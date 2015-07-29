package com.saymedia.chessgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Set;


public class Game extends ActionBarActivity {

    public static BluetoothSocket socket;
    ConnectedThread connectThread;
    IncomeListenerThread IncomeListenerThread = new IncomeListenerThread(this);
    Utils u = new Utils(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ConnectedThread tmp = new ConnectedThread(socket);
        connectThread=tmp;
        connectThread.start();
        IncomeListenerThread.start();

        setupPieces();

        if(color==1){ myTurn=true; }
        else{ myTurn=false; }

        CheckBox c = (CheckBox)findViewById(R.id.vibrateCB);
        c.setChecked(true);
        c = (CheckBox)findViewById(R.id.soundCB);
        c.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save||id == R.id.action_load) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ChesserDbOperations mDbHelper = new ChesserDbOperations(this);

    public static int color = 1;

    public static Boolean kingInDanger;

    public static Boolean myTurn;
    public static Boolean vibrate = true;
    public static Boolean sound = true;;

    public static Piece pressed;

    public static Piece wr1 , br1;
    public static Piece wr2 , br2;
    public static Piece wn1 , bn1;
    public static Piece wn2 , bn2;
    public static Piece wb1 , bb1;
    public static Piece wb2 , bb2;
    public static Piece wk , bk;
    public static Piece wq , bq;
    public static Piece wp1 , bp1;
    public static Piece wp2 , bp2;
    public static Piece wp3 , bp3;
    public static Piece wp4 , bp4;
    public static Piece wp5 , bp5;
    public static Piece wp6 , bp6;
    public static Piece wp7 , bp7;
    public static Piece wp8 , bp8;

    public void removeAponnent(){

        String[] apponentc;

        // If the player is white
        if(color==1) {
            apponentc = new String[] {br1.c(), br2.c(), bn1.c(), bn2.c(), bb1.c(), bb2.c(), bk.c(), bq.c(),
                    bp1.c(), bp2.c(), bp3.c(), bp4.c(), bp5.c(), bp6.c(), bp7.c(), bp8.c()};
        }
        // If the player is black
        else {
            apponentc = new String[] {wr1.c(), wr2.c(), wn1.c(), wn2.c(), wb1.c(), wb2.c(), wk.c(), wq.c(),
                    wp1.c(), wp2.c(), wp3.c(), wp4.c(), wp5.c(), wp6.c(), wp7.c(), wp8.c()};
        }

        String s = pressed.c();
        for(int t=0; t<apponentc.length; t++){
            if (s.equals(apponentc[t])){
                Piece p = u.findPieceByCoordinates(apponentc[t]);
                System.out.println(p);

//                p.setImageResource(R.drawable.knight);
                p.x = 0;
                System.out.println(p);
                p.y = 0;
                RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
                rl.removeView(p);

            }
        }
    }

    /** Returns a string of all the white pieces coordinates+IDs. */
    public static String getWPCIDs(){
        String wc = wr1.cId()+","+wr2.cId()+","+wn1.cId()+","+wn2.cId()+","+wb1.cId()+","+wb2.cId()+","+wk.cId()+","+wq.cId()+
                ","+wp1.cId()+","+wp2.cId()+","+wp3.cId()+","+wp4.cId()+","+wp5.cId()+","+wp6.cId()+","+wp7.cId()+","+wp8.cId();

        return wc;
    }

    /** Returns a string of all the black pieces coordinates+IDs. */
    public static String getBPCIDs(){
        String bc = br1.cId()+","+br2.cId()+","+bn1.cId()+","+bn2.cId()+","+bb1.cId()+","+bb2.cId()+","+bk.cId()+","+bq.cId()+
                ","+bp1.cId()+","+bp2.cId()+","+bp3.cId()+","+bp4.cId()+","+bp5.cId()+","+bp6.cId()+","+bp7.cId()+","+bp8.cId();

        return bc;
    }

    /** Returns an array of two booleans witch indicates if there is a Piece on the given coordinate. */
    public static boolean[] checkCoordinate(String s){
        String[] playerCoordinates;
        String[] opponentCoordinates;

        // If the player is white
        if(color==1) {
            playerCoordinates = new String[] {wr1.c(), wr2.c(), wn1.c(), wn2.c(), wb1.c(), wb2.c(), wk.c(), wq.c(),
                    wp1.c(), wp2.c(), wp3.c(), wp4.c(), wp5.c(), wp6.c(), wp7.c(), wp8.c()};

            opponentCoordinates = new String[] {br1.c(), br2.c(), bn1.c(), bn2.c(), bb1.c(), bb2.c(), bk.c(), bq.c(),
                    bp1.c(), bp2.c(), bp3.c(), bp4.c(), bp5.c(), bp6.c(), bp7.c(), bp8.c()};
        }
        // If the player is black
        else{
            playerCoordinates = new String[] {br1.c(), br2.c(), bn1.c(), bn2.c(), bb1.c(), bb2.c(), bk.c(), bq.c(),
                    bp1.c(), bp2.c(), bp3.c(), bp4.c(), bp5.c(), bp6.c(), bp7.c(), bp8.c()};

            opponentCoordinates = new String[] {wr1.c(), wr2.c(), wn1.c(), wn2.c(), wb1.c(), wb2.c(), wk.c(), wq.c(),
                    wp1.c(), wp2.c(), wp3.c(), wp4.c(), wp5.c(), wp6.c(), wp7.c(), wp8.c()};
        }

        boolean p = true;
        boolean o = true;

        for(int t=0; t<playerCoordinates.length; t++){
            if (s.equals(playerCoordinates[t])){
                p=false;
            }
        }
        for(int t=0; t<opponentCoordinates.length; t++){
            if (s.equals(opponentCoordinates[t])){
                o=false;
            }
        }

        boolean[] B = {p,o};
        return B;
    }

    public void selectSquares(String s, Piece p){
        if(!s.equals("")) {
            String[] array = s.split(",");

            for (int i = 0; i < array.length; i++) {
                String pars = array[i];
                int x = pars.charAt(0) - 48;
                int y = pars.charAt(1) - 48;
                creatSelectedSquare(x, y, (i + 1) * 100);
            }
        }

        creatSelectedSquare(p.x, p.y, 65 * 100);
        creatSelectedSquare(p.x, p.y, 66 * 100);
        p.bringToFront();
    }


    public void setupPieces(){
            creatPiece(1, 1, 1);
            creatPiece(2, 1, 2);
            creatPiece(3, 1, 3);
            creatPiece(4, 1, 4);
            creatPiece(5, 1, 5);
            creatPiece(6, 1, 6);
            creatPiece(7, 1, 7);
            creatPiece(8, 1, 8);
            for (int i = 1; i < 9; i++) {
                creatPiece(i, 2, (i + 8));
            }
            creatPiece(1, 8, 17);
            creatPiece(2, 8, 18);
            creatPiece(3, 8, 19);
            creatPiece(4, 8, 20);
            creatPiece(5, 8, 21);
            creatPiece(6, 8, 22);
            creatPiece(7, 8, 23);
            creatPiece(8, 8, 24);
            for (int i = 1; i < 9; i++) {
                creatPiece(i, 7, (i + 24));
            }
    }


    public void creatSelectedSquare(int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        SelectedSquare image = new SelectedSquare(getApplicationContext(), x, y, id);

        image.setLayoutParams(u.getPlaceParams(x, y));

        image.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        selectedOnClick(v);
                    }
                }
        );

        rl.addView(image);
    }


    public void creatPiece(int x, int y, int id){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        Piece p = new Piece(getApplicationContext(), x, y, id);

        p.setOnClickListener(new Piece.OnClickListener() {
            public void onClick(View v) {
                piecesOnClick(v);
            }
        });
        p.setLayoutParams(u.getPlaceParams(x, y));
        rl.addView(p);


        switch (id){
        case 1: wr1 = p; break;
        case 2: wn1 = p; break;
        case 3: wb1 = p; break;
        case 4: wq = p; break;
        case 5: wk = p; break;
        case 6: wb2 = p; break;
        case 7: wn2 = p; break;
        case 8: wr2 = p; break;
        case 9: wp1 = p; break;
        case 10: wp2 = p; break;
        case 11: wp3 = p; break;
        case 12: wp4 = p; break;
        case 13: wp5 = p; break;
        case 14: wp6 = p; break;
        case 15: wp7 = p; break;
        case 16: wp8 = p; break;

        case 17: br1 = p; break;
        case 18: bn1 = p; break;
        case 19: bb1 = p; break;
        case 20: bq = p; break;
        case 21: bk = p; break;
        case 22: bb2 = p; break;
        case 23: bn2 = p; break;
        case 24: br2 = p; break;
        case 25: bp1 = p; break;
        case 26: bp2 = p; break;
        case 27: bp3 = p; break;
        case 28: bp4 = p; break;
        case 29: bp5 = p; break;
        case 30: bp6 = p; break;
        case 31: bp7 = p; break;
        case 32: bp8 = p; break;
        }
    }


    public void backButton(View v){
        startActivity(new Intent("first.activity"));
    }


    public void piecesOnClick(View v){
        if(myTurn) {

            RelativeLayout rl = (RelativeLayout) findViewById(R.id.fragment);
            for (int id = 100; id < 6700; id += 100) {
                rl.removeView(findViewById(id));
            }


            Piece p = (Piece) v;

            pressed = p;
            int id = pressed.getId();

            String c;

/*
            // If the player is white
            if(color==1) {

                if (id > 8 && id < 17) {
                    c = p.pawnMoves();
                    selectSquares(c, p);
                }
                if (id == 1 || id == 8) {
                    c = p.rookMoves();
                    selectSquares(c, p);
                }
                if (id == 3 || id == 6) {
                    c = p.bishopMoves();
                    selectSquares(c, p);
                }
                if (id == 4) {
                    c = p.queenMoves();
                    selectSquares(c, p);
                }
                if (id == 5) {
                    c = p.kingMoves();
                    selectSquares(c, p);
                }
                if (id == 2 || id == 7) {
                    c = p.knightMoves();
                    selectSquares(c, p);
                }
            }
            // If the player is black
            else{
                if (id > 24 && id < 33) {
                    c = p.pawnMoves();
                    selectSquares(c, p);
                }
                if (id == 17 || id == 24) {
                    c = p.rookMoves();
                    selectSquares(c, p);
                }
                if (id == 19 || id == 22) {
                    c = p.bishopMoves();
                    selectSquares(c, p);
                }
                if (id == 20) {
                    c = p.queenMoves();
                    selectSquares(c, p);
                }
                if (id == 21) {
                    c = p.kingMoves();
                    selectSquares(c, p);
                }
                if (id == 18 || id == 23) {
                    c = p.knightMoves();
                    selectSquares(c, p);
                }
            }
*/
            if((color==1&&id>0&&id<17)||(color==-1&&id>16&&id<33)) {
                c = p.moves();
                selectSquares(c, p);
            }

            Piece.s = "";
        }
    }

    public void selectedOnClick(View v){

        myTurn=false;

        SelectedSquare s = (SelectedSquare)v;

        pressed.x = s.x;
        pressed.y = s.y;

        pressed.setLayoutParams(u.getPlaceParams(s.x , s.y));

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        for(int id=100; id<6700; id+=100) {
            rl.removeView(findViewById(id));
        }

        removeAponnent();

        String Coor = getWPCIDs() +","+ getBPCIDs();

        String message = Coor + ";" + pressed.c()+":"+pressed.getId();

        connectThread.stringWrite(message);
        System.out.println("sending: " + message);

    }

    /** Saves the current game to the data base. */
    public void saveGame(MenuItem item){
        final Activity activity = this;
        final View layout = View.inflate(this, R.layout.save_dialog, null);
        new AlertDialog.Builder(activity)
                .setTitle("What kind of game is this?")
                .setPositiveButton("New game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(activity)
                                .setTitle("Save Game")
                                .setMessage("Enter the name you want to give to this game so you will easily remember")
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        EditText nameText = (EditText) layout.findViewById(R.id.gamename);
                                        String gameName = nameText.getText().toString();
                                        System.out.println(gameName);
                                        mDbHelper.saveGame(gameName);
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setView(layout)
                                .show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /** Loads  a chosen game from the database. */
    public void loadGame(MenuItem item){
        final Activity activity = this;

        final View layout = View.inflate(this, R.layout.load_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("Load Game").setView(layout).show();

        final ListView listview = (ListView) layout.findViewById(R.id.entries);

        final ArrayList<String> list = mDbHelper.getGameNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);

        System.out.println(list);
        System.out.println(adapter);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                String gameSate = mDbHelper.getGameState(position);
                NewState state = new NewState(gameSate, activity);

                state.creatNewState();
                dialog.dismiss();
            }
        });

//        public void itemOnClick(int i){


//        }


    }

    /** Disable enable vibration in game. */
    public void setVibrate(View v){
        CheckBox vibrateRB = (CheckBox)v;

        boolean checked = vibrateRB.isChecked();

        if (checked){
            vibrate = true;
        }
        else {
            vibrate = false;
        }
    }
}
