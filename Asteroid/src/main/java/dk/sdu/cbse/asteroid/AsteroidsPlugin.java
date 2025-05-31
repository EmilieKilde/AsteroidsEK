package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.commonasteroids.Asteroid;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.service.IGamePluginService;

import java.util.Random;

public class AsteroidsPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
        System.out.println("Spawning Asteroid" + asteroid.getID());
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Removing the entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        System.out.println("Creating Asteroid");
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        // random størrelse
        int size = rnd.nextInt(10) + 5; // Size from 6 to 15
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        asteroid.setType("Asteroid");
        asteroid.setHealth(1);

        // Random spawn location
        asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
        asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
        return asteroid;
    }
}