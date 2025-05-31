package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.commonasteroids.Asteroid;
import dk.sdu.cbse.commonasteroids.IAsteroidSplitter;

import java.util.Random;

public class AsteroidSplit implements IAsteroidSplitter {
    private final AsteroidSplit asteroidSplit;
    public AsteroidSplit(){
        this.asteroidSplit = new AsteroidSplit();
    }
//    @Override
//    public void removeBeforeSplit(Entity entity, World world){
//        asteroidSplit.createSplitAsteroid(entity, world);
//    }
    @Override
    public void createSplitAsteroid(Entity asteroids, World world){
        Random random = new Random();
        double x = asteroids.getX();
        double y = asteroids.getY();

        //splitter asteroider afhængig af størrelse.
        if(asteroids.getRadius()>5){
            int asteroidsAmount = random.nextInt(3)+1;
            int size = (int)asteroids.getRadius()/asteroidsAmount;
            if(size<1) size = 1; //hvis linjen over giver mindre end 1, så sættes den til 1

            for(int i = 0; i<asteroidsAmount; i++){
                Entity asteroid = new Asteroid();
                asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
                asteroid.setRadius(size);
                asteroid.setRotation(random.nextInt(90) + (i * ((double)360 / asteroidsAmount)));
                asteroid.setHealth(1);
                asteroid.setX(x+ random.nextInt(5));
                asteroid.setY(y+random.nextInt(5));

                world.addEntity(asteroid);
            }
        }
    }
}
