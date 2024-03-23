package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {


    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        // Print out all beans detected by Spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModuleConfig.class);

        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("Bean: " + beanName + " detected.");
        }

        Game game = context.getBean(Game.class);
        game.start(window);
        game.render();
    }
}
