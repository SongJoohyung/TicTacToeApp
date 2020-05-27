package com.example.tictactoejava;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper to initialize and setup access to the SQLite DB for
 * the "Player scores" Leaderboard.
 */
public class LeaderBoardDB extends SQLiteOpenHelper {

    // TABLE INFORMATTION
    public static final String TABLE_NAME = "player_scores";
    public static final String PLAYER_ID = "_id";
    public static final String PLAYER_NAME = "player";
    public static final String PLAYER_WINS = "player_wins";
    public static final String PLAYER_DRAWS = "player_draws";
    public static final String PLAYER_LOSSES = "player_losses";
    public static final int COLUMN_COUNT = 5;

    // DATABASE INFORMATION
    static final String DB_NAME = "leaderboard.db";
    static final int DB_VERSION = 1;

    // TABLE CREATION STATEMENT
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(" + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PLAYER_NAME + " TEXT NOT NULL,"
            + PLAYER_WINS + " INTEGER DEFAULT 0,"
            + PLAYER_DRAWS + " INTEGER DEFAULT 0,"
            + PLAYER_LOSSES + " INTEGER DEFAULT 0);";


    public LeaderBoardDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
