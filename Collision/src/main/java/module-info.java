import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    opens dk.sdu.mmmi.cbse.collisionsystem to spring.core;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
}