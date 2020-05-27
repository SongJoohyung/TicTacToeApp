package com.example.tictactoejava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * Main Activity for rendering the Leaderboard and triggering Database operations based on the clicked button.
 *
 * @since 2020-04-28
 * @version 1.0.0
 * @author bcopeland
 */
public class LeaderBoardActivity extends AppCompatActivity {
    private LeaderBoardDB playerDatabase;
    private final String TAG = this.getClass().getName();
    private SQLiteDatabase db;

    TableLayout tableLayout;
    EditText playerName, playerWins, playerDraws, playerLosses;
    Button createButton, readButton, updateButton, deleteButton;

    LeaderBoardController sqlcon;

    ProgressDialog progressTracker;

    private final int WINS_COLUMN = 2;
    private final int DRAWS_COLUMN = 3;
    private final int LOSSES_COLUMN = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        try {
            // Get the Intent that started this activity and extract the string
            Intent intent = getIntent();
            String currentPlayer = intent.getStringExtra("currentPlayer");
            // Capture the layout's TextView and set the string as its text
            Toast.makeText(this, "Current Player: " + currentPlayer, Toast.LENGTH_LONG).show();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        playerDatabase = new LeaderBoardDB(this);
        db = playerDatabase.getReadableDatabase();
        sqlcon = new LeaderBoardController(this);

        playerName = (EditText) findViewById(R.id.player_name);
        playerWins = (EditText) findViewById(R.id.player_wins);
        playerDraws = (EditText) findViewById(R.id.player_draws);
        playerLosses = (EditText) findViewById(R.id.player_losses);

        createButton = (Button) findViewById(R.id.create_btn);
        readButton = (Button) findViewById(R.id.read_btn);
        updateButton = (Button) findViewById(R.id.update_btn);
        deleteButton = (Button) findViewById(R.id.delete_btn);

        tableLayout = (TableLayout) findViewById(R.id.table_layout);

        buildTable();

    }


    public int calculateScore(int wins, int draws, int losses) {
        int score = wins*5;
        score += draws*2;
        score -= losses*1;
        return score;
    }


    /**
     * Builds a Table with all the required rows to show the records in the DB
     */
    private void buildTable() {
        sqlcon.open();

        List<Player> players = sqlcon.read();

        int rows = players.size();
        int cols = LeaderBoardDB.COLUMN_COUNT;


        // rebuild Header row
        TableRow tableHeader = new TableRow(this);
        tableHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tableHeader.setBackgroundColor(getResources().getColor(R.color.tableHeader));

        TextView th = new TextView(this);
        th.setText(getString(R.string.leaderboard_col1));
        tableHeader.addView(th);
        TextView th1 = new TextView(this);
        th1.setText(getString(R.string.leaderboard_col2));
        tableHeader.addView(th1);
        TextView th2 = new TextView(this);
        th2.setText(getString(R.string.leaderboard_col3));
        tableHeader.addView(th2);
        TextView th3 = new TextView(this);
        th3.setText(getString(R.string.leaderboard_col4));
        tableHeader.addView(th3);
        TextView th4 = new TextView(this);
        th4.setText(getString(R.string.leaderboard_col5));
        tableHeader.addView(th4);
        TextView th5 = new TextView(this);
        th5.setText(getString(R.string.leaderboard_col6));
        tableHeader.addView(th5);

        tableLayout.addView(tableHeader);

        // outer for loop
        for (int i = 0; i < rows; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            // inner for loop
            for (int j = 0; j < cols; j++) {
                TextView td = new TextView(this);
                td.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//                td.setBackgroundResource(R.drawable.cell_shape);
                td.setGravity(Gravity.CENTER);
                td.setTextSize(18);
                td.setPadding(0, 5, 0, 5);

                if (players == null || players.size() <= 0) {
                    td.setText("???");
                } else {
                    if (j == WINS_COLUMN) {
                        td.setText(String.valueOf(players.get(i).getWins()));
                    } else if (j == DRAWS_COLUMN) {
                        td.setText(String.valueOf(players.get(i).getDraws()));
                    } else if (j == LOSSES_COLUMN) {
                        td.setText(String.valueOf(players.get(i).getLosses()));
                    }
                }
                tr.addView(td);
            }

            //add special "Points" score not stored in DB but calculated by Leaderboard ranking algorithm
            TextView score = new TextView(this);
            try {
                int w = Integer.parseInt(((TextView) tr.getChildAt(WINS_COLUMN)).getText().toString());
                int d = Integer.parseInt(((TextView) tr.getChildAt(DRAWS_COLUMN)).getText().toString());
                int l = Integer.parseInt(((TextView) tr.getChildAt(LOSSES_COLUMN)).getText().toString());
                score.setText("" + calculateScore(w, d, l));
            } catch (NumberFormatException | NullPointerException ex) {
                Log.e(TAG, "Invalid (non-numeric) data was entered for record at row: " + i);
                score.setText("???");
            }
            tr.addView(score);



            tableLayout.addView(tr);
        }
        sqlcon.close();
    }

}
