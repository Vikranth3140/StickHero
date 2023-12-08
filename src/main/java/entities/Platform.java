package entities;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Platform {

    private AnchorPane plane;
    private double planeHeight;
    private double planeWidth;
    private double widthMax = 160;
    private double widthMin = 45;

    private double spaceMin = 10;
    private double spaceMax = 100;

    private Random random = new Random();
    private double positionX;
    private double positionY;
    private int center;
    private boolean isOnPlatform;

    public Platform(AnchorPane plane, double planeHeight, double planeWidth) {
        this.plane = plane;
        this.planeHeight = planeHeight;
        this.planeWidth = planeWidth;
    }

    public ArrayList<Rectangle> createPlatforms() {
        // Fixed height for the platform
        int platformHeight = 254;

        // Randomize the platform width within a range
        double recWidth = random.nextDouble() * (widthMax - widthMin) + widthMin;
        double space = random.nextDouble() * (spaceMax - spaceMin) + spaceMin;

        positionX = 720;

        if (positionX + recWidth > planeWidth) {
            positionX = planeWidth - recWidth;
        }

        positionY = 477;

        // Create a white rectangle
        Rectangle whitePlatform = new Rectangle(positionX, positionY, recWidth, platformHeight);
        whitePlatform.setFill(javafx.scene.paint.Color.WHITE);

        // Create a red rectangle on top of the white rectangle
        Rectangle redPlatform = new Rectangle(positionX + recWidth * 0.375, positionY, recWidth * 0.25, 10);
        redPlatform.setFill(javafx.scene.paint.Color.RED);

        // Add the created platforms to the game plane
        plane.getChildren().addAll(whitePlatform, redPlatform);

        // Return the platforms as a list
        return new ArrayList<>(List.of(whitePlatform, redPlatform));
    }


    public void movePlatforms(ArrayList<Rectangle> platforms) {
        if (plane == null) {
            System.err.println("Error: 'plane' is null in Platform class");
            return;
        }

        ArrayList<Rectangle> outOfScreen = new ArrayList<>();

        for (Rectangle platform : platforms) {
            movePlatform(platform, -1.5);

            if (platform.getX() + platform.getLayoutX() + platform.getWidth() <= 0) {
                outOfScreen.add(platform);
            }
        }
        platforms.removeAll(outOfScreen);
        plane.getChildren().removeAll(outOfScreen);
    }

    private void movePlatform(Rectangle platform, double amount) {
        platform.setX(platform.getX() + amount);
        //platform.setLayoutX(platform.getX());
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getCenter() {
        return center;
    }

    public void setCenter(int center) {
        this.center = center;
    }

    public boolean isOnPlatform() {
        return isOnPlatform;
    }

    public void setOnPlatform(boolean onPlatform) {
        isOnPlatform = onPlatform;
    }

    public void reachedMiddle() {
        // Action when reached middle
    }

    public void reachedPlatform() {
        // Action when reached platform
    }
}