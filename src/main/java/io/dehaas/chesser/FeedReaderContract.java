package io.dehaas.chesser;

import android.provider.BaseColumns;

/**
 * Defines the chesser database.
 */
public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String DATABASE_NAME = "chesser_db";
        public static final String TABLE_NAME = "Games";
//        public static final String ID = "id";
        public static final String GAME_NAME = "game_name";
        public static final String GAME_STATE = "game_state";
    }


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
//                    FeedEntry.ID + " INT PRIMARY KEY, "+
                    FeedEntry.GAME_NAME + " TEXT, " +
                    FeedEntry.GAME_STATE + " TEXT " +
                    ");";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}