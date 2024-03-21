package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


/***
 *  This class is responsible for controlling the player entity.
 *  It listens for key presses and updates the player entity accordingly.
 *  It also reacts to collisions with the player entity.
 */
public class PlayerControlSystem implements IEntityProcessingService {

    /**
     * This method is called every frame. It updates the player entity based on key presses.
     * Note: All player entities are processed the same way, in this method.
     *
     * @param gameData - The game data object
     * @param world    - The game world object containing all entities
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if (gameData.getKeys().isPressed(GameKeys.SPACE)) { // If the "shoot" key is pressed
                getBulletSPIs().forEach(bulletSPI -> {
                    Entity bullet = bulletSPI.createBullet(player, gameData); // Create a bullet
                    world.addEntity(bullet); // Add the bullet to the world
                });
            }
            if (gameData.getKeys().isDown(GameKeys.SPACE)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            world.addEntity(spi.createBullet(player, gameData));
                        }
                );
            }

            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(gameData.getDisplayWidth() - 1);
            }

            if (player.getY() < 0) {
                player.setY(1);
            }

            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight() - 1);
            }


            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight() - 1);
            }
        }
    }

    /**
     * This method is called whenever two entities collide.
     * Removes the player entity that collided with something.
     *
     * @param world    - The game world object containing all entities
     * @param collider - The entity that collided with another entity
     * @param collidee - The entity that was collided with
     */
    @Override
    public void collide(World world, Entity collider, Entity collidee) {
        if (collider instanceof Player) {
            world.removeEntity(collider);
        } else if (collidee instanceof Player) {
            world.removeEntity(collidee);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
