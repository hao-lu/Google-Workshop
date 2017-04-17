package com.example.haolu.scarnedice;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private Button mRollButton;
    private Button mHoldButton;
    private Button mResetButton;
    private ImageView mDiceImage;
    private TextView mScoreText;

    // Game variables
    private int playerScore;
    private int playerTurnScore;
    private int computerScore;
    private int computerTurnScore;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerHandler.post(timerRunnable);

        mDiceImage = (ImageView) findViewById(R.id.imageView);
        mScoreText = (TextView) findViewById(R.id.textView);

        mRollButton = (Button) findViewById(R.id.button_roll);
        mHoldButton = (Button) findViewById(R.id.button_hold);
        mResetButton = (Button) findViewById(R.id.button_reset);

        mRollButton.setOnClickListener(this);
        mHoldButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_roll:
                onRoll();
                break;
            case R.id.button_hold:
                onHold();
                break;
            case R.id.button_reset:
                onReset();
                break;
        }
    }

    private void onRoll() {
        Log.d(TAG, "onRoll");
        int rand = rollDice() + 1;
        if (rand == 1) {
            // Rolled 1
            playerTurnScore = 0;
            computerTurn();

        }
        else {
            playerTurnScore += rand;
        }
        mScoreText.setText("Your score: " + playerScore + " Computer score: " + computerScore +
                " Your turn score: " + playerTurnScore);

    }

    private void onHold() {
        Log.d(TAG, "onHold");
        playerScore += playerTurnScore;
        computerScore += computerTurnScore;
        if (playerScore >= 100) {
            Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();
            onReset();
        }
        else if (computerScore >= 100) {
            Toast.makeText(this, "YOU LOSE", Toast.LENGTH_SHORT).show();
            onReset();
        }
        else if (computerTurnScore == 0) {
            computerTurn();
        }
        playerTurnScore = 0;
        computerTurnScore = 0;
        mScoreText.setText("Your score: " + playerScore + " Computer score: " + computerScore);
//        timerHandler.removeCallbacks(timerRunnable);
    }

    private void onReset() {
        Log.d(TAG, "onReset");
        timerHandler.removeCallbacks(timerRunnable);
        playerScore = 0;
        playerTurnScore = 0;
        computerScore = 0;
        computerTurnScore = 0;
        mScoreText.setText("Your score: 0 Computer score: 0");
        enableButtons();
    }

    private int rollDice() {
        TypedArray images = getResources().obtainTypedArray(R.array.dice_images);
        int rand = (int) (Math.random() * images.length());
        mDiceImage.setImageResource(images.getResourceId(rand, R.drawable.dice1));
        images.recycle();
        Log.d(TAG, Integer.toString(rand + 1));
        return rand;
    }

    private void computerTurn() {
        disableButtons();
        Log.d(TAG, "computerturnscore " + computerTurnScore);

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (computerTurnScore < 20) {
                    int rand = rollDice() + 1;
                    Log.d(TAG, "RAND " + rand);
                    if (rand == 1) {
                        // Rolled a 1
                        computerTurnScore = 0;
                        enableButtons();
                        timerHandler.removeCallbacks(this);

                    } else {
                        computerTurnScore += rand;
                        timerHandler.postDelayed(this, 500);
                    }
                    mScoreText.setText("Your score: " + playerScore + " Computer score: " + computerScore +
                            " Computer turn score: " + computerTurnScore + " Computer rolled a " + rand);
                }
                else {
                    enableButtons();
                    mHoldButton.performClick();
                    mScoreText.append(" Computer holds");
                    timerHandler.removeCallbacks(this);
                }
            }
        };
        timerHandler.postDelayed(timerRunnable, 500);

    }

    private void disableButtons() {
        mRollButton.setEnabled(false);
        mHoldButton.setEnabled(false);
    }

    private void enableButtons() {
        mRollButton.setEnabled(true);
        mHoldButton.setEnabled(true);
    }

}
