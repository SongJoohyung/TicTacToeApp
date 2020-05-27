package com.example.tictactoejava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL Controller for CRUD operations on a Leaderboard.
 */
public class LeaderBoardController {

    private LeaderBoardDB leaderBoardDbHelper;
    private Context ourcontext;
    private SQLiteDatabase db;

    public LeaderBoardController(Context c) {
        ourcontext = c;
    }


    /**
     * Open database connection.
     * @return
     * @throws SQLException
     */
    public LeaderBoardController open() throws SQLException {
        leaderBoardDbHelper = new LeaderBoardDB(ourcontext);
        db = leaderBoardDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Close database connection.
     */
    public void close() {
        leaderBoardDbHelper.close();
    }



    /**
     * Create new data record operation.
     *
     * @param playerName
     * @param playerWins
     * @param playerDraws
     * @param playerLosses
     */
    public void create(String playerName, String playerWins, String playerDraws, String playerLosses) {
        ContentValues cv = new ContentValues();
        cv.put(LeaderBoardDB.PLAYER_NAME, playerName);
        cv.put(LeaderBoardDB.PLAYER_WINS, playerWins);
        cv.put(LeaderBoardDB.PLAYER_DRAWS, playerDraws);
        cv.put(LeaderBoardDB.PLAYER_LOSSES, playerLosses);
        db.insert(LeaderBoardDB.TABLE_NAME, null, cv);
    }

    /**
     * Read data operation.
     *
     * @return
     */
    public List<Player> read() {
        String[] allColumns = new String[] { LeaderBoardDB.PLAYER_ID, LeaderBoardDB.PLAYER_NAME, LeaderBoardDB.PLAYER_WINS, LeaderBoardDB.PLAYER_DRAWS, LeaderBoardDB.PLAYER_LOSSES };
        Cursor c = db.query(LeaderBoardDB.TABLE_NAME, allColumns, null, null, null, null, null);
        List<Player> players = new ArrayList<Player>();
        if (c != null) {
            c.moveToFirst();
            while(c.moveToNext()) {
                Player player = new Player();
                player.setPlayerName(c.getString(c.getColumnIndex(LeaderBoardDB.PLAYER_NAME)));
                player.setWins(c.getInt(c.getColumnIndex(LeaderBoardDB.PLAYER_WINS)));
                player.setDraws(c.getInt(c.getColumnIndex(LeaderBoardDB.PLAYER_DRAWS)));
                player.setLosses(c.getInt(c.getColumnIndex(LeaderBoardDB.PLAYER_LOSSES)));
                players.add(player);
            }
        }
        return players;
    }

    /**
     * Update data columns in a given row operation.
     *
     * @param player
     * @return int  the count of the updated row(s)
     */
    public int update(Player player) {
        ContentValues cv = new ContentValues();
        cv.put(LeaderBoardDB.PLAYER_NAME, player.getPlayerName());
        cv.put(LeaderBoardDB.PLAYER_WINS, player.getWins());
        cv.put(LeaderBoardDB.PLAYER_DRAWS, player.getDraws());
        cv.put(LeaderBoardDB.PLAYER_LOSSES, player.getLosses());

        int count = db.update(LeaderBoardDB.TABLE_NAME, cv, LeaderBoardDB.PLAYER_NAME+" = ?", new String[]{String.valueOf(player.getPlayerName())});
        return count;
    }

    /**
     * Delete data record operation.
     *
     * @param playerName
     * @return
     */
    public void delete(String playerName) {
        db.delete(LeaderBoardDB.TABLE_NAME, LeaderBoardDB.PLAYER_NAME+" = ?", new String[]{String.valueOf(playerName)});
    }

}
