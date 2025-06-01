package dk.sdu.cbse.commonasteroids;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

public interface IAsteroidSplitter {
    /**
     * Create smaller asteroids when an asteroid is destroyed
     * @param destroyedAsteroid The asteroid that was destroyed
     * @param world The world to add new asteroids to
     */
    void createSplitAsteroid(Entity destroyedAsteroid, World world);
}
