import dk.sdu.cbse.common.service.IGamePluginService;
import dk.sdu.cbse.common.service.IEntityProcessingService;
module Enemy {
    requires Common;
    requires Player;
    requires CommonBullet;
    uses dk.sdu.cbse.commonbullet.BulletSPI;
    provides IGamePluginService with dk.sdu.cbse.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.cbse.enemy.EnemyProcess;

}