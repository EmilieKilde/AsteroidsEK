package dk.sdu.cbse.common.data;
import java.io.*;
import java.net.*;

public class GameData {
    private static final String DEFAULT_BASE_URL = "http://localhost:8080";
    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 5000;

    private int displayWidth = 800;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();
    private int asteroidsKilled = 0;
    private int enemiesKilled = 0;
    private int playerDeaths = 0;
    private int rounds = 0;
    private final String baseURL;

    public GameData() {
        this(DEFAULT_BASE_URL);
    }

    public GameData(String baseURL) {
        this.baseURL = baseURL;
    }

    public void increaseAsteroidsKilled() throws IOException {
        asteroidsKilled++;
        safeAddScore(1);
    }

    public void increaseEnemiesKilled() throws IOException {
        enemiesKilled++;
        safeAddScore(10);
    }

    public void increasePlayerDeaths() throws IOException {
        playerDeaths++;
        safeAddScore(-10);
    }

    public void increaseRounds() throws IOException {
        rounds++;
        safeAddScore(10);
    }


    public int getCurrentScore() throws IOException {
        try {
            String response = performRequest("/getScore", "GET");
            return Integer.parseInt(response);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse score from server: " + e.getMessage());
            return 0;
        }
    }

    private void safeAddScore(int points) {
        try {
            addScore(points);
        } catch (IOException e) {
            System.err.println("Failed to update score: " + e.getMessage());
        }
    }

    private void addScore(int addPoints) throws IOException {
        String endpoint = "/addScore?points=" + addPoints;
        performRequest(endpoint, "PUT");
    }

    private String performRequest(String endpoint, String method) throws IOException {
        URL url = new URL(this.baseURL + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            return executeRequest(connection, method);
        } finally {
            connection.disconnect();
        }
    }

    private String executeRequest(HttpURLConnection connection, String method) throws IOException {
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.connect();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {

            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            return content.toString();
        }
    }

    // Getters and setters
    public int getRounds() { return rounds; }
    public int getAsteroidsKilled() { return asteroidsKilled; }
    public int getPlayerDeaths() { return playerDeaths; }
    public int getEnemiesKilled() { return enemiesKilled; }
    public GameKeys getKeys() { return keys; }

    public void setDisplayWidth(int width) {
        this.displayWidth = Math.max(100, width);
    }

    public int getDisplayWidth() { return displayWidth; }

    public void setDisplayHeight(int height) {
        this.displayHeight = Math.max(100, height);
    }

    public int getDisplayHeight() { return displayHeight; }
}