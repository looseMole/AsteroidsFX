package com.loosemole.enemy;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class SplitPackageClassPlugin implements dk.sdu.mmmi.cbse.common.services.IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        SplitPackageClass.splitPackageMethod();
    }

    @Override
    public void stop(GameData gameData, World world) {
        return;
    }
}
