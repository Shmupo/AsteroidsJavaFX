package kindowwill;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Window extends Application {

    public static Scene scene;
    public static Pane pane;
    public static Stage stage;

    public static AsteroidManager asteroidManager;

    private int windowX = 1000;
    private int windowY = 800;

    @Override
    public void start(Stage stage) throws IOException {
        pane = new Pane();
        pane.setPrefSize(windowX, windowY);
        pane.setStyle("-fx-background-color: black;");
        
        scene = new Scene(pane);
        
        Ship ship = new Ship(this);
        pane.getChildren().add(ship.shape);
        ship.setPosition(windowX / 2, windowX / 2);

        ship.setupInput();

        asteroidManager = new AsteroidManager(this);

        stage.setScene(scene);
        stage.show();

        Window.stage = stage;
        stage.setResizable(Boolean.FALSE); // disable user manual resizing

        asteroidManager.addRandomAsteroid();
    }

    public static void main(String[] args) {
        launch();
    }

}