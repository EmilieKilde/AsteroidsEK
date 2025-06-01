package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.commonbullet.*;
import dk.sdu.cbse.common.data.*;
import dk.sdu.cbse.common.service.IEntityProcessingService;

public class BulletsControl implements IEntityProcessingService, BulletSPI {
    private static final double BULLET_SPEED = 3.0;
    private static final double SPAWN_DISTANCE = 10.0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            moveBullet(bullet);

            if (isOutOfBounds(bullet, gameData)) {
                world.removeEntity(bullet);
            }
        }
    }

    private void moveBullet(Entity bullet) {
        double radians = Math.toRadians(bullet.getRotation());
        double changeX = Math.cos(radians) * BULLET_SPEED;
        double changeY = Math.sin(radians) * BULLET_SPEED;

        bullet.setX(bullet.getX() + changeX);
        bullet.setY(bullet.getY() + changeY);
    }

    private boolean isOutOfBounds(Entity bullet, GameData gameData) {
        double x = bullet.getX();
        double y = bullet.getY();
        return x < 0 || x > gameData.getDisplayWidth() ||
                y < 0 || y > gameData.getDisplayHeight();
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();

        // Position bullet slightly in front of shooter
        double radians = Math.toRadians(shooter.getRotation());
        double spawnX = shooter.getX() + Math.cos(radians) * SPAWN_DISTANCE;
        double spawnY = shooter.getY() + Math.sin(radians) * SPAWN_DISTANCE;

        bullet.setX(spawnX);
        bullet.setY(spawnY);
        bullet.setRotation(shooter.getRotation());

        return bullet;
    }
    }

