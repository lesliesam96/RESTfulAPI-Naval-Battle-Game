# Battleship Client

This is a simple Java client for playing Battleship against a server. The client communicates with the server using HTTP requests to start, play, and stop a game.

## Getting Started

### Prerequisites

- Java 17 or later
- Maven

### Usage
You have the compiled JAR file: battlesClient-1.0-SNAPSHOT.jar
It is located in path/to/project/battlesClient/target

To run code run this command i terminal from target directory:
java -jar battlesClient-1.0-SNAPSHOT.jar

Configuration
The base URL of the Battleship server is set to http://localhost:8080/api/v1/battleships. Make sure the server is running at this address.

Files Generated
After playing the game, a text file will be generated with the format: <teamName>-battleships-<gameId>.txt, containing the total shots fired during the game.


# Battleship Server

The Battleship Server is a Spring Boot application that provides a RESTful API for playing the Battleship game. It includes endpoints for starting a new game, firing shots, and stopping an ongoing game.

## Getting Started

### Prerequisites

- Java 17 or later
- Maven

### Running the Server

1. Build the project:
mvn clean install

2. Run the server from target directory:
java -jar battleships-1.0-SNAPSHOT.jar

The server will start on http://localhost:8080.

API Endpoints

Health Check
Endpoint: /api/v1/battleships
Method: GET
Description: Health check endpoint to verify that the API is running.

Start a New Game
Endpoint: /api/v1/battleships/game/start
Method: POST
Request Body:
{
  "teamName": "YourTeamName"
}
Description: Start a new game for the specified team.

Fire a Shot
Endpoint: /api/v1/battleships/game/fire
Method: GET
Request Parameters:
gameId: The ID of the game
cell: The target cell to fire (e.g., "A1", "B2")
Description: Fire a shot in the specified game at the given cell.

Stop an Ongoing Game
Endpoint: /api/v1/battleships/game/stop
Method: POST
Request Body:
{
  "gameId": "your-game-id"
}
Description: Stop an ongoing game with the specified game ID.