package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button [][] buttons = new Button[3][3];

    private boolean playerOneTurn = true;

    private int roundCount;

    private int playerOnePoints;
    private int playerTwoPoints;

    private TextView textViewPlayerOne;
    private TextView textViewPlayerTwo;

    int i, j, resID;
    String buttonID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerOne = findViewById(R.id.text_view_player_one);
        textViewPlayerTwo = findViewById(R.id.text_view_player_two);

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                buttonID = "button_" + i + j;
                resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons [i][j] = findViewById(resID);
                buttons [i][j].setOnClickListener(this);
            }
        }

        Button button_reset = findViewById(R.id.button_reset);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (playerOneTurn) {
            ((Button)v).setText("X");
        } else {
            ((Button)v).setText("O");
        }

        roundCount++;

        if(checkWin()) {
            if (playerOneTurn) {
                playerOneWins();
            } else {
                playerTwoWins();
            }
        } else if(roundCount == 9) {
            draw();
        } else {
            playerOneTurn = !playerOneTurn;
        }
    }

    private boolean checkWin(){
        String [][] field = new String[3][3];

        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (i = 0; i < 3; i++) {
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

    private void playerOneWins() {
        playerOnePoints++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void playerTwoWins() {
        playerTwoPoints++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayerOne.setText("Player 1: " + playerOnePoints);
        textViewPlayerTwo.setText("Player 2: " + playerTwoPoints);
    }

    private  void resetBoard() {
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        playerOneTurn = true;
    }

    private void resetGame() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        Toast.makeText(this, "The game has been reset.", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("playerOnePoints", playerOnePoints);
        outState.putInt("playerTwoPoints", playerTwoPoints);
        outState.putBoolean("playerOneTurn", playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roudCount");
        playerOnePoints = savedInstanceState.getInt("playerOnePoints");
        playerTwoPoints = savedInstanceState.getInt("playerTwoPoints");
        playerOneTurn = savedInstanceState.getBoolean("playerOneTurn");
    }
}
