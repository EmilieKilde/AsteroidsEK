import dk.sdu.cbse.common.service.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires org.junit.jupiter.api;
    provides IPostEntityProcessingService with dk.sdu.cbse.collision.CollisionDetect;
}