package com.example.battleship.controller;

import com.example.battleship.dto.*;
import com.example.battleship.model.FireResult;
import com.example.battleship.model.Game;
import com.example.battleship.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Health check endpoint
    @GetMapping("/battleships")
    public ResponseEntity<?> healthCheck() {
        // Return a simple status message indicating that the API is running
        ServiceStatusDto status = new ServiceStatusDto("OK", "Naval Battle Game API is running. For game operations, use /battleships/game/* endpoints.");
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    // Endpoint to start a new game
    @PostMapping("/battleships/game/start")
    public ResponseEntity<?> startGame(@RequestBody StartGameRequestDto gameRequest) {
        try {
            // Attempt to start a new game with the provided team name
            Game game = gameService.startGame(gameRequest.getTeamName());

            // Respond with the game details if the game is started successfully
            StartGameResponseDto response = new StartGameResponseDto(game.getGameId(), game.getTeamName());
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            // Return a 400 Bad Request response if there is an exception (e.g., invalid request)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to fire a shot in the game
    @GetMapping("/battleships/game/fire")
    public ResponseEntity<?> fire(@RequestParam String gameId, @RequestParam String cell) {
        try {
            // Attempt to fire a shot in the specified game at the given cell
            FireResult result = gameService.fire(gameId, cell);

            // Respond with the outcome of the shot
            FireResponseDto fireResponseDto = new FireResponseDto(result.name(), !FireResult.SUNK.equals(result));
            return new ResponseEntity<>(fireResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            // Return a 400 Bad Request response if there is an exception (e.g., invalid request)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to stop an ongoing game
    @PostMapping("/battleships/game/stop")
    public ResponseEntity<?> stopGame(@RequestBody StopGameRequestDto stopGameRequestDto) {
        try {
            // Attempt to stop the specified game
            String result = gameService.stopGame(stopGameRequestDto.getGameId());

            // Respond with the result of stopping the game
            StopGameResponseDto stopGameResponseDto = new StopGameResponseDto(result);
            return new ResponseEntity<>(stopGameResponseDto, HttpStatus.OK);
        } catch (Exception e) {
            // Return a 400 Bad Request response if there is an exception (e.g., invalid request)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}