package com.loosemole.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        this.enemy = createEnemyShip(gameData);
        SplitPackageClass.splitPackageMethod(); // This method is only called, to demonstrate a split package.
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(this.enemy);
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(12, -1, 8, -1, 8, -3, 6, -3, 6, -5, -2, -5, -2, -7, 0, -7, 0, -9, -10, -9, -10, -5, -8, -5, -8, -3, -6, -3, -6, -1, -10, -1, -10, 1, -6, 1, -6, 3, -8, 3, -8, 5, -10, 5, -10, 9, 0, 9, 0, 7, -2, 7, -2, 5, 2, 5, 2, 1, 4, 1, 4, -1, 2, -1, 2, -3, 4, -3, 4, -1, 6, -1, 6, 1, 4, 1, 4, 3, 2, 3, 2, 5, 6, 5, 6, 3, 8, 3, 8, 1, 12, 1);
        enemyShip.setX(Math.random()*gameData.getDisplayWidth());
        enemyShip.setY(0);
        enemyShip.setRadius(8);
        return enemyShip;
    }
}
