package dk.sdu.cbse.score;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class Score {
    private final AtomicInteger score = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("Starting Asteroids Scoring System...");
        SpringApplication.run(Score.class, args);
    }

    /**
     * Add points to the current score
     * @param points Points to add (can be negative)
     */
    @PutMapping("/addScore")
    public ScoreResponse addPoints(@RequestParam(value = "points", defaultValue = "0") int points) {
        int newScore = score.addAndGet(points);
        System.out.println("Score updated: +" + points + " = " + newScore);
        return new ScoreResponse(newScore, points);
    }

    /**
     * Get the current score
     * @return Current total score
     */
    @GetMapping("/getScore")
    public int getScore() {
        return score.get();
    }

    /**
     * Reset the score to zero
     */
    @PostMapping("/resetScore")
    public ScoreResponse resetScore() {
        int oldScore = score.getAndSet(0);
        System.out.println("Score reset from " + oldScore + " to 0");
        return new ScoreResponse(0, -oldScore);
    }

    /**
     * Get scoring system health status
     */
    @GetMapping("/health")
    public HealthResponse getHealth() {
        return new HealthResponse("UP", score.get());
    }

    // Response DTOs
    public static class ScoreResponse {
        private final int totalScore;
        private final int pointsAdded;

        public ScoreResponse(int totalScore, int pointsAdded) {
            this.totalScore = totalScore;
            this.pointsAdded = pointsAdded;
        }

        public int getTotalScore() { return totalScore; }
        public int getPointsAdded() { return pointsAdded; }
    }

    public static class HealthResponse {
        private final String status;
        private final int currentScore;

        public HealthResponse(String status, int currentScore) {
            this.status = status;
            this.currentScore = currentScore;
        }

        public String getStatus() { return status; }
        public int getCurrentScore() { return currentScore; }
    }
}