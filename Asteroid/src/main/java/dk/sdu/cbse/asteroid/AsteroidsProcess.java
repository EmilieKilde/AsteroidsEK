package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.commonasteroids.Asteroid;
import dk.sdu.cbse.commonasteroids.IAsteroidSplitter;

public class AsteroidsProcess implements IEntityProcessingService {
    //private IAsteroidSplitter asteroidSplit = new AsteroidSplit();
    AsteroidsPlugin newAsteroid;
    int asteroidsCurrent; //asteroids som er til stede
    int asteroidsDestroyed; //Asteroids ødelagt
    int difficulty;

    public AsteroidsProcess(){
        this.newAsteroid = new AsteroidsPlugin();
        this.asteroidsDestroyed = 0;
        this.difficulty = 1;
        this.asteroidsCurrent = 0;

    }
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            // opdater pos
            asteroid.setX(asteroid.getX() + changeX * 1);
            asteroid.setY(asteroid.getY() + changeY * 1);


            float screenHeight = gameData.getDisplayHeight();
            float screenWidth = gameData.getDisplayWidth();
            //Jeg prøver at fjerne asteroider der går uden for skærmen
            if((asteroid.getX()<0) || (asteroid.getX()> screenWidth)||(asteroid.getY() < 0) || (asteroid.getY() > screenHeight)){
                world.removeEntity(asteroid);
            }
        }
    }
}