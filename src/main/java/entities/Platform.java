package entities;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Platform {

    private AnchorPane plane;
    private double planeHeight;
    private double planeWidth;
    private double widthMax = 160;
    private double widthMin = 45;

    private double spaceMin = 65;
    private double spaceMax = 180;

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
        int platformHeight = 250;

        // Randomize the platform width within a range
        double recWidth = random.nextDouble() * (widthMax - widthMin) + widthMin;
        double space = random.nextDouble() * (spaceMax - spaceMin) + spaceMin;

        positionX = recWidth + space;

        if (positionX > planeWidth) {
            positionX = space; // Reset to a starting position
        }

        positionY = 477;

        Rectangle platform = new Rectangle(positionX, positionY,recWidth,platformHeight);

        // Add the created platform to the game plane

        plane.getChildren().addAll(platform);

        // Return the platform as a list (useful if you plan to have multiple platforms)
        return new ArrayList<>(Arrays.asList(platform));
    }

    public void movePlatforms(ArrayList<Rectangle> platforms) {
        if (plane == null) {
            System.err.println("Error: 'plane' is null in Platform class");
            return;
        }

        ArrayList<Rectangle> outOfScreen = new ArrayList<>();

        for (Rectangle platform : platforms) {
            movePlatform(platform, -2);

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

//    private Rectangle createPlatform(double x, double y, double width) {
//        // Fixed height for the platform
//        int platformHeight = 20;
//
//        Rectangle platform = new Rectangle(x, y, width, platformHeight);
//        return platform;
//    }

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