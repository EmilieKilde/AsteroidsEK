package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {
    private static final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        Entity enemy = createEnemy(gameData);
        world.addEntity(enemy);
        System.out.println("Spawning Enemy ship " + enemy.getID());
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class)
                .forEach(world::removeEntity);
    }

    private Entity createEnemy(GameData gameData) {
        System.out.println("Creating Enemy Ship");
        Entity enemy = new Enemy();

        enemy.setRotation(random.nextInt(360));

        // Random spawn location
        enemy.setX(random.nextInt(gameData.getDisplayWidth()));
        enemy.setY(random.nextInt(gameData.getDisplayHeight()));

        return enemy;
    }
}
