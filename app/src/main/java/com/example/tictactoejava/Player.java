package com.example.tictactoejava;

public class Player {
    private String playerName;
    private int playerWins, playerLosses, playerDraws;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getWins() {
        return playerWins;
    }

    public void setWins(int playerWins) {
        this.playerWins = playerWins;
    }

    public int getLosses() {
        return playerLosses;
    }

    public void setLosses(int playerLosses) {
        this.playerLosses = playerLosses;
    }

    public int getDraws() {
        return playerDraws;
    }

    public void setDraws(int playerDraws) {
        this.playerDraws = playerDraws;
    }


}
