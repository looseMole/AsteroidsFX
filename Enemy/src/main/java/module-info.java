module Enemy {
    requires Common;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService with com.loosemole.EnemyPlugin;
    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService with com.loosemole.EnemyControlSystem;
}