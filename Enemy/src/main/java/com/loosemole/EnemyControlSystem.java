package com.loosemole;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    /**
     * This method is called every frame. It updates the enemy movement, and controls when they shoot, based on chance.
     * It also checks if the enemy is out of bounds, and if so, moves it back in bounds.
     *
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            if (Math.random() <= 0.01) { // Every 100th time, should change direction
                enemy.setRotation(enemy.getRotation() + (float) Math.random() * 180);
            }
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            if (Math.random() <= 0.05) { // Every 20th time, should shoot
                getBulletSPIs().forEach(bulletSPI -> {
                    Entity bullet = bulletSPI.createBullet(enemy, gameData); // Create a bullet
                    world.addEntity(bullet); // Add the bullet to the world
                });
            }

            if (enemy.getX() < 0) {
                enemy.setX(1);
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(gameData.getDisplayWidth() - 1);
            }

            if (enemy.getY() < 0) {
                enemy.setY(1);
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(gameData.getDisplayHeight() - 1);
            }
        }
    }

    /**
     * This method is called whenever an entity collides with another entity.
     * If one of the entities in the collision is an enemy, it removes it.
     *
     * @param world
     * @param collider
     * @param collidee
     */
    @Override
    public void collide(World world, Entity collider, Entity collidee) {
        if (collider instanceof Enemy) {
            world.removeEntity(collider);
        } else if (collidee instanceof Enemy) {
            world.removeEntity(collidee);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
