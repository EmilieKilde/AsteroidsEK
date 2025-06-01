package dk.sdu.cbse.bulletsystem;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IGamePluginService;
import dk.sdu.cbse.commonbullet.Bullet;

public class BulletPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // Bullets are created dynamically when entities shoot
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Bullet.class)
                .forEach(world::removeEntity);
    }
}
