package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.commonasteroids.Asteroid;
import dk.sdu.cbse.commonasteroids.IAsteroidSplitter;

public class AsteroidsProcess implements IEntityProcessingService {
    private IAsteroidSplitter asteroidSplit = new AsteroidSplit();
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            // opdater rotation
            asteroid.setRotation(asteroid.getRotation() /*+ asteroid.getRotationSpeed()*/);

            // opdater pos
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            wrapAroundScreen(gameData, asteroid);
        }
    }
    private void wrapAroundScreen(GameData gameData, Entity asteroid) {

        if (asteroid.getX() < 0) {
            asteroid.setX(gameData.getDisplayWidth());
        } else if (asteroid.getX() > gameData.getDisplayWidth()) {
            asteroid.setX(0);
        }


        if (asteroid.getY() < 0) {
            asteroid.setY(gameData.getDisplayHeight());
        } else if (asteroid.getY() > gameData.getDisplayHeight()) {
            asteroid.setY(0);
        }
    }
    public void setAsteroidSplit(IAsteroidSplitter asteroidSplit){
        this.asteroidSplit = asteroidSplit;
    }
    public void removeAsteroidSplit(IAsteroidSplitter asteroidSplit){
        this.asteroidSplit = null;
    }
}