package kindowwill;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Projectile {
    Circle shape;

    private static double radius = 5.0;
    private static double movementSpeed = 5.0;
    private static Pane pane = null;
    private static Stage stage = null;
    private static Ship ship = null;

    // need this to make sure multiple projectile collisions for the same collision are not generated
    private Boolean collision = false;

    public Projectile(double xPos, double yPos, double directionDegrees, Window window, Ship ship) {
        shape = new Circle(xPos, yPos, radius);
        shape.setFill(Color.WHITE);

        shape.setRotate(directionDegrees);

        this.pane = window.pane;
        this.stage = window.stage;
        this.ship = ship;

        new AnimationTimer() {
            public void handle(long now) {
                double angleRad = Math.toRadians(shape.getRotate());
                double moveX = movementSpeed * Math.cos(angleRad);
                double moveY = movementSpeed * Math.sin(angleRad);
                shape.setTranslateX(shape.getTranslateX() + moveX);
                shape.setTranslateY(shape.getTranslateY() + moveY);

                if (projectileHitWindowBorder()) {
                    stop();
                    delete();
                }
            }
        }.start();
    }

    private Boolean projectileHitWindowBorder() {
        double shapeMinX = shape.getBoundsInParent().getMinX();
        double shapeMinY = shape.getBoundsInParent().getMinY();
        double shapeMaxX = shape.getBoundsInParent().getMaxX();
        double shapeMaxY = shape.getBoundsInParent().getMaxY();

        // Check if any part of the shape is outside the window
        if (!collision) {
            // left
            if (shapeMinX < 0 || shapeMinY < 0 || shapeMaxX > pane.getWidth() || shapeMaxY > pane.getHeight()) {
                collision = true;
                return true;
            }
        }

        return false;
    }

    private void delete() {
        pane.getChildren().remove(shape);
    }
}
