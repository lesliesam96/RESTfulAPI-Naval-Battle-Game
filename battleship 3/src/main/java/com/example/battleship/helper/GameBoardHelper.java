package com.example.battleship.helper;

import com.example.battleship.model.Coordinate;
import com.example.battleship.model.Game;
import com.example.battleship.model.Ship;
import com.example.battleship.model.ShipType;

import java.util.*;

/**
 * Helper class for managing the game board and ship placement logic.
 */
public class GameBoardHelper {

    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final Map<ShipType, Integer> shipCount = Map.ofEntries(
            Map.entry(ShipType.AIRCRAFT_CARRIER, 1),
            Map.entry(ShipType.CRUISER, 2),
            Map.entry(ShipType.DESTROYER, 3),
            Map.entry(ShipType.TORPEDO_BOAT, 4)
    );

    /**
     * Initializes the game board with empty cells.
     *
     * @param board The 2D array representing the game board.
     */
    public static void initializeBoard(String[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = "_"; // Empty cell symbol
            }
        }
    }

    /**
     * Places ships randomly on the game board.
     *
     * @param game The game object containing the board to place ships on.
     */
    public static void placeShipsRandomly(Game game) {
        Map<ShipType, Integer> remainingShipCount = new HashMap<>(shipCount);
        Random random = new Random();

        for (ShipType shipType : ShipType.values()) {
            int count = shipCount.get(shipType);

            for (int i = 0; i < count; i++) {
                boolean placed = false;
                while (!placed) {
                    int row = random.nextInt(10); // Random row index (0 to 9)
                    int column = random.nextInt(10); // Random column index (0 to 9)
                    boolean isVertical = random.nextBoolean(); // Randomly choose vertical or horizontal placement

                    Ship ship = new Ship(shipType);
                    placed = game.getGameBoard().placeShip(ship, row, column, isVertical);

                    if (placed) {
                        remainingShipCount.put(shipType, remainingShipCount.get(shipType) - 1);
                        if (remainingShipCount.get(shipType) == 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Places a ship on the game board at the specified position.
     *
     * @param ship     The ship to be placed.
     * @param board    The 2D array representing the game board.
     * @param row      The starting row index.
     * @param column   The starting column index.
     * @param isVertical True if the ship should be placed vertically, false for horizontal placement.
     * @return True if the ship is successfully placed, false otherwise.
     */
    public static boolean placeShip(Ship ship, String[][] board, int row, int column, boolean isVertical) {
        if (isValidPlacement(ship, board, row, column, isVertical)) {
            List<Coordinate> coordinates = new ArrayList<>();
            if (isVertical) {
                for (int i = 0; i < ship.getSize(); i++) {
                    board[row + i][column] = ship.getType().getSymbol();
                    coordinates.add(new Coordinate(row + i, column));
                }
            } else {
                for (int j = 0; j < ship.getSize(); j++) {
                    board[row][column + j] = ship.getType().getSymbol();
                    coordinates.add(new Coordinate(row, column + j));
                }
            }
            ship.setCoordinates(coordinates);
            return true;
        }
        return false;
    }

    /**
     * Checks if placing a ship at the specified position is a valid move.
     *
     * @param ship     The ship to be placed.
     * @param board    The 2D array representing the game board.
     * @param row      The starting row index.
     * @param column   The starting column index.
     * @param isVertical True if the ship should be placed vertically, false for horizontal placement.
     * @return True if the placement is valid, false otherwise.
     */
    private static boolean isValidPlacement(Ship ship, String[][] board, int row, int column, boolean isVertical) {
        if (isVertical) {
            if (row + ship.getSize() > ROWS) {
                return false; // Ship exceeds board boundaries
            }
            for (int i = 0; i < ship.getSize(); i++) {
                if (isInvalidCell(board, row + i, column)) {
                    return false; // Invalid cell (occupied or out of bounds)
                }
            }
        } else {
            if (column + ship.getSize() > COLUMNS) {
                return false; // Ship exceeds board boundaries
            }
            for (int j = 0; j < ship.getSize(); j++) {
                if (isInvalidCell(board, row, column + j)) {
                    return false; // Invalid cell (occupied or out of bounds)
                }
            }
        }
        return true;
    }

    /**
     * Checks if a cell on the board is invalid (occupied or out of bounds).
     *
     * @param board  The 2D array representing the game board.
     * @param row    The row index.
     * @param column The column index.
     * @return True if the cell is invalid, false otherwise.
     */
    private static boolean isInvalidCell(String[][] board, int row, int column) {
        if (row < 0 || row >= ROWS || column < 0 || column >= COLUMNS || !board[row][column].equals("_")) {
            return true; // Out of bounds or occupied cell
        }

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int currentRow = row + i;
                int currentColumn = column + j;
                if (currentRow >= 0 && currentRow < ROWS && currentColumn >= 0 && currentColumn < COLUMNS) {
                    if (!board[currentRow][currentColumn].equals("_")) {
                        return true; // Cell is in proximity to another ship
                    }
                }
            }
        }

        return false; // Valid cell
    }

    /**
     * Prints the current state of the game board to the console.
     *
     * @param board The 2D array representing the game board.
     */
    public static void printBoard(String[][] board) {
        System.out.println("  A B C D E F G H I J");

        for (int i = 0; i < ROWS; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
