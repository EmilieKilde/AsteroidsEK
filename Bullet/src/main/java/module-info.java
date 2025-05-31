import dk.sdu.cbse.commonbullet.BulletSPI;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.common.service.IGamePluginService;
module Bullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with dk.sdu.cbse.bulletsystem.BulletPlugin;
    provides BulletSPI with dk.sdu.cbse.bulletsystem.BulletsControl;
    provides IEntityProcessingService with dk.sdu.cbse.bulletsystem.BulletsControl;

}