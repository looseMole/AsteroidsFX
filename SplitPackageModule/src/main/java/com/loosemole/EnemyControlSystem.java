package com.loosemole;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        System.out.println("The split package Enemy class has been loaded.");
    }

    @Override
    public void collide(World world, Entity collider, Entity collidee) {
        return;
    }
}
