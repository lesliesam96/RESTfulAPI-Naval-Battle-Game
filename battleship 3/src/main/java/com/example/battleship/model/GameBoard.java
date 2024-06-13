package com.example.battleship.model;

import java.util.ArrayList;
import java.util.List;

import com.example.battleship.helper.GameBoardHelper;

public class GameBoard {

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;

    private final String[][] board;
    private final List<Ship> ships;

    public GameBoard() {
        this.board = new String[ROWS][COLUMNS];
        this.ships = new ArrayList<>();
        GameBoardHelper.initializeBoard(board);
    }

    public List<Ship> getShips() {
        return ships;
    }

    public boolean placeShip(Ship ship, int row, int column, boolean isVertical) {
        boolean placed = GameBoardHelper.placeShip(ship, board, row, column, isVertical);
        if (placed) {
            ships.add(ship);
        }
        return placed;
    }

    public void printBoard() {
        GameBoardHelper.printBoard(board);
    }
}
