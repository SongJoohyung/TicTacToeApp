package com.example.tictactoejava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LeaderBoardDB playerDatabase;
    private Player player1 = new Player();
        private int player1Wins = 0;
        private int player2Wins = 0;
        private int player1Draws = 0;
        private int player2Draws = 0;
        private int player1Losses = 0;
        private int player2Losses = 0;
    private Player player2 = new Player();

    private EditText nameEditText;
    private Button[][] buttons = new Button[3][3];

    LeaderBoardController sqlcon;

    private boolean player1Turn = true;

    private int roundCount;

    private TextView playername;
    private TextView textviewplayer1;
    private TextView textviewplayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the database Helper
        playerDatabase = new LeaderBoardDB(this);
        //for updating
        sqlcon = new LeaderBoardController(this);
        //open DB connection so we're ready to display LeaderBoard
        sqlcon.open();

        textviewplayer1 = findViewById(R.id.text_view_p1);
        textviewplayer2 = findViewById(R.id.text_view_p2);


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button buttonLeaderboard = findViewById(R.id.button_leaderboard);
        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLeaderboard(v);
            }
        });

    }

    private void showLeaderboard(View view) {
        Intent intent = new Intent(this, LeaderBoardActivity.class);
        String currentPlayer = (player1Turn) ? "Player 1" : "Player 2";
        intent.putExtra("currentPlayer", currentPlayer);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("o");
        }
        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1.setWins(player1Wins++);
        player2.setLosses(player2Losses++);

        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();

        sqlcon.update(player1);
        sqlcon.update(player2);
        updatePointsText();
        resetBoard();
    }


    private void player2Wins() {
        player2.setWins(player2Wins++);
        player1.setLosses(player1Losses++);
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();


        sqlcon.update(player1);
        sqlcon.update(player2);
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        player1.setDraws(player1Draws++);
        player2.setDraws(player2Draws++);

        sqlcon.update(player1);
        sqlcon.update(player2);
        resetBoard();
    }

    private void updatePointsText() {
        textviewplayer1.setText("player 1: " + player1Wins);
        textviewplayer2.setText("player 2: " + player2Wins);
    }

    private void resetBoard() {
        //playerDatabase = new playerDB(nameEditText, player1Points);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }
    private void resetGame() {

        player1Wins = 0;
        player2Wins = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points",player1Wins);
        outState.putInt("player2Points",player2Wins);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Wins = savedInstanceState.getInt("player1Points");
        player2Wins = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}