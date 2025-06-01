package dk.sdu.cbse.commonbullet;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
public interface BulletSPI {
    /**
     * Create a bullet fired by the given entity
     * @param shooter The entity that fired the bullet
     * @param gameData Game state and configuration
     * @return New bullet entity
     */
    Entity createBullet(Entity shooter, GameData gameData);
}