package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.commonbullet.BulletSPI;
import dk.sdu.cbse.player.Player;
import dk.sdu.cbse.commonbullet.Bullet;

import java.io.IOException;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class EnemyProcess implements IEntityProcessingService {
    private static final double MOVEMENT_SPEED = 0.7;
    private static final int ROTATION_CHANGE = 2;
    private static final int SHOOT_CHANCE = 5; // 5% chance per frame
    private static final Random random = new Random();

    private final EnemyPlugin enemyFactory;

    public EnemyProcess() {
        this.enemyFactory = new EnemyPlugin();
    }

    @Override
    public void process(GameData gameData, World world) {
        Collection<Entity> enemies = world.getEntities(Enemy.class);

        for (Entity enemy : enemies) {
            updateEnemyBehavior(enemy, gameData, world);

            if (isOutOfBounds(enemy, gameData)) {
                world.removeEntity(enemy);
                safeIncreaseAsteroidsKilled(gameData);
            }
        }

        // Ensure at least one enemy exists
        if (enemies.isEmpty()) {
            respawnEnemy(gameData, world);
        }
    }

    private void updateEnemyBehavior(Entity enemy, GameData gameData, World world) {
        // Random rotation change
        double newRotation = enemy.getRotation() + random.nextInt(ROTATION_CHANGE);
        enemy.setRotation(newRotation);

        // Move forward
        double radians = Math.toRadians(enemy.getRotation());
        double changeX = Math.cos(radians) * MOVEMENT_SPEED;
        double changeY = Math.sin(radians) * MOVEMENT_SPEED;

        enemy.setX(enemy.getX() + changeX);
        enemy.setY(enemy.getY() + changeY);

        // Occasionally shoot
        if (random.nextInt(100) < SHOOT_CHANCE) {
            fireBullet(enemy, gameData, world);
        }
    }

    private boolean isOutOfBounds(Entity enemy, GameData gameData) {
        double x = enemy.getX();
        double y = enemy.getY();
        return x < 0 || x > gameData.getDisplayWidth() ||
                y < 0 || y > gameData.getDisplayHeight();
    }

    private void fireBullet(Entity enemy, GameData gameData, World world) {
        getBulletSPIs().stream()
                .findFirst()
                .ifPresent(spi -> world.addEntity(spi.createBullet(enemy, gameData)));
    }

    private void respawnEnemy(GameData gameData, World world) {
        try {
            gameData.increaseEnemiesKilled();
        } catch (IOException e) {
            System.err.println("Failed to update enemies killed: " + e.getMessage());
        }
        enemyFactory.start(gameData, world);
    }

    private void safeIncreaseAsteroidsKilled(GameData gameData) {
        try {
            gameData.increaseAsteroidsKilled();
        } catch (IOException e) {
            System.err.println("Failed to update asteroids killed: " + e.getMessage());
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }
}
