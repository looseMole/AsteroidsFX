package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * An interface for plugin-services to the game, such as PlayerPlugin and BulletPlugin. Defines the methods start and
 * stop for the plugin-services' start-up and shut-down logic.
 */
public interface IGamePluginService {

    /**
     * Start the plugin-service. Is called when the game is initially started.
     *
     * @param gameData - Is passed to the method, to give the plugin-service access to key presses, for instance.
     * @param world    - Is passed to the method, for the plugin-service to be able to interact with other entities
     *                 in the world.
     */
    void start(GameData gameData, World world);

    /**
     * Stop the plugin-service. Is called when the game is shut down.
     * gameData and world needs to be passed to this method as well, as start and stop is called on different instances,
     * thanks to the serviceLoader being called each "tick".
     *
     * @param gameData - Is passed to the method, to give the plugin-service access to key presses, for instance.
     * @param world    - Is passed to the method, for the plugin-service to be able to interact with other entities
     *                 in the world.
     */
    void stop(GameData gameData, World world);
}
