package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.service.IGamePluginService;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {

    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 15;
    private static final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
        System.out.println("Spawning Asteroid " + asteroid.getID());
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Asteroid.class)
                .forEach(world::removeEntity);
    }

    private Entity createAsteroid(GameData gameData) {
        System.out.println("Creating Asteroid");
        Entity asteroid = new Asteroid();

        // Random size between MIN_SIZE and MAX_SIZE
        int size = random.nextInt(MAX_SIZE - MIN_SIZE + 1) + MIN_SIZE;

        // Set polygon shape
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setRadius(size);
        asteroid.setRotation(random.nextInt(360));

        // Random spawn location
        asteroid.setX(random.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(random.nextInt(gameData.getDisplayHeight()));

        return asteroid;
    }
}