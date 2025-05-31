import dk.sdu.cbse.common.service.IPostEntityProcessingService;

module Collision {
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.cbse.collision.CollisionDetect;
}