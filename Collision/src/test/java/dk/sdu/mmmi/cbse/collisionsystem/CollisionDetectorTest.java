package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {
    GameData gameData;
    World world;
    CollisionDetector collisionDetector;

    @BeforeEach
    void setUp() {
        // Create a CollisionDetector object to test.
        // Create a GameData and World object for the process method
        this.collisionDetector = new CollisionDetector();

        this.gameData = new GameData();
        this.world = new World();
    }

    @Test
    void process() {
        // Add entities to the world
        // TODO: Create an interface for the test, implementing IEntityProcessingService, with a detectable reaction to Collide()
        // A further solution to this, could be to mock ServiceLoader.load()
        Entity entity1 = new Entity();
        world.addEntity(entity1);
        world.addEntity(entity1);

        // Call the process method
        collisionDetector.process(gameData, world);

        // Check if the collide method was called on all services implementing IEntityProcessingService
        for(IEntityProcessingService service : ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList())) {
            assertTrue(service.collideCalled);
        }
    }

    @Test
    void collides() {
//        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
//            entityProcessorService.process(gameData, world);
//        }
//        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
//            postEntityProcessorService.process(gameData, world);
//        }
    }
}