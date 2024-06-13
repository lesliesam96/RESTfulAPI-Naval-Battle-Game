package org.example;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BattleshipClient {
    private static final String BASE_URL = "http://localhost:8080/api/v1/battleships";
    private static final Set<String> firedCells = new HashSet<>();

    public static void main(String[] args) throws IOException {
        BattleshipClient client = new BattleshipClient();
        String gameId = null;
        try {
            JSONObject obj = new JSONObject(client.startGame("TeamName"));
            gameId = obj.getString("gameId");
            int totalShotsFired = client.playGame(gameId);
            System.out.println("Total shots fired: " + totalShotsFired);

            String fileName = obj.getString("message") + "-battleships-" + gameId + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("Total shots fired: " + totalShotsFired);
            } catch (IOException e) {
                e.printStackTrace();
            }

            client.stopGame(gameId);

        } catch (IOException e) {
            System.out.println("Invalid request. Game over!");
            client.stopGame(gameId);
        }
    }

    private String startGame(String teamName) throws IOException {
        String url = BASE_URL + "/game/start";
        String requestBody = "{\"teamName\": \"" + teamName + "\"}";

        return sendRequest(url, "POST", requestBody);
    }

    private int playGame(String gameId) throws IOException {
        int totalShotsFired = 0;
        int shipsSunk = 0;

        while (shipsSunk < 10) {
            String cell = generateRandomCell();

            String url = BASE_URL + "/game/fire?gameId=" + gameId + "&cell=" + cell;
            System.out.println("Generated cell: " + cell);
            JSONObject response = new JSONObject(sendRequest(url, "GET", null));
            System.out.println("Server response: " + response);

            if ("SUNK".equals(response.getString("consequence"))) {
                shipsSunk++;
            }

            totalShotsFired++;
        }

        return totalShotsFired;
    }

    private String generateRandomCell() {
        Random random = new Random();
        char column;
        int row;
        String cell;

        do {
            column = (char) ('A' + random.nextInt(10));
            row = random.nextInt(10) + 1;
            cell = String.valueOf(column) + row;
        } while (firedCells.contains(cell));

        firedCells.add(cell);
        return cell;
    }

    private void stopGame(String gameId) throws IOException {
        String url = BASE_URL + "/game/stop";
        String requestBody = "{\"gameId\": \"" + gameId + "\"}";
        sendRequest(url, "POST", requestBody);
    }

    private String sendRequest(String url, String method, String requestBody) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set the content type to application/json
        con.setRequestProperty("Content-Type", "application/json");

        // HTTP request method
        con.setRequestMethod(method);

        // Enable input/output streams
        con.setDoOutput(true);
        con.setDoInput(true);

        if ("POST".equals(method)) {
            con.getOutputStream().write(requestBody.getBytes());
        }

        // Read response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}
