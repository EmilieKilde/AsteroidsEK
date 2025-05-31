import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.common.service.IGamePluginService;
module Asteroid {
    requires Common;
    requires CommonAsteroid;
    provides IGamePluginService with dk.sdu.cbse.asteroid.AsteroidsPlugin;
    provides IEntityProcessingService with dk.sdu.cbse.asteroid.AsteroidsProcess, dk.sdu.cbse.asteroid.EntityProcessor;

}