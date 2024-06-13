package com.example.battleship.model;

public class Game {
    public Game(String gameId, String teamName) {
        this.gameId = gameId;
        this.teamName = teamName;
        gameBoard = new GameBoard();
    }

    private String gameId;
    private String teamName;
    private GameBoard gameBoard;

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
