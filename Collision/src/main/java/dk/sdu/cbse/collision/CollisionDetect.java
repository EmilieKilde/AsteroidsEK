package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.service.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CollisionDetect implements IPostEntityProcessingService {
    private static final String BULLET_TYPE = "Bullet";
    private static final String ASTEROID_TYPE = "Asteroid";

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);

            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);

                if (collides(entity1, entity2)) {
                    handleCollision(entity1, entity2, world);
                }
            }
        }
    }

    public boolean collides(Entity entity1, Entity entity2) {
        if (entity1 == null || entity2 == null) {
            return false;
        }

        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private void handleCollision(Entity entity1, Entity entity2, World world) {
        String type1 = entity1.getType();
        String type2 = entity2.getType();

        System.out.println("Collision between " + type1 + " and " + type2);

        if (areBothBullets(type1, type2)) {
            // Both bullets destroy each other
            destroyEntity(entity1, world);
            destroyEntity(entity2, world);
        } else if (areBothAsteroids(type1, type2)) {
            // Asteroids pass through each other
            return;
        } else if (onlyOneIsBullet(type1, type2)) {
            // Bullet hits non-bullet entity
            damageEntity(entity1, world);
            damageEntity(entity2, world);
        } else {
            // Non-bullet entities collide (e.g., player hits asteroid)
            destroyEntity(entity1, world);
            destroyEntity(entity2, world);
        }
    }

    private boolean areBothBullets(String type1, String type2) {
        return BULLET_TYPE.equals(type1) && BULLET_TYPE.equals(type2);
    }

    private boolean areBothAsteroids(String type1, String type2) {
        return ASTEROID_TYPE.equals(type1) && ASTEROID_TYPE.equals(type2);
    }

    private boolean onlyOneIsBullet(String type1, String type2) {
        return BULLET_TYPE.equals(type1) || BULLET_TYPE.equals(type2);
    }

    private void damageEntity(Entity entity, World world) {
        if (entity.getHealth() > 1) {
            entity.setHealth(entity.getHealth() - 1);
        } else {
            destroyEntity(entity, world);
        }
    }

    private void destroyEntity(Entity entity, World world) {
        entity.onRemoval(entity, world);
        world.removeEntity(entity);
    }
}
