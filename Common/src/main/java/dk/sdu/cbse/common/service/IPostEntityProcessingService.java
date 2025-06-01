package dk.sdu.cbse.common.service;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

public interface IPostEntityProcessingService {
    /**
     * Process entities after main entity processing (e.g., collision detection)
     * @param gameData Display size and game state
     * @param world The game world containing all entities
     */
    void process(GameData gameData, World world);
}