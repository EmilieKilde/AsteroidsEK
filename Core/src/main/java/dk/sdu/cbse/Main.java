package dk.sdu.cbse;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();

    public static void main(String[] args) {
        launch(Main.class);
    }

    /**
     * REMEMBER TO START THE ScoringSystemApplication SEPARATELY FIRST
     * Run: mvn spring-boot:run from the ScoringSystem directory
     */

    @Override
    public void start(Stage window) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        Game game = context.getBean(Game.class);
        game.start(window);
        game.render();
    }
}
}