package dk.sdu.cbse;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.service.IEntityProcessingService;
import dk.sdu.cbse.common.service.IGamePluginService;
import dk.sdu.cbse.common.service.IPostEntityProcessingService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Game extends Application {
    private static final String WINDOW_TITLE = "ASTEROIDS";
    private static final Color BACKGROUND_COLOR = Color.BLACK;
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color ENTITY_COLOR = Color.WHITE;

    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Pane gameWindow = new Pane();
    private final List<IEntityProcessingService> processingServices;
    private final List<IPostEntityProcessingService> postProcessingServices;
    private final List<IGamePluginService> gamePluginServices;
    private final GameData gameData = new GameData();
    private Text scoreText;

    public Game(List<IEntityProcessingService> entityProcessingServices,
                List<IPostEntityProcessingService> postEntityProcessingServices,
                List<IGamePluginService> gamePluginServices) {
        this.processingServices = entityProcessingServices;
        this.postProcessingServices = postEntityProcessingServices;
        this.gamePluginServices = gamePluginServices;
    }

    @Override
    public void start(Stage window) throws Exception {
        initializeUI(window);
        initializeGame();
        setupKeyBindings(window.getScene());

        window.show();
    }

    private void initializeUI(Stage window) {
        scoreText = new Text(10, 20, "Points: 0");
        scoreText.setFill(TEXT_COLOR);
        scoreText.setFont(Font.font(16));

        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.setStyle("-fx-background-color: black;");
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);
        window.setScene(scene);
        window.setTitle(WINDOW_TITLE);
        window.setResizable(false);
    }

    private void initializeGame() {
        // Start all game plugins
        for (IGamePluginService plugin : gamePluginServices) {
            try {
                plugin.start(gameData, world);
            } catch (Exception e) {
                System.err.println("Failed to start plugin: " + e.getMessage());
            }
        }

        // Create initial polygons for existing entities
        updatePolygons();
    }

    private void setupKeyBindings(Scene scene) {
        scene.setOnKeyPressed(event -> handleKeyPress(event.getCode(), true));
        scene.setOnKeyReleased(event -> handleKeyPress(event.getCode(), false));
    }

    private void handleKeyPress(KeyCode keyCode, boolean pressed) {
        GameKeys keys = gameData.getKeys();

        switch (keyCode) {
            case LEFT:
                keys.setKey(GameKeys.LEFT, pressed);
                break;
            case RIGHT:
                keys.setKey(GameKeys.RIGHT, pressed);
                break;
            case UP:
                keys.setKey(GameKeys.UP, pressed);
                break;
            case SPACE:
                keys.setKey(GameKeys.SPACE, pressed);
                break;
        }
    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        // Process entities
        for (IEntityProcessingService service : processingServices) {
            try {
                service.process(gameData, world);
            } catch (Exception e) {
                System.err.println("Error in entity processing: " + e.getMessage());
            }
        }

        // Post-process entities (collision detection, etc.)
        for (IPostEntityProcessingService service : postProcessingServices) {
            try {
                service.process(gameData, world);
            } catch (Exception e) {
                System.err.println("Error in post-entity processing: " + e.getMessage());
            }
        }
    }

    private void draw() {
        removeDestroyedEntities();
        updatePolygons();
        updateScore();
    }

    private void removeDestroyedEntities() {
        polygons.entrySet().removeIf(entry -> {
            Entity entity = entry.getKey();
            if (!world.getEntities().contains(entity)) {
                Polygon polygon = entry.getValue();
                gameWindow.getChildren().remove(polygon);
                return true;
            }
            return false;
        });
    }

    private void updatePolygons() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);

            if (polygon == null) {
                polygon = createPolygon(entity);
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }

            updatePolygonTransform(polygon, entity);
        }
    }

    private Polygon createPolygon(Entity entity) {
        Polygon polygon = new Polygon(entity.getPolygonCoordinates());
        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(ENTITY_COLOR);
        polygon.setStrokeWidth(1);
        return polygon;
    }

    private void updatePolygonTransform(Polygon polygon, Entity entity) {
        polygon.setTranslateX(entity.getX());
        polygon.setTranslateY(entity.getY());
        polygon.setRotate(entity.getRotation());
    }

    private void updateScore() {
        try {
            int score = gameData.getCurrentScore();
            scoreText.setText("Points: " + score);
        } catch (IOException e) {
            scoreText.setText("Points: Error");
            System.err.println("Failed to get score: " + e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        // Clean shutdown
        for (IGamePluginService plugin : gamePluginServices) {
            try {
                plugin.stop(gameData, world);
            } catch (Exception e) {
                System.err.println("Failed to stop plugin: " + e.getMessage());
            }
        }
        super.stop();
    }
}
