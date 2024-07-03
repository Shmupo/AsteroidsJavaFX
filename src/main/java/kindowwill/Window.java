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

    private int windowX = 800;
    private int windowY = 500;

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

        stage.setScene(scene);
        stage.show();

        Window.stage = stage;
        stage.setResizable(Boolean.FALSE);

        //expandWindowRight();
    }

    public void pushBorderRight() {
        pane.setPrefSize(windowX + 500, windowY);
        stage.setY(100);
        stage.setX(100);
    }

    public static void main(String[] args) {
        launch();
    }

}