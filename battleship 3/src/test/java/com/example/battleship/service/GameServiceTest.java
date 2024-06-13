package com.example.battleship.service;

import com.example.battleship.model.FireResult;
import com.example.battleship.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameService = new GameService();
    }

    @Test
    void startGame() {
        // Test the startGame method
        String teamName = "Team1";
        Game game = gameService.startGame(teamName);

        assertNotNull(game);
        assertEquals(teamName, game.getTeamName());
        assertNotNull(game.getGameBoard());
        assertFalse(game.getGameBoard().getShips().isEmpty());
    }


    @Test
    void fire_Miss() {
        // Test the fire method for a miss
        Game game = gameService.startGame("Team1");
        String gameId = game.getGameId();
        FireResult fireResult = gameService.fire(gameId, "Z9");

        assertEquals(FireResult.MISS, fireResult);
    }

    @Test
    void fire_GameNotFound() {
        // Test the fire method when the game is not found
        assertThrows(NoSuchElementException.class, () -> gameService.fire("NonExistentGame", "A1"));
    }

    @Test
    void stopGame_GameFound() {
        // Test the stopGame method when the game is found
        Game game = gameService.startGame("Team1");
        String gameId = game.getGameId();
        String result = gameService.stopGame(gameId);

        assertEquals("Game stopped successfully", result);
        assertFalse(gameService.getGames().containsKey(gameId));
    }

    @Test
    void stopGame_GameNotFound() {
        // Test the stopGame method when the game is not found
        String result = gameService.stopGame("NonExistentGame");

        assertEquals("Game not found", result);
    }
}