package dk.sdu.cbse.common.service;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;

public interface IGamePluginService {
    /**
     * Initialize and add entities to the world
     * @param gameData Display size and game state
     * @param world The game world to add entities to
     */
    void start(GameData gameData, World world);

    /**
     * Clean up and remove entities from the world
     * @param gameData Display size and game state
     * @param world The game world to remove entities from
     */
    void stop(GameData gameData, World world);
}
