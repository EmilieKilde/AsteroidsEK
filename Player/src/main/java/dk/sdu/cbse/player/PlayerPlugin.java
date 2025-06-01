package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerShip(gameData);
        world.addEntity(player);
        System.out.println("Spawning Player " + player.getID());
    }

    @Override
    public void stop(GameData gameData, World world) {
        if (player != null) {
            world.removeEntity(player);
        }
    }

    private Entity createPlayerShip(GameData gameData) {
        Entity playerShip = new Player();

        // Center the player in the display
        playerShip.setX(gameData.getDisplayWidth() / 2.0);
        playerShip.setY(gameData.getDisplayHeight() / 2.0);
        playerShip.setRotation(0);

        return playerShip;
    }
}