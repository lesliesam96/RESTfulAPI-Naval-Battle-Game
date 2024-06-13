package com.example.battleship.helper;

import com.example.battleship.model.Game;
import com.example.battleship.model.Ship;
import com.example.battleship.model.ShipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBoardHelperTest {

    private Game game;
    private String[][] board;

    @BeforeEach
    void setUp() {
        game = new Game("testGameId", "TestTeam");
        board = new String[10][10];
        GameBoardHelper.initializeBoard(board);
    }

    @Test
    void placeShipsRandomly() {
        GameBoardHelper.placeShipsRandomly(game);
        assertTrue(game.getGameBoard().getShips().size() > 0);
    }

    @Test
    void placeShip_InvalidPlacement() {
        Ship ship = new Ship(ShipType.CRUISER);
        assertFalse(GameBoardHelper.placeShip(ship, board, 9, 9, true));
        assertFalse(game.getGameBoard().getShips().contains(ship));
    }

}