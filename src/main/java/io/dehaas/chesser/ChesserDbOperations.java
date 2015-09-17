package io.dehaas.chesser;

import android.content.ContentValues;
import android.content.Context;
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
    Context appContext;

    public ChesserDbOperations(Context context) {
        super(context, FeedReaderContract.FeedEntry.DATABASE_NAME, null, DATABASE_VERSION);
        appContext = context;
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
            String piecesCoordinates = Game.getWPCIDs() + "," + Game.getBPCIDs();
            String color;
            if (Game.color == 1) {
                color = "white";
            } else {
                color = "black";
            }
            String turn;
            if (Game.myTurn) {
                turn = "myTurn";
            } else {
                turn = "opTurn";
            }
            String gameState = piecesCoordinates + ";" + color + ";" + turn;

            // Gets the data repository in write mode
            SQLiteDatabase db = this.getWritableDatabase();
//            System.out.println(db.toString());

//            System.out.println(FeedReaderContract.SQL_CREATE_ENTRIES);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.GAME_NAME, gameName);
            values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    FeedReaderContract.FeedEntry.GAME_STATE,
                    values);

            Toast.makeText(appContext, R.string.successfully_saved,
                    Toast.LENGTH_LONG).show();

//            System.out.println(newRowId);


            db.close();
        }
        else{
            // There is already an entry with the same name. Notify the problem.
            Toast.makeText(appContext, R.string.name_game_already_given,
                    Toast.LENGTH_LONG).show();
        }

    }

    /** Deletes a game state from the database. */
    public void deleteGame(String gameName){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE FROM Games WHERE game_name='test2'

        // Which row to delete, based on the ID
        String selection = FeedReaderContract.FeedEntry.GAME_NAME + " = ?";
        String[] selectionArgs = { gameName };

        int deleteCount = db.delete(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                selection,
                selectionArgs);

        System.out.println("deleteCount: " + deleteCount);

        Toast.makeText(appContext, R.string.successfully_deleted,
                Toast.LENGTH_LONG).show();

        db.close();
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
                FeedReaderContract.FeedEntry.GAME_STATE,
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
//            System.out.println("gameName: " + gameNames);

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
                FeedReaderContract.FeedEntry.GAME_STATE,
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

    /** Update an existing game state saved in the database. */
    public void updateGame(String gameName){
        // Gets the data repository in read mode
        SQLiteDatabase db = this.getReadableDatabase();

        // UPDATE Games game_state='...' WHERE game_name='test2'
        // DELETE FROM Games WHERE game_name='test2'

        // Create the game state string.
        String piecesCoordinates = Game.getWPCIDs() +","+ Game.getBPCIDs();
        String color;
        if(Game.color==1){
            color = "white";
        }
        else{
            color = "black";
        }
        String turn;
        if(Game.myTurn){
            turn = "myTurn";
        }
        else{
            turn = "opTurn";
        }
        String gameState = piecesCoordinates +";"+ color +";"+ turn;

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

        // Which row to update, based on the ID
        String selection = FeedReaderContract.FeedEntry.GAME_NAME + " = ?";
        String[] selectionArgs = { gameName };

        int updateCount = db.update(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

//        System.out.println("updateCount: " + updateCount);

        Toast.makeText(appContext, R.string.successfully_saved,
                Toast.LENGTH_LONG).show();



        db.close();

    }

    /** Save to the autosave value the current game state. */
    public void autoSave(String name){

        // Create the game state string.
        String piecesCoordinates = Game.getWPCIDs() + "," + Game.getBPCIDs();
        String color;
        if (Game.color == 1) {
            color = "white";
        } else {
            color = "black";
        }
        String turn;
        if (Game.myTurn) {
            turn = "myTurn";
        } else {
            turn = "opTurn";
        }
        String gameState = piecesCoordinates + ";" + color + ";" + turn;

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();


        // Check if there is no entry with the same game name.
        ArrayList existingGamesNames = getGameNames();
        if(!existingGamesNames.contains(name)) {

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.GAME_NAME, name);
            values.put(FeedReaderContract.FeedEntry.GAME_STATE, gameState);

            // Insert the new row, returning the primary key value of the new row
            long newRowId;
            newRowId = db.insert(
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

            db.update(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

        }

        db.close();
    }
}
