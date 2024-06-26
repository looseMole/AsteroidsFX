package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CollisionDetector implements IPostEntityProcessingService {
    private List<IEntityProcessingService> injectedEntityProcessingServices = new ArrayList<>();

    public CollisionDetector() {
    }

    /**
     * This method is called every frame. It processes all entities in the world, and checks if any of their radius' overlap.
     * If they do, it calls the collide method on all services implementing IEntityProcessingService.
     * @param gameData - The game data object
     * @param world - The game world object containing all entities
     */
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;                    
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) { // TODO: Implement a more efficient collision detection algorithm
                    List<IEntityProcessingService> entityProcessingServices = new ArrayList<>();
                    // Get all services implementing IEntityProcessingService from service loader
                    entityProcessingServices.addAll(ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList()));
                    entityProcessingServices.addAll(this.injectedEntityProcessingServices); // Added ability to inject services, for testing purposes.
                    for(IEntityProcessingService service : entityProcessingServices) {
                        service.collide(world, entity1, entity2);
//                        System.out.println("Collision detected");
                    }
                }
            }
        }

    }

    /**
     * This method checks if two entities' radius overlap (whether the entities collide).
     * @param entity1 - The first entity
     * @param entity2 - The second entity
     * @return - True if the entities collide, false otherwise
     */
    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    public void injectEntityProcessingService(IEntityProcessingService service) {
        this.injectedEntityProcessingServices.add(service);
    }
}
