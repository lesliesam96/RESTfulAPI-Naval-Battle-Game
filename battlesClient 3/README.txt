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