package com.saymedia.chessgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by SayMedia on 19/07/2015.
 */
public class ChesserDbOperations extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public ChesserDbOperations(Context context) {
        super(context, FeedReaderContract.FeedEntry.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
        System.out.println(FeedReaderContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /** Saves a game state to the database. */
    public void saveGame(String gameName){

        String piecesCoordinates = Game.getWPCIDs() +","+ Game.getBPCIDs();
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(db.toString());

        System.out.println(FeedReaderContract.SQL_CREATE_ENTRIES);

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.GAME_NAME, gameName);
        values.put(FeedReaderContract.FeedEntry.GAME_PIECES_PLACEMENT, piecesCoordinates);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.GAME_PIECES_PLACEMENT,
                values);

        System.out.println(newRowId);

    }

    /** Returns a list of all game names. */
    public ArrayList getGameNames(){
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        // select game_name from Games
        // SELECT game_name, game_pieces_placement FROM Games ORDER BY game_name ASC

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.GAME_NAME,
                FeedReaderContract.FeedEntry.GAME_PIECES_PLACEMENT,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.FeedEntry.GAME_NAME + " ASC";

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        c.moveToFirst();
        final ArrayList gameNames = new ArrayList();

//        String gameNames = c.getString( c.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID) );
        while (!c.isAfterLast()) {
            gameNames.add(c.getString(0));

//            System.out.println("cursor: " + c.toString());
            System.out.println("gameName: " + gameNames);

            c.move(1);

        }

        return gameNames;

    }

    /** Returns a specific game state. */
    public String getGameState(int i){
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        // select game_name from Games
        // SELECT game_name, game_pieces_placement FROM Games ORDER BY game_name ASC

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.GAME_NAME,
                FeedReaderContract.FeedEntry.GAME_PIECES_PLACEMENT,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.FeedEntry.GAME_NAME + " ASC";

        Cursor c = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,    // The table to query
                projection,                                 // The columns to return
                null,                                       // The columns for the WHERE clause
                null,                                       // The values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        c.moveToPosition(i);

        String gameState = c.getString(1);

        return gameState;

    }


/*
    // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.GAME_NAME, id);
        values.put(FeedReaderContract.FeedEntry.GAME_PIECES_PLACEMENT, title);

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry.GAME_PIECES_PLACEMENT,
                values);
*/
}
