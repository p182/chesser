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
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Manages the pieces and main UI and holds the game info.
 */
public class Game extends AppCompatActivity {

    public static BluetoothSocket socket;
    public static ConnectedThread connectedThread;
    public static Menu menu;
    Utils u = new Utils(this);

    public static Boolean firstGame = true;
    Boolean running = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        System.out.println("Game onCreate()");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // only when this is the first game between the devices create new ConnectedThread
        if(firstGame) {
            System.out.println("first game");
            ConnectedThread tmp = new ConnectedThread(socket, this);
            connectedThread = tmp;
            connectedThread.start();

            firstGame = false;
        }
        // if it is a new game but with same device only update the activity pointer in incomingStateListenerThread
        else {
//            incomingStateListenerThread.activity = this;
            connectedThread.activity = this;
        }
        //TODO: remove tmp

        //setupPieces();

        // setup shared preferences
        SharedPreferences settings = getSharedPreferences("chesserSettings", 0);
        boardNum = settings.getInt("boardNum", 3);
        vibrate = settings.getBoolean("vibrate", true);

        // setup board
        ImageView board = (ImageView)findViewById(R.id.board);
        switch (boardNum){
            case 1: board.setImageResource(R.drawable.board3); break;
            case 2: board.setImageResource(R.drawable.board4); break;
            case 3: board.setImageResource(R.drawable.board5); break;
        }

        TextView turnNotifier = (TextView)findViewById(R.id.turnNotifier);

        // Indicate player/opponent turn.
        if(color==1){
            myTurn=true;
            turnNotifier.setText(R.string.player_turn);
        }
        else{
            myTurn=false;
            turnNotifier.setText(R.string.opponent_turn);
        }

        /*
        CheckBox c = (CheckBox)findViewById(R.id.vibrateCB);
        c.setChecked(true);
        c = (CheckBox)findViewById(R.id.soundCB);
        c.setChecked(true);
        */

        castlingRook1 = true;
        castlingRook2 = true;

        piecesClickable = true;

        stateNumber = 1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        this.menu.findItem(R.id.action_undo).setEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*
        if (id == R.id.action_save||id == R.id.action_load) {
            return true;
        }
*/
        if (id == android.R.id.home){

            new AlertDialog.Builder(this)
                    .setTitle(R.string.exit_confirm)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            incomingStateListenerThread.cancel();
                            connectedThread.cancel();
                            startActivity(new Intent("first.activity"));
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    })
                    .create().show();
        }
        if(id == R.id.action_saveMenu){
            final Activity activity = this;

            final View layout = View.inflate(this, R.layout.load_dialog, null);
            final AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("Game Saving Options").setView(layout).show();

            final ListView listview = (ListView) layout.findViewById(R.id.entries);

            final ArrayList<String> list = new ArrayList<>(3);
            list.add(0 , "Save a game");
            list.add(1 , "Load a game");
            list.add(2 , "Delete a game");



            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);

            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new ListView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                    switch (position){
                        case 0: saveGame(); break;
                        case 1: loadGame(); break;
                        case 2: deleteGame(); break;
                    }
                    dialog.dismiss();
                }
            });
        }
        if(id == R.id.action_newGame){
            connectedThread.stringWrite("message:restartRequest");
            System.out.println("Requesting restart");
        }if(id == R.id.action_undo){
            connectedThread.stringWrite("message:undoRequest");
            System.out.println("Requesting undo");
        }

        return super.onOptionsItemSelected(item);
    }

    ChesserDbOperations mDbHelper = new ChesserDbOperations(this);

    public static int color = 1;

    public static Boolean kingInCheck = false;

    public static Boolean castlingRook1 = true;
    public static Boolean castlingRook2 = true;

    // the id number of the two opponent piece that can make an en-passant move devided by a coma
    public static String opponentEnPassant = ".";

    public static int enPassantXCoorForOpp = 0;
    public static int enPassantXCoor = 0;

    // the ids of the pawns that can perform an en-passant
    public static int enPassant1 = 0;
    public static int enPassant2 = 0;

    public static Boolean castlingRook1Happened = false;
    public static Boolean castlingRook2Happened = false;
    public static Boolean enPassantHappened = false;

    public static Boolean myTurn;
    public static Boolean piecesClickable=true;
    public static Boolean vibrate = true;
    int boardNum = 0;

    public static Piece pressedPiece;
    public static Piece removedPiece;
    public static int removedPieceX;
    public static int removedPieceY;

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

    public static int stateNumber;

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
    public static boolean[] checkCoordinate(String s, int customColor){
        String[] playerCoordinates;
        String[] opponentCoordinates;

        // If the player is white
        if(customColor==1) {
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
                createSelectedSquare(x, y, (i + 1) * 100);
            }
        }

        createSelectedSquare(p.x, p.y, 65 * 100);
        createSelectedSquare(p.x, p.y, 66 * 100);
        p.bringToFront();
    }

    public void setupPieces(){
            createPiece(1, 1, 1);
            createPiece(2, 1, 2);
            createPiece(3, 1, 3);
            createPiece(4, 1, 4);
            createPiece(5, 1, 5);
            createPiece(6, 1, 6);
            createPiece(7, 1, 7);
            createPiece(8, 1, 8);
            for (int i = 1; i < 9; i++) {
                createPiece(i, 2, (i + 8));
            }
            createPiece(1, 8, 17);
            createPiece(2, 8, 18);
            createPiece(3, 8, 19);
            createPiece(4, 8, 20);
            createPiece(5, 8, 21);
            createPiece(6, 8, 22);
            createPiece(7, 8, 23);
            createPiece(8, 8, 24);
            for (int i = 1; i < 9; i++) {
                createPiece(i, 7, (i + 24));
            }
    }

    public void createSelectedSquare(int x, int y, int id){

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

    public void createSpecialSelectedSquare(int x, int y, int id, int type){

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.fragment);
        final SpecialSelectedSquare image = new SpecialSelectedSquare(getApplicationContext(), x, y, id, type);

        image.setLayoutParams(u.getPlaceParams(x, y));

        image.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        switch (image.type) {
                            case 1:
                                castlingRook1Happened = true;
                                break;
                            case 2:
                                castlingRook2Happened = true;
                                break;
                            case 3:
                                enPassantHappened = true;
                        }
                        selectedOnClick(v);
                    }
                }
        );

        rl.addView(image);
    }

    public void createPiece(int x, int y, int id){

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

    public void piecesOnClick(View v){
        // Only if players turn and pieces are set to clickable a piece will react to click.
        if(myTurn&&piecesClickable) {
            // Remove all other selected squares.
            RelativeLayout rl = (RelativeLayout) findViewById(R.id.fragment);
            for (int id = 100; id < 7000; id += 100) {
                rl.removeView(findViewById(id));
            }

            Piece p = (Piece) v;

            pressedPiece = p;
            int id = pressedPiece.getId();

            String c;

            //Only if piece is one of the players pieces display its selected squares.
            if((color==1&&id>0&&id<17)||(color==-1&&id>16&&id<33)||(color==1&&id/1000>0&&id/1000<17)||(color==-1&&id/1000>16&&id/1000<33)) {
                c = p.moves();
                selectSquares(c, p);
            }

            if(color==1&&id==5||color==-1&&id==21){
                System.out.println("checking for castling");

                if(castlingAvailable()[0]){
                    if(color==1)createSpecialSelectedSquare(wk.x-2,wk.y,6700,1);
                    if(color==-1)createSpecialSelectedSquare(bk.x-2,bk.y,6700,1);
                    System.out.println("castling availble 0");
                }
                if(castlingAvailable()[1]){
                    if(color==1)createSpecialSelectedSquare(wk.x+2,wk.y,6800,2);
                    if(color==-1)createSpecialSelectedSquare(bk.x+2,bk.y,6800,2);
                    System.out.println("castling availble 1");
                }
            }

            if(id==enPassant1 || id==enPassant2){
                if(color==1)createSpecialSelectedSquare(enPassantXCoor, pressedPiece.y+1,6900,3);
                if(color==-1)createSpecialSelectedSquare(enPassantXCoor, pressedPiece.y-1,6900,3);
            }
        }
    }

    public void selectedOnClick(View v){
        // To eliminate bugs only if players turn a selected square will react to click.
        if (myTurn) {
            // Move the piece to the selected square. Save the old piece's coordinates to be able to undo the move if own king is in check.
//        Button turnNotifier = (Button)findViewById(R.id.turnNotifier);

            myTurn = false; // Disable other pieces from responding to clicks


            SelectedSquare s = (SelectedSquare) v;

            final int tmpX = pressedPiece.x;
            final int tmpY = pressedPiece.y;

            pressedPiece.x = s.x;
            pressedPiece.y = s.y;

            pressedPiece.setLayoutParams(u.getPlaceParams(s.x, s.y));
            pressedPiece.bringToFront();
            u.removeOpponent();

            // if the player has chosen a castling move but its puts the king in danger set castling1SquaresSafe to false
            boolean castling1SquaresSafe = true;
            if(castlingRook1Happened && (color==1&&(u.squareIsUnsafe(3, 1)||u.squareIsUnsafe(4, 1)) || color==-1&&(u.squareIsUnsafe(3, 8)||u.squareIsUnsafe(4, 8)))){
                castling1SquaresSafe = false;
            }

            // if the player has chosen a castling move but its puts the king in danger set castling2SquaresSafe to false
            boolean castling2SquaresSafe = true;
            if(castlingRook2Happened && (color==1&&(u.squareIsUnsafe(6, 1)||u.squareIsUnsafe(7, 1)) || color==-1&&(u.squareIsUnsafe(6, 8)||u.squareIsUnsafe(7, 8)))){
                castling2SquaresSafe = false;
            }

            if (!u.myKingInCheck() && castling1SquaresSafe && castling2SquaresSafe) {
                // Valid move, king not in check.
                kingInCheck = false;

                removedPiece = null;

                RelativeLayout rl = (RelativeLayout) findViewById(R.id.fragment);
                for (int id = 100; id < 7000; id += 100) {
                    rl.removeView(findViewById(id));
                }

                // If piece is a pawn and it got to the end of the board let the player choose to what piece he wants to switch to
                if ((pressedPiece.getId() > 8 && pressedPiece.getId() < 17 && s.y == 8) || (pressedPiece.getId() > 24 && pressedPiece.getId() < 33 && s.y == 1)) {
                    displayNewPieceChooser();
                } else {
                    // Valid move and pawn not at end of board

                    // if king or rook moved disable accordingly the castling options
                    if((pressedPiece==wr1 || pressedPiece==wk)&&color==1 || (pressedPiece==br1 || pressedPiece==bk)&&color==-1){
                        castlingRook1 = false;
                    }
                    if((pressedPiece==wr2 || pressedPiece==wk)&&color==1 || (pressedPiece==br2 || pressedPiece==bk)&&color==-1){
                        castlingRook2 = false;
                    }

                    // if castling happened then move the rook accordingly
                    if(castlingRook1Happened){
                        if(color==1) {
                            wr1.x = wk.x+1;
                            wr1.setLayoutParams(u.getPlaceParams(wr1.x, wr1.y));
                        }
                        if(color==-1) {
                            br1.x = bk.x+1;
                            br1.setLayoutParams(u.getPlaceParams(br1.x, br1.y));
                        }
                        castlingRook1Happened=false;
                    }
                    if(castlingRook2Happened){
                        if(color==1) {
                            wr2.x = wk.x-1;
                            wr2.setLayoutParams(u.getPlaceParams(wr2.x, wr2.y));
                        }
                        if(color==-1) {
                            br2.x = bk.x-1;
                            br2.setLayoutParams(u.getPlaceParams(br2.x, br2.y));
                        }
                        castlingRook2Happened=false;
                    }

                    //if en-passant happened move remove the opp pawn accordingly
                    if(enPassantHappened){
                        enPassantHappened=false;

                        String oppCoor;
                        if(color==1) oppCoor = enPassantXCoor + "" + (pressedPiece.y-1) ;
                        else oppCoor = enPassantXCoor + "" + (pressedPiece.y+1) ;
//                        System.out.println(oppCoor);
                        u.removePieceByCoor(oppCoor);
                    }
                    // disable en-passant
                    enPassant1=0;
                    enPassant2=0;

                    // if opponent can do a en-passant move ad the according opp pieces ids to opponentEnPassant
                    if((pressedPiece.getId() > 8 && pressedPiece.getId() < 17) || (pressedPiece.getId() > 24 && pressedPiece.getId() < 33)){
                        if(Math.abs(pressedPiece.y - tmpY)==2){
                            Boolean first = true;
                            for(Piece piece : u.getAllOpponentsPieces()){
                                if((pressedPiece.y==piece.y && Math.abs(pressedPiece.x-piece.x)==1)&&((piece.getId() > 8 && piece.getId() < 17) || (piece.getId() > 24 && piece.getId() < 33))){
                                    if(first){
                                        first=false;
                                        opponentEnPassant =""+piece.getId();
                                    }
                                    else{
                                        opponentEnPassant = opponentEnPassant +","+piece.getId();
                                    }
                                    enPassantXCoorForOpp = pressedPiece.x;
                                }
                            }
                        }
                    }

                    opponentTurnNotifier();

                    // set new state number
                    stateNumber++;


                    Chronometer playerClock = (Chronometer) findViewById(R.id.playerClock);
                    Chronometer oppClock = (Chronometer) findViewById(R.id.oppClock);
                    playerClock.stop();
                    oppClock.start();
                    oppClock.setAlpha(1f);
                    playerClock.setAlpha(0.4f);

                    // Prepare a new game state string and send it to opponent
                    String state = u.getState();

                    opponentEnPassant = ".";
                    enPassantXCoorForOpp = 0;

                    // Autosave game
                    mDbHelper.autoSave(getResources().getText(R.string.autosave_sent).toString());


                    // Send game to opponent
                    connectedThread.stringWrite(state);

                    if(stateNumber!=2) Game.menu.findItem(R.id.action_undo).setEnabled(true);


                    // Run the OpponentKingInCheckmate thread
                    OpponentKingInMateThread opponentKingInMateThreadThread = new OpponentKingInMateThread(this);
                    opponentKingInMateThreadThread.start();
                }
            } else{
                // King is checked or illegal castling attempted, the piece will undo the move after delay
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pressedPiece.x = tmpX;
                        pressedPiece.y = tmpY;

                        pressedPiece.setLayoutParams(u.getPlaceParams(tmpX, tmpY));

                        // If an opponent piece was removed return it
                        u.returnOpponent();

                        if (castlingRook1Happened) castlingRook1Happened=false;
                        if (castlingRook2Happened) castlingRook2Happened=false;

                        myTurn = true; // Enable other pieces to respond to clicks
                    }

                }, 500); // delay
            }

        }
    }

    /** Saves the current game to the data base. */
    public void saveGame(){
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
                                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setView(layout)
                                .show();
                    }
                })
                .setNegativeButton("Old game", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final View layout = View.inflate(activity, R.layout.load_dialog, null);
                        final AlertDialog updateDialog = new AlertDialog.Builder(activity).setTitle("Update Game").setView(layout).show();

                        final ListView listview = (ListView) layout.findViewById(R.id.entries);

                        final ArrayList<String> list = mDbHelper.getGameNames();

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);

                        listview.setAdapter(adapter);

                        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                                String gameName = (String)listview.getItemAtPosition(position);
                                mDbHelper.updateGame(gameName);
                                updateDialog.dismiss();
                            }
                        });
                    }
                })
                .show();
    }

    /** Loads  a chosen game from the database. */
    public void loadGame(){
        final Activity activity = this;

        final View layout = View.inflate(this, R.layout.load_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("Load Game").setView(layout).show();

        final ListView listview = (ListView) layout.findViewById(R.id.entries);

        final ArrayList<String> list = mDbHelper.getGameNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {

                String gameSate = mDbHelper.getGameStateFromDb(position);
                String[] stateString = gameSate.split(";");

                try {
                    System.out.println(Game.stateNumber);
                    System.out.println(Integer.parseInt(stateString[7]));
                    // check if player has attempted undo
                    if (Game.stateNumber == (Integer.parseInt(stateString[7]) + 1)) {
                        // send a undo request and exit method
                        Game.connectedThread.stringWrite("message:undoRequest");
                        Toast.makeText(activity, "you have attempted to undo your turn", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    } else {
                        Game.stateNumber = Integer.parseInt(stateString[7]);
                        NewState state = new NewState(gameSate, activity);
                        state.createNewState();
                        dialog.dismiss();
                    }
                }
                catch(IndexOutOfBoundsException e){
                    Toast.makeText(activity, "Game from an older version, some functions may not work.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /** Deletes  a chosen game from the database. */
    public void deleteGame(){
        final Activity activity = this;

        final View layout = View.inflate(this, R.layout.load_dialog, null);
        final AlertDialog listDialog = new AlertDialog.Builder(activity).setTitle("Delete Game").setView(layout).show();

        final ListView listview = (ListView) layout.findViewById(R.id.entries);

        final ArrayList<String> list = mDbHelper.getGameNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                final String gameName = (String) listview.getItemAtPosition(position);

                // Confirm with user that he wants to delete game
                new AlertDialog.Builder(activity)
                        .setTitle(getResources().getText(R.string.confirm_delete).toString() + " " + gameName + " ?")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mDbHelper.deleteGame(gameName);

                                // Dissmiss the list dialog only if delete confirmed
                                listDialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }

    /** Disable enable vibration in game. */
    public void setVibrate(View v){
        Switch vibrateRB = (Switch) v;

        boolean checked = vibrateRB.isChecked();

        if (checked){
            vibrate = true;

        }
        else {
            vibrate = false;
        }

        // save to shared preferences
        SharedPreferences settings = getSharedPreferences("chesserSettings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("vibrate", vibrate);
        editor.commit();
    }

    /** Display a bar at the bottom of the screen for prommoting a pawn. */
    public void displayNewPieceChooser(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);

        ImageView rook = (ImageView) findViewById(R.id.rook);
        ImageView knight = (ImageView) findViewById(R.id.knight);
        ImageView bishop = (ImageView) findViewById(R.id.bishop);
        ImageView queen = (ImageView) findViewById(R.id.queen);
        if (color == 1) {
            ll.setBackgroundColor(getResources().getColor(R.color.secondary_text_default_material_light));
            rook.setImageResource(R.drawable.wrook);
            knight.setImageResource(R.drawable.wknight);
            bishop.setImageResource(R.drawable.wbishop);
            queen.setImageResource(R.drawable.wqueen);
        } else {
            ll.setBackgroundColor(getResources().getColor(R.color.secondary_text_default_material_dark));
            rook.setImageResource(R.drawable.brook);
            knight.setImageResource(R.drawable.bknight);
            bishop.setImageResource(R.drawable.bbishop);
            queen.setImageResource(R.drawable.bqueen);
        }
        rook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pawn is white
                if (color == 1) {
                    if(pressedPiece.getId()>1000) pressedPiece.setId( pressedPiece.getId() / 1000 *1000 + 1);
                    else pressedPiece.setId( pressedPiece.getId() *1000 + 1);
                    pressedPiece.setImageResource(R.drawable.wrook);
                }
                // Pawn is black
                else {
                    if(pressedPiece.getId()>1000) pressedPiece.setId( pressedPiece.getId() / 1000 *1000 + 17);
                    else pressedPiece.setId( pressedPiece.getId() *1000 + 17);
                    pressedPiece.setImageResource(R.drawable.brook);
                }
                System.out.println(pressedPiece.getId());
            }
        });
        knight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pawn is white
                if (color == 1) {
                    if (pressedPiece.getId() > 1000)
                        pressedPiece.setId(pressedPiece.getId() / 1000 * 1000 + 2);
                    else pressedPiece.setId(pressedPiece.getId() * 1000 + 2);
                    pressedPiece.setImageResource(R.drawable.wknight);
                }
                // Pawn is black
                else {
                    if (pressedPiece.getId() > 1000)
                        pressedPiece.setId(pressedPiece.getId() / 1000 * 1000 + 18);
                    else pressedPiece.setId(pressedPiece.getId() * 1000 + 18);
                    pressedPiece.setImageResource(R.drawable.bknight);
                }
                System.out.println(pressedPiece.getId());
            }
        });
        bishop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pawn is white
                if (color == 1) {
                    if(pressedPiece.getId()>1000) pressedPiece.setId( pressedPiece.getId() / 1000 *1000 + 3);
                    else pressedPiece.setId( pressedPiece.getId() *1000 + 3);
                    pressedPiece.setImageResource(R.drawable.wbishop);
                }
                // Pawn is black
                else {
                    if(pressedPiece.getId()>1000) pressedPiece.setId( pressedPiece.getId() / 1000 *1000 + 19);
                    else pressedPiece.setId( pressedPiece.getId() *1000 + 19);
                    pressedPiece.setImageResource(R.drawable.bbishop);
                }
                System.out.println(pressedPiece.getId());
            }
        });
        queen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Pawn is white
                if (color == 1) {
                    if(pressedPiece.getId()>1000) pressedPiece.setId( pressedPiece.getId() / 1000 *1000 + 4);
                    else pressedPiece.setId( pressedPiece.getId() *1000 + 4);
                    pressedPiece.setImageResource(R.drawable.wqueen);
                }
                // Pawn is black
                else {
                    if(pressedPiece.getId()>1000) pressedPiece.setId( pressedPiece.getId() / 1000 *1000 + 20);
                    else pressedPiece.setId( pressedPiece.getId() *1000 + 20);
                    pressedPiece.setImageResource(R.drawable.bqueen);
                }
                System.out.println(pressedPiece.getId());
            }
        });

        ll.setVisibility(View.VISIBLE);
        ll.bringToFront();
        System.out.println("pawn at end of board");

    }

    public void onNewPieceChosen(View v){
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);
        ll.setVisibility(View.INVISIBLE);

        TextView turnNotifier = (TextView) findViewById(R.id.turnNotifier);
        // Notify opponent's turn and whether opponent is in check.
        if (u.opponentKingInCheck()) {
            if (Game.color == 1) {
                turnNotifier.setText(R.string.black_opponent_in_check);
//                        turnNotifier.setText(R.string.autosave_received);
            } else {
                turnNotifier.setText(R.string.white_opponent_in_check);
            }
        } else {
            // Opponent not in check.
            turnNotifier.setText(R.string.opponent_turn);
        }

        // Prepare a new game state string and send it to opponent
        String state = u.getState();
        connectedThread.stringWrite(state);
    }

    public void gameFinishedCloseOnClick(View v){
        piecesClickable = true;
        LinearLayout wonLayout = (LinearLayout) findViewById(R.id.gameFinishedLayout);
        wonLayout.setVisibility(View.INVISIBLE);
    }
    public void checkmateExitOnClick(View v){
//        incomingStateListenerThread.cancel();
        connectedThread.cancel();
        startActivity(new Intent("first.activity"));
    }

    /** Notify opponent's turn and whether opponent is in check. */
    public void opponentTurnNotifier(){
        TextView turnNotifier = (TextView) findViewById(R.id.turnNotifier);

        if (u.opponentKingInCheck()) {
            if (Game.color == 1) {
                turnNotifier.setText(R.string.black_opponent_in_check);
            } else {
                turnNotifier.setText(R.string.white_opponent_in_check);
            }
        } else {
            // Opponent not in check.
            turnNotifier.setText(R.string.opponent_turn);
        }
    }

    /** Override the onBackPressed method and do nothing. */
    @Override
    public void onBackPressed() {
    }

    public boolean[] castlingAvailable(){
        boolean rook1CastlingAvailable = false;
        boolean rook2CastlingAvailable = false;

        // Both the king and the first rook have not moved yet
        if(castlingRook1&&!kingInCheck){
            boolean squaresClear = true;

            // Scan all pieces and if there is a piece between the rook and the king declare it and break
            for(Piece piece : u.getAllPieces()){
                if(color==1&&(piece.c().equals("21")||piece.c().equals("31")||piece.c().equals("41")) || color==-1&&(piece.c().equals("28")||piece.c().equals("38")||piece.c().equals("48"))){
                    squaresClear = false;
                    break;
                }
            }

            if(squaresClear) rook1CastlingAvailable = true;
        }
        // Both the king and the second rook have not moved yet
        if(castlingRook2&&!kingInCheck){
            boolean squaresClear = true;

            // Scan all pieces and if there is a piece between the rook and the king declare it and break
            for(Piece piece : u.getAllPlayersPieces()){
                if(color==1&&(piece.c().equals("61")||piece.c().equals("71")) || color==-1&&(piece.c().equals("68")||piece.c().equals("78"))){
                    squaresClear = false;
                    break;
                }
            }

            if(squaresClear) rook2CastlingAvailable = true;
        }

        return new boolean[] {rook1CastlingAvailable,rook2CastlingAvailable};
    }

    /** Show the help dialog. */
    public void help(MenuItem item){

        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(R.layout.game_help_dialog);
        settingsDialog.show();

        Button ok = (Button) settingsDialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });

        /*
        piecesClickable=false;
        LinearLayout info = (LinearLayout)findViewById(R.id.linearLayout2);
        info.bringToFront();

        if(info.getVisibility()==View.INVISIBLE) info.setVisibility(View.VISIBLE);
        else info.setVisibility(View.INVISIBLE);
        */
    }

    /** Close the help dialog. */
    public void closeHelp(View v){
        piecesClickable=true;
        LinearLayout info = (LinearLayout)findViewById(R.id.linearLayout2);
        info.setVisibility(View.INVISIBLE);
    }

    /** Restars a new game. */
    public void restartGame(View v){
        connectedThread.stringWrite("message:restartRequest");
        System.out.println("Requesting restart");
    }

    /** Show the settings dialog. */
    public void settings(MenuItem item){
        final Dialog settingsDialog = new Dialog(this);
        settingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        settingsDialog.setContentView(R.layout.setting_board_dialog);
        settingsDialog.show();

        RadioButton board1 = (RadioButton) settingsDialog.findViewById(R.id.board1);
        RadioButton board2 = (RadioButton) settingsDialog.findViewById(R.id.board2);
        RadioButton board3 = (RadioButton) settingsDialog.findViewById(R.id.board3);

        switch (boardNum){
            case 1: board1.setChecked(true); break;
            case 2: board2.setChecked(true); break;
            case 3: board3.setChecked(true); break;
        }

        Switch switch1 = (Switch) settingsDialog.findViewById(R.id.switch1);
        switch1.setChecked(vibrate);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVibrate(v);
            }
        });

        board1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardPickerOnClick(v, settingsDialog);
            }
        });
        board2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardPickerOnClick(v, settingsDialog);
            }
        });
        board3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardPickerOnClick(v, settingsDialog);
            }
        });

        Button ok = (Button) settingsDialog.findViewById(R.id.button4);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDialog.dismiss();
            }
        });
    }

    /** Board picker onClick. */
    public void boardPickerOnClick(View view, Dialog settingsDialog){
        int selectedId = view.getId();
        ImageView board = (ImageView)findViewById(R.id.board);

        RadioButton board1 = (RadioButton) settingsDialog.findViewById(R.id.board1);
        RadioButton board2 = (RadioButton) settingsDialog.findViewById(R.id.board2);
        RadioButton board3 = (RadioButton) settingsDialog.findViewById(R.id.board3);

        board1.setChecked(false);
        board2.setChecked(false);
        board3.setChecked(false);

        RadioButton currentRadioButton = (RadioButton)view;
        currentRadioButton.setChecked(true);

        switch (selectedId){
            case R.id.board1: board.setImageResource(R.drawable.board3); boardNum = 1; break;
            case R.id.board2: board.setImageResource(R.drawable.board4); boardNum = 2; break;
            case R.id.board3: board.setImageResource(R.drawable.board5); boardNum = 3; break;
        }

        // save to shared preferences
        SharedPreferences settings = getSharedPreferences("chesserSettings", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("boardNum", boardNum);
        editor.commit();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (running) {
            setupPieces();

            Chronometer playerClock = (Chronometer) findViewById(R.id.playerClock);
            Chronometer oppClock = (Chronometer) findViewById(R.id.oppClock);
            playerClock.start();
            oppClock.setAlpha(0.4f);

            running = false;
        }
        super.onWindowFocusChanged(hasFocus);
    }
}
