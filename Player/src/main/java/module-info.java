import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.common.service.IGamePluginService;
module Player {
    exports dk.sdu.cbse.player;
    requires Common;
    requires CommonBullet;

    uses dk.sdu.cbse.commonbullet.BulletSPI;

    provides IGamePluginService with dk.sdu.cbse.player.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.cbse.player.PlayerControl, dk.sdu.cbse.player.EntityProcessor;
}