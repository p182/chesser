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
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Chesser data access object.
 */
public class ChesserDbOperations extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    Activity activity;

    Utils u = new Utils(activity);

    public ChesserDbOperations(Activity gameActivity) {
        super(gameActivity, FeedReaderContract.FeedEntry.DATABASE_NAME, null, DATABASE_VERSION);
        activity = gameActivity;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedReaderContract.SQL_CREATE_ENTRIES);
//        System.out.println(FeedReaderContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /** Saves a game state to the database. */
    public void saveGame(String gameName){
        // Check if there is no entry with the same game name.
        ArrayList existingGamesNames = getGameNames();
        if(!existingGamesNames.contains(gameName)) {

            // Create the game state string.
            String gameState = u.getStateForDb();

            // Gets the data repository in write mode
            SQLiteDatabase dbWrite = this.getWritableDatabase();
//            System.out.println(db.toString());

//            System.out.println(FeedReaderContract.SQL_CREATE_ENTRIES);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.GAME_NAME, gameName);
            values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = dbWrite.insert(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    FeedReaderContract.FeedEntry.GAME_STATE,
                    values);

            Toast.makeText(activity, R.string.successfully_saved,
                    Toast.LENGTH_LONG).show();

//            System.out.println(newRowId);


            //if(db.isOpen()) close();
        }
        else{
            // There is already an entry with the same name. Notify the problem.
            Toast.makeText(activity, R.string.name_game_already_given,
                    Toast.LENGTH_LONG).show();
        }

    }

    /** Deletes a game state from the database. */
    public void deleteGame(String gameName){
        // Gets the data repository in write mode
        SQLiteDatabase dbWrite = this.getWritableDatabase();

        // DELETE FROM Games WHERE game_name='test2'

        // Which row to delete, based on the ID
        String selection = FeedReaderContract.FeedEntry.GAME_NAME + " = ?";
        String[] selectionArgs = { gameName };

        int deleteCount = dbWrite.delete(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                selection,
                selectionArgs);

        System.out.println("deleteCount: " + deleteCount);

        Toast.makeText(activity, R.string.successfully_deleted,
                Toast.LENGTH_LONG).show();

//        if(db.isOpen()) close();
    }

    /** Returns a list of all game names. */
    public ArrayList getGameNames(){
        // Gets the data repository in read mode
        SQLiteDatabase dbRead = this.getReadableDatabase();

        // select game_name from Games
        // SELECT game_name, game_pieces_placement FROM Games ORDER BY game_name ASC

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.GAME_NAME,
                FeedReaderContract.FeedEntry.GAME_STATE,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.FeedEntry.GAME_NAME + " ASC";

        Cursor c = dbRead.query(
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
//            System.out.println("gameName: " + gameNames);

            c.move(1);

        }

        c.close();
//        if(db.isOpen()) close();

        return gameNames;

    }

    /** Returns a specific game state from the database. */
    public String getGameStateFromDb(int i){
        // Gets the data repository in read mode
        SQLiteDatabase dbRead = this.getReadableDatabase();

        // select game_name from Games
        // SELECT game_name, game_pieces_placement FROM Games ORDER BY game_name ASC

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                FeedReaderContract.FeedEntry.GAME_NAME,
                FeedReaderContract.FeedEntry.GAME_STATE,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = FeedReaderContract.FeedEntry.GAME_NAME + " ASC";

        Cursor c = dbRead.query(
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

        c.close();

//        if(db.isOpen()) close();

        return gameState;

    }

    /** Update an existing game state saved in the database. */
    public void updateGame(String gameName){
        // Gets the data repository in read mode
        SQLiteDatabase dbRead = this.getReadableDatabase();

        // UPDATE Games game_state='...' WHERE game_name='test2'
        // DELETE FROM Games WHERE game_name='test2'

        // Create the game state string.
        String gameState = u.getStateForDb();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

        // Which row to update, based on the ID
        String selection = FeedReaderContract.FeedEntry.GAME_NAME + " = ?";
        String[] selectionArgs = { gameName };

        int updateCount = dbRead.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

//        System.out.println("updateCount: " + updateCount);

        Toast.makeText(activity, R.string.successfully_saved, Toast.LENGTH_LONG).show();

//        if(db.isOpen()) close();
    }

    /** Save to the autosave value the current game state. */
    public void autoSave(String name){

        // Create the game state string.
        String gameState = u.getStateForDb();
//        System.out.println(gameState);

        // Gets the data repository in write mode
        SQLiteDatabase dbWrite = this.getWritableDatabase();


        // Check if there is no entry with the same game name.
        ArrayList existingGamesNames = getGameNames();
        if(!existingGamesNames.contains(name)) {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.GAME_NAME, name);
            values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = dbWrite.insert(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    FeedReaderContract.FeedEntry.GAME_STATE,
                    values);
        }

        // There is already an entry with the same name, update instead of creating a new entry.
        else{

            // New value for one column
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

            // Which row to update, based on the ID
            String selection = FeedReaderContract.FeedEntry.GAME_NAME + " = ?";
            String[] selectionArgs = { name };



            //synchronized (dbWrite) {
                dbWrite.update(
                        FeedReaderContract.FeedEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
            //}

        }

//        if(db.isOpen()) db.close();
    }
}
