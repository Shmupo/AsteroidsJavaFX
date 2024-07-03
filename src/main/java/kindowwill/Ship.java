package kindowwill;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Ship {
    public Polygon shape;
    public static Window window = null;
    public static Scene scene = null;
    public static Pane pane = null;

    double movementSpeed = 0;
    final private double acceleration = 0.1; 
    final private double idleDecceleration = 0.01; 
    final private double activeDecceleration = 0.05; 
    final private double maxSpeed = 3;

    public Ship(Window window) {
        shape = new Polygon(50, 0, -50, 25, -50, -25);

        shape.setFill(Color.WHITE);

        this.scene = window.scene;
        this.pane = window.pane;
    }

    public void setPosition(int xPos, int yPos) {
        shape.setTranslateX(xPos);
        shape.setTranslateY(yPos);
    }

    public void setupInput() {
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

        final double[] mouseX = {0};
        final double[] mouseY = {0};

        scene.setOnKeyPressed(event -> {
            pressedKeys.put(event.getCode(), Boolean.TRUE);
        });

        scene.setOnKeyReleased(event -> {
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });

        scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            mouseX[0] = event.getX();
            mouseY[0] = event.getY();
        });

        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            mouseX[0] = event.getX();
            mouseY[0] = event.getY();
        });

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            double angleDegrees = shape.getRotate();
            Projectile p = new Projectile(shape.getTranslateX(), shape.getTranslateY(), angleDegrees, window, this);
            pane.getChildren().add(p.shape);
        });

        // create timer that is called each frame to handle input
        new AnimationTimer() {
            public void handle(long now) {
                // move ship when pressing 'W'
                if (pressedKeys.getOrDefault(KeyCode.W, false)) {
                    movementSpeed += acceleration;

                    if (movementSpeed > maxSpeed) {
                        movementSpeed = maxSpeed;
                    }
                } else if (pressedKeys.getOrDefault(KeyCode.S, false)) { // pressing 'S' to slow down faster
                    movementSpeed -= activeDecceleration;
                    if (movementSpeed < 0) {
                        movementSpeed = 0;
                    }
                } else {
                    movementSpeed -= idleDecceleration;
                    if (movementSpeed < 0) {
                        movementSpeed = 0;
                    }
                } 

                double angleRad = Math.toRadians(shape.getRotate());
                double moveX = movementSpeed * Math.cos(angleRad);
                double moveY = movementSpeed * Math.sin(angleRad);
                shape.setTranslateX(shape.getTranslateX() + moveX);
                shape.setTranslateY(shape.getTranslateY() + moveY);

                // point shape in direction of mouse position
                double deltaX = mouseX[0] - shape.getTranslateX();
                double deltaY = mouseY[0] - shape.getTranslateY();
                float angleDiff = (float) Math.toDegrees(Math.atan2(deltaY, deltaX));

                shape.setRotate(angleDiff);
            }
        }.start();
    }
}
