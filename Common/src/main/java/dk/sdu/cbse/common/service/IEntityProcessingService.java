package dk.sdu.cbse.common.service;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface IEntityProcessingService {
    /**
     * Process entities in the world based on game data
     * @param gameData Display size and keyboard input state
     * @param world The game world containing all entities
     */
    void process(GameData gameData, World world);
}
