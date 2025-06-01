module Core {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    requires spring.context;
    requires spring.beans;
    requires spring.core;

    opens dk.sdu.cbse to javafx.graphics, spring.core;
    uses dk.sdu.cbse.common.service.IGamePluginService;
    uses dk.sdu.cbse.common.service.IEntityProcessingService;
    uses dk.sdu.cbse.common.service.IPostEntityProcessingService;
exports dk.sdu.cbse;
}