package entities;

import javafx.scene.image.ImageView;

public class Character {
    private ImageView characterView;
    private double positionX;
    private double positionY;
    private boolean isWalking;
    private boolean isUpsideDown;
    private boolean isFalling;
    private boolean isStickUp;
    private int cherriesCollected;

    public ImageView getCharacterView() {
        return characterView;
    }

    public void setCharacterView(ImageView characterView) {
        this.characterView = characterView;
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

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public boolean isUpsideDown() {
        return isUpsideDown;
    }

    public void setUpsideDown(boolean upsideDown) {
        isUpsideDown = upsideDown;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isStickUp() {
        return isStickUp;
    }

    public void setStickUp(boolean stickUp) {
        isStickUp = stickUp;
    }

    public int getCherriesCollected() {
        return cherriesCollected;
    }

    public void setCherriesCollected(int cherriesCollected) {
        this.cherriesCollected = cherriesCollected;
    }

    public void walk() {
        //walk function
    }

    public void fall() {
        //fall function
    }

    public void UpsideDownWalk() {
        //upside down walk function
    }

    public void collectCherries() {
        //collect cherries function
    }

    public void stickPosition() {
        //stick position function
    }

    public void jump() {
        //jump function
    }

    public void changeCharacter() {
        //change function
    }
}