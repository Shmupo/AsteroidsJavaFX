package kindowwill;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class AsteroidManager {
    private static Window window;
    private static Pane pane;

    List<Polygon> asteroids = new ArrayList<>();

    final private double movementSpeed = 1; 
    final private double minAsteroidSize = 20;
    final private double maxAsteroidSize = 75;

    public AsteroidManager(Window window) {
        this.window = window;
        this.pane = window.pane;

        new AnimationTimer() {
            public void handle(long now) {
                for (Polygon shape : asteroids) {
                    if (shape != null) {
                        double angleRad = Math.toRadians(shape.getRotate());
                        double moveX = movementSpeed * Math.cos(angleRad);
                        double moveY = movementSpeed * Math.sin(angleRad);
                        shape.setTranslateX(shape.getTranslateX() + moveX);
                        shape.setTranslateY(shape.getTranslateY() + moveY);
                    }
                }
            }
        }.start();

        Timeline asteroidSpawnTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> { // spawn asteroids
            addRandomAsteroid();
        }));

        asteroidSpawnTimeline.setCycleCount(Timeline.INDEFINITE);
        asteroidSpawnTimeline.play();
    }

    private Polygon createAsteroidShape(double radius) {
        Polygon asteroid = new Polygon();
        Random random = new Random();
        double angleStep = 360.0 / 8;

        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(i * angleStep);
            double variation = radius * 0.4; // Variation amount for jagged effect
            double distance = radius + (random.nextDouble() * variation - variation / 2);
            double x = distance * Math.cos(angle);
            double y = distance * Math.sin(angle);
            asteroid.getPoints().addAll(x, y);
        }

        asteroid.setFill(Color.WHITE);
        return asteroid;
    }

    public void addRandomAsteroid() {
        Random random = new Random();
        double randomRadius = minAsteroidSize + (maxAsteroidSize - minAsteroidSize) * random.nextDouble();
        Polygon asteroidShape = createAsteroidShape(randomRadius);

        pane.getChildren().add(asteroidShape);
        asteroids.add(asteroidShape);

        placeAtBorder(asteroidShape);
    }

    private void placeAtBorder(Polygon asteroidShape) {
        Random random = new Random();
        int border = random.nextInt(4);
        double x = 0, y = 0;

        /*
         * 0 degrees goes right
         * 90 degrees goes down
         */

         double angleVariation = getRandomDoubleInRange(-30, 30);

        switch (border) {
            case 0: // Top
                x = getRandomDoubleInRange(0, pane.getWidth());
                y = -100;
                asteroidShape.setRotate(90 + angleVariation);
                break;
            case 1: // Right
                x = pane.getWidth() + 100;
                y = getRandomDoubleInRange(0, pane.getHeight());
                asteroidShape.setRotate(180 + angleVariation);
                break;
            case 2: // Bottom
                x = getRandomDoubleInRange(0, 1000);
                y = pane.getHeight() + 100;
                asteroidShape.setRotate(270 + angleVariation);
                break;
            case 3: // Left
                x = -100;
                y = getRandomDoubleInRange(0, pane.getHeight());
                asteroidShape.setRotate(0 + angleVariation);
                break;
        }

        asteroidShape.setTranslateX(x);
        asteroidShape.setTranslateY(y);
    }

    private double getRandomDoubleInRange(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
