package com.example.battleship.service;

import com.example.battleship.model.Coordinate;
import com.example.battleship.model.FireResult;
import com.example.battleship.model.Game;
import com.example.battleship.model.Ship;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.battleship.helper.GameBoardHelper.placeShipsRandomly;
import static com.example.battleship.model.Coordinate.fromFormattedCoordinate;

@Service
public class GameService {

    // Map to store active games using their unique game IDs
    private final Map<String, Game> games = new HashMap<>();

    // Getter for the games map (for testing or external access)
    public Map<String, Game> getGames() {
        return games;
    }

    // Start a new game for a given team
    public Game startGame(String teamName) {
        // Create a new Game instance with a unique game ID
        Game game = new Game(generateGameId(), teamName);

        // Place ships randomly on the game board
        placeShipsRandomly(game);

        // Print the initial state of the game board (for debugging or user feedback)
        game.getGameBoard().printBoard();

        // Add the game to the active games map
        games.put(game.getGameId(), game);

        // Return the newly created game
        return game;
    }

    // Perform a shot on a specified game and cell
    public FireResult fire(String gameId, String cell) {
        // Convert the cell string to a Coordinate object
        Coordinate targetCoordinate = fromFormattedCoordinate(cell);

        // Get the game instance using the game ID
        Game game = games.get(gameId);

        if (game != null) {
            // Iterate through the ships on the game board
            for (Ship ship : game.getGameBoard().getShips()) {
                List<Coordinate> shipCoordinates = ship.getCoordinates();

                // Check if the target coordinate is part of the ship
                if (shipCoordinates.contains(targetCoordinate)) {
                    shipCoordinates.remove(targetCoordinate);

                    // If all ship coordinates are hit, mark the ship as sunk
                    if (shipCoordinates.isEmpty()) {
                        ship.setSunk(Boolean.TRUE);
                        return FireResult.SUNK;
                    } else {
                        // If only a part of the ship is hit, return HIT
                        return FireResult.HIT;
                    }
                }
            }
            // If the target coordinate doesn't hit any ship, return MISS
            return FireResult.MISS;
        } else {
            // If the game ID is not found, throw a NoSuchElementException
            throw new NoSuchElementException();
        }
    }

    // Stop an ongoing game
    public String stopGame(String gameId) {
        // Remove the game from the active games map using its game ID
        Game game = games.remove(gameId);

        if (game != null) {
            // If the game is successfully removed, return success message
            return "Game stopped successfully";
        }

        // If the game ID is not found, return a message indicating that the game is not found
        return "Game not found";
    }

    // Generate a unique game ID using a combination of UUID and current timestamp
    private String generateGameId() {
        return UUID.randomUUID() + "_" + System.currentTimeMillis();
    }
}