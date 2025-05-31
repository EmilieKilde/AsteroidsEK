module Core {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;

    opens dk.sdu.cbse to javafx.graphics;
    uses dk.sdu.cbse.common.service.IGamePluginService;
    uses dk.sdu.cbse.common.service.IEntityProcessingService;
    uses dk.sdu.cbse.common.service.IPostEntityProcessingService;

}