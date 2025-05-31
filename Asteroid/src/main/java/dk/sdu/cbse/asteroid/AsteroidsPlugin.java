package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.commonasteroids.Asteroid;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.service.IGamePluginService;

import java.util.Random;

public class AsteroidsPlugin  implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world){
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random random = new Random();
        int size = 15;
        //tilfældig position
        asteroid.setX(random.nextDouble() * gameData.getDisplayWidth());
        asteroid.setY(random.nextDouble() * gameData.getDisplayHeight());
        //rotation
        asteroid.setRotation(random.nextDouble() * 360);
        asteroid.setRadius(size);
        return asteroid;
    }
}
