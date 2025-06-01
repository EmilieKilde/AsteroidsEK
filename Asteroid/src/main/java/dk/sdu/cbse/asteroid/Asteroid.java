package dk.sdu.cbse.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.IEntityRemoval;

public class Asteroid extends Entity implements IEntityRemoval {
    private static final String ASTEROID_TYPE = "Asteroid";
    private static final int DEFAULT_HEALTH = 1;

    private final AsteroidSplit asteroidSplit;

    public Asteroid() {
        super();
        this.asteroidSplit = new AsteroidSplit();
        setType(ASTEROID_TYPE);
        setHealth(DEFAULT_HEALTH);
    }

    @Override
    public void onRemoval(Entity entity, World world) {
        asteroidSplit.createSplitAsteroid(entity, world);
    }
}
