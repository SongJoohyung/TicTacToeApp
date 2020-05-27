//package com.example.tictactoejava;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.view.View;
//import android.widget.Toast;
//
//public class PlayerDB extends SQLiteOpenHelper {
//
//    private static final int DB_VERSION = 1;
//    private static final String PLAYER_TABLE_NAME = "players";
//
//    private Context appContext;
//
//    public PlayerDB(Context context, String player_name, SQLiteDatabase.CursorFactory factory) {
//        super(context, player_name, factory, DB_VERSION);
//        appContext = context;
//    }
//
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        //call method below to create table
//        createPlayerTable(sqLiteDatabase);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        String dropSQL = "DROP TABLE EXISTS" + PLAYER_TABLE_NAME+";";
//        sqLiteDatabase.execSQL(dropSQL);
//        createPlayerTable(sqLiteDatabase);
//    }
//    private void createPlayerTable(SQLiteDatabase dbPlayers) {
//        String createSQL = "CREATE TABLE " + PLAYER_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, player_name TEXT, score TEXT, player_draw Text, player_wins TEXT, player_Loss TEXT)";
//
//        dbPlayers.execSQL(createSQL);
//
//        String insertSQL = "INSERT INTO " + PLAYER_TABLE_NAME + " (player_name, score,  player_draw,player_wins, player_loss) SELECT 'Joohyung Song' AS Name, '19' AS Score, '2' AS Player_Draw, '4' AS Player_Wins, '1' AS Player_Loss " +
//                "UNION SELECT 'Joohyung Song', '19', '2', '4', '1'  " +
//                "UNION SELECT 'Bob', '15', '5', '3', '2'  ";
//        dbPlayers.execSQL(insertSQL);
//    }
//
//    public void updateDB(SQLiteDatabase dbPlayers, Game game) {
//        if (dbPlayers.isOpen()) {
//            ContentValues cv = new ContentValues();
//            cv.put("player_name", game.getPlayerName());
//            cv.put("score", (game.getWins()*5) + (game.getDraws()*2) - (game.getLosses()*4));
//            cv.put("player_wins", game.getWins());
//            cv.put("player_draw", game.getDraws());
//            cv.put("player_loss", game.getLosses());
//            dbPlayers.update(PLAYER_TABLE_NAME , cv, "player_name = '" + game.getPlayerName() + "';", null);
//            //countries = db.query("countries", columnNames, null, null, null, null, null);
//            //adapter.changeCursor(countries);
//            dbPlayers.close();
//        } else {
//            Toast.makeText(appContext, "Unable to connect to DB", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//
//}
