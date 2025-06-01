package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.commonasteroids.IAsteroidSplitter;

import java.util.Random;

public class AsteroidSplit implements IAsteroidSplitter {
    private static final int MIN_SPLIT_SIZE = 5;
    private static final int MAX_SPLIT_COUNT = 4;
    private static final int POSITION_OFFSET = 5;
    private static final Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity asteroidA, World world) {
        if (asteroidA.getRadius() <= MIN_SPLIT_SIZE) {
            return; // Too small to split
        }

        double originalX = asteroidA.getX();
        double originalY = asteroidA.getY();

        // Create 1-4 smaller asteroids
        int numSplits = random.nextInt(MAX_SPLIT_COUNT) + 1;
        int newSize = Math.max(1, (int) asteroidA.getRadius() / numSplits);

        int baseDirection = random.nextInt(360);
        int angleIncrement = 360 / numSplits;

        for (int i = 0; i < numSplits; i++) {
            Entity splitAsteroid = new Asteroid();

            // Set size and shape
            splitAsteroid.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize,
                    -newSize, newSize, newSize, newSize);
            splitAsteroid.setRadius(newSize);

            // Set direction
            int direction = baseDirection + (i * angleIncrement);
            splitAsteroid.setRotation(direction);

            // Slightly offset position
            double offsetX = random.nextInt(POSITION_OFFSET);
            double offsetY = random.nextInt(POSITION_OFFSET);
            splitAsteroid.setX(originalX + offsetX);
            splitAsteroid.setY(originalY + offsetY);

            world.addEntity(splitAsteroid);
        }

        System.out.println("Splitting asteroid into " + numSplits + " smaller pieces");
    }
}
