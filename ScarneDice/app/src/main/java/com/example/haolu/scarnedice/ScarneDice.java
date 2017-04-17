package com.example.haolu.scarnedice;

import java.util.Random;

public class ScarneDice {

    private int currentPlayer = 1;
    private int playerOneScore;
    private int playerTwoScore;
    private int MAX_NUM;
    private Random rand;

    public ScarneDice() {
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public boolean playGame(String action) {
        int num = 0;
        int total = 0;
        rand = new Random(MAX_NUM);
        switch (action) {
            case "ROLL":
                num = rand.nextInt();
                total += num;
                if (num == 1)
                    return false;
                break;
            case "HOLD":
                addScore(total);
                return false;
        }
        return false;
    }

    private void addScore(int total) {
        if (currentPlayer == 1) {
            playerOneScore += total;
        }
        else {
            playerTwoScore += total;
        }
    }
}
