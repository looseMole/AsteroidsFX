package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Is made to run after all IEntityProcessingService-implementing classes have run their process method.
 */
public interface IPostEntityProcessingService {

    /**
     * An "after-process" process. Is used to process the states of world and gameData after all entities has been
     * updated with their own process method.
     *
     * @param gameData - Is passed to the method, to give the service access to key presses, for instance.
     * @param world    - Is passed to the method, for the service to be able to interact with other entities in the world.
     */
    void process(GameData gameData, World world);
}
