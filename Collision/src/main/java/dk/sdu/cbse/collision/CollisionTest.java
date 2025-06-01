package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class CollisionTest {
    private CollisionDetect collisionDetect;
    private GameData gameData;
    private World world;

    @BeforeEach
    void setUp() {
        collisionDetect = new CollisionDetect();
        gameData = new GameData();
        world = new World();
    }

    @Test
    void testCollisionSamePosition() {
        Entity entity1 = createTestEntity(10, 10, 10);
        Entity entity2 = createTestEntity(10, 10, 10);

        assertTrue(collisionDetect.collides(entity1, entity2));
    }

    @Test
    void testCollisionNoOverlap() {
        Entity entity1 = createTestEntity(10, 40, 40);
        Entity entity2 = createTestEntity(10, 10, 10);

        assertFalse(collisionDetect.collides(entity1, entity2));
    }

    @Test
    void testCollisionDifferentSizes() {
        Entity entity1 = createTestEntity(10, 10, 10);
        Entity entity2 = createTestEntity(20, 10, 10);

        assertTrue(collisionDetect.collides(entity1, entity2));
    }

    @Test
    void testBulletCollision() {
        Entity bullet1 = createBullet(10, 10);
        Entity bullet2 = createBullet(10, 10);

        world.addEntity(bullet1);
        world.addEntity(bullet2);

        collisionDetect.process(gameData, world);

        assertFalse(world.getEntities().contains(bullet1));
        assertFalse(world.getEntities().contains(bullet2));
    }

    @Test
    void testAsteroidCollision() {
        Entity asteroid1 = createAsteroid(10, 10);
        Entity asteroid2 = createAsteroid(10, 10);

        world.addEntity(asteroid1);
        world.addEntity(asteroid2);

        collisionDetect.process(gameData, world);

        // Asteroids should pass through each other
        assertTrue(world.getEntities().contains(asteroid1));
        assertTrue(world.getEntities().contains(asteroid2));
    }

    @Test
    void testBulletAsteroidCollision() {
        Entity bullet = createBullet(10, 10);
        Entity asteroid = createAsteroid(10, 10);

        world.addEntity(bullet);
        world.addEntity(asteroid);

        collisionDetect.process(gameData, world);

        // Both should be destroyed (health = 1)
        assertFalse(world.getEntities().contains(bullet));
        assertFalse(world.getEntities().contains(asteroid));
    }

    @Test
    void testPlayerAsteroidCollision() {
        Entity player = createPlayer(10, 10);
        Entity asteroid = createAsteroid(10, 10);

        world.addEntity(player);
        world.addEntity(asteroid);

        collisionDetect.process(gameData, world);

        // Both should be destroyed
        assertFalse(world.getEntities().contains(player));
        assertFalse(world.getEntities().contains(asteroid));
    }

    private Entity createTestEntity(float radius, double x, double y) {
        Entity entity = new Entity();
        entity.setRadius(radius);
        entity.setX(x);
        entity.setY(y);
        entity.setHealth(1);
        return entity;
    }

    private Entity createBullet(double x, double y) {
        Entity bullet = new Entity();
        bullet.setType("Bullet");
        bullet.setRadius(1);
        bullet.setX(x);
        bullet.setY(y);
        bullet.setHealth(1);
        return bullet;
    }

    private Entity createAsteroid(double x, double y) {
        Entity asteroid = new Entity();
        asteroid.setType("Asteroid");
        asteroid.setRadius(10);
        asteroid.setX(x);
        asteroid.setY(y);
        asteroid.setHealth(1);
        return asteroid;
    }

    private Entity createPlayer(double x, double y) {
        Entity player = new Entity();
        player.setType("Player");
        player.setRadius(8);
        player.setX(x);
        player.setY(y);
        player.setHealth(5);
        return player;
    }
}
