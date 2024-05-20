package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CollisionDetectorTest {
    GameData gameData;
    World world;
    CollisionDetector collisionDetector;

    @BeforeEach
    void setUp() {
        // Create a CollisionDetector object to test.
        // Create a GameData and World object for the testCollision method
        this.collisionDetector = new CollisionDetector();

        this.gameData = new GameData();
        this.world = new World();
    }

    @Test
    void testCollision() {
        // Arrange
        int x = 0;
        int y = 0;
        Entity entity1 = new Entity();
        entity1.setX(x);
        entity1.setY(y);
        entity1.setRadius(3);

        Entity entity2 = new Entity();
        entity2.setX(x);
        entity2.setY(y);
        entity2.setRadius(3);

        world.addEntity(entity1);
        world.addEntity(entity2);
//        System.out.println(world.getEntities().size());

        // Mock an entity implementing the IEntityProcessingService interface, removing an entity from world if collide is called.
        IEntityProcessingService entityProcessingService = mock(IEntityProcessingService.class);
        doAnswer(invocation -> {
//            System.out.println("Collide method called");
            world.removeEntity(entity1);
            return null;
        }).when(entityProcessingService).collide(world, entity1, entity2);

        // Act
        // Call the testCollision method
        collisionDetector.injectEntityProcessingService(entityProcessingService);
        collisionDetector.process(gameData, world);
//        System.out.println(world.getEntities().size());

        // Assert
        // Check if the collide method was called, by checking whether an entity has been removed from World.
        assertEquals(1, world.getEntities().size());
    }
}