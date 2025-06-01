package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IEntityProcessingService;

import java.io.IOException;

public class AsteroidsProcess implements IEntityProcessingService {
    private static final double MOVEMENT_SPEED = 0.5;
    private static final int[] DIFFICULTY_THRESHOLDS = {5, 20, 50, 100};
    private static final int[] DIFFICULTY_SCALING = {5, 10, 20, 40};

    private final AsteroidsPlugin asteroidFactory;

    public AsteroidsProcess() {
        this.asteroidFactory = new AsteroidsPlugin();
    }

    @Override
    public void process(GameData gameData, World world) {
        int asteroidCount = processExistingAsteroids(gameData, world);
        spawnNewAsteroidsIfNeeded(gameData, world, asteroidCount);
    }

    private int processExistingAsteroids(GameData gameData, World world) {
        int count = 0;

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            count++;
            moveAsteroid(asteroid);

            if (isOutOfBounds(asteroid, gameData)) {
                world.removeEntity(asteroid);
                safeIncreaseAsteroidsKilled(gameData);
            }
        }

        return count;
    }

    private void moveAsteroid(Entity asteroid) {
        double radians = Math.toRadians(asteroid.getRotation());
        double changeX = Math.cos(radians) * MOVEMENT_SPEED;
        double changeY = Math.sin(radians) * MOVEMENT_SPEED;

        asteroid.setX(asteroid.getX() + changeX);
        asteroid.setY(asteroid.getY() + changeY);
    }

    private boolean isOutOfBounds(Entity asteroid, GameData gameData) {
        double x = asteroid.getX();
        double y = asteroid.getY();
        return x < 0 || x > gameData.getDisplayWidth() ||
                y < 0 || y > gameData.getDisplayHeight();
    }

    private void spawnNewAsteroidsIfNeeded(GameData gameData, World world, int currentCount) {
        int targetCount = calculateTargetAsteroidCount(gameData.getAsteroidsKilled());

        if (currentCount < targetCount) {
            int toSpawn = targetCount - currentCount;
            for (int i = 0; i < toSpawn; i++) {
                asteroidFactory.start(gameData, world);
                safeIncreaseRounds(gameData);
            }
        }
    }

    private int calculateTargetAsteroidCount(int asteroidsKilled) {
        for (int i = DIFFICULTY_THRESHOLDS.length - 1; i >= 0; i--) {
            if (asteroidsKilled >= DIFFICULTY_THRESHOLDS[i]) {
                return DIFFICULTY_SCALING[i];
            }
        }
        return 1; // Default minimum
    }

    private void safeIncreaseAsteroidsKilled(GameData gameData) {
        try {
            gameData.increaseAsteroidsKilled();
        } catch (IOException e) {
            System.err.println("Failed to update asteroids killed: " + e.getMessage());
        }
    }

    private void safeIncreaseRounds(GameData gameData) {
        try {
            gameData.increaseRounds();
        } catch (IOException e) {
            System.err.println("Failed to update rounds: " + e.getMessage());
        }
    }
}
