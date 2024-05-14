import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module SplitPackageModule {
    requires Common;
    provides IGamePluginService with com.loosemole.EnemyPlugin;
}