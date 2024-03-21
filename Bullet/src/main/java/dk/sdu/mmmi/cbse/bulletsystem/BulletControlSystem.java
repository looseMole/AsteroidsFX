package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    /**
     * This method is called every frame. It updates all the bullet's movement, and removes any which are outside the game borders.
     * @param gameData - The game data object
     * @param world - The game world object containing all entities
     */
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX * 3);
            bullet.setY(bullet.getY() + changeY * 3);

            // If bullet outside borders, remove it
            if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth() || bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }

    /**
     * This method creates a bullet entity, and sets its position, rotation and shape.
     * @param shooter - The entity which shot the bullet
     * @param gameData - The game data object
     * @return bullet - The freshly created bullet entity
     */
    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));
        bullet.setX(shooter.getX() + changeX * 10);
        bullet.setY(shooter.getY() + changeY * 10);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);
        return bullet;
    }

    /**
     * This method is called whenever an entity collides with another entity.
     * If either entity in the collision is a bullet, the bullet is removed.
     * @param world - The game world object containing all entities
     * @param collider - The entity which collided with another entity
     * @param collidee - The entity which was collided with
     */
    @Override
    public void collide(World world, Entity collider, Entity collidee) {
        if(collider instanceof Bullet){
            world.removeEntity(collider);
        }

        else if(collidee instanceof Bullet){
            world.removeEntity(collidee);
        }
    }
}
