import com.loosemole.enemy.SplitPackageClassPlugin;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module SplitPackageDemo {
    requires Common;
    provides IGamePluginService with SplitPackageClassPlugin;
}