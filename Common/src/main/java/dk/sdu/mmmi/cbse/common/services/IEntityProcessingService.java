package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * "Process" the game data and world. Is used to update the state (execute the behaviour) of any entities,
     * the modules might have, like Player and Bullet.
     *
     * @param gameData - Is passed to the method, to give the Entity access to key presses, for instance.
     * @param world    - Is passed to the method, for the Entity to be able to interact with other entities in the world.
     */
    void process(GameData gameData, World world);

    /**
     * "Collide" the game data and world. Is used to determine what should happen when two entities collide. Is called
     * on all IEntityProcessingService-implementing classes, whenever any two entities in the world collide.
     *
     * @param world    - Is passed to the method, for the Entity to be able to interact with other entities in the world.
     * @param collider - The entity that is colliding with another entity.
     * @param collidee - The entity that is being collided with.
     */
    void collide(World world, Entity collider, Entity collidee);
}
