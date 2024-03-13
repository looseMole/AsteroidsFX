import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

module SplitPackageModule {
    requires Common;
    provides IEntityProcessingService with com.loosemole.EnemyControlSystem;
}