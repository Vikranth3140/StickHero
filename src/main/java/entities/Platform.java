package entities;

import javafx.scene.image.ImageView;

public class Platform {
    private ImageView platformView;
    private double positionX;
    private double positionY;
    private double width;
    private int center;
    private boolean isOnPlatform;

    public ImageView getPlatformView() {
        return platformView;
    }

    public void setPlatformView(ImageView platformView) {
        this.platformView = platformView;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
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
        //reached middle
    }

    public void reachedPlatform() {
        //reached platform
    }
}