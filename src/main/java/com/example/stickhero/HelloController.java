package com.example.stickhero;

import entities.*;
import entities.Character;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    Label score12;
    @FXML
    Label score122;
    private SceneSwitcherFacade sceneSwitcher;
    private HelloController controller;
    private MediaPlayer myMediaPlayer;
    @FXML
    ImageView charstart;

    private Random random = new Random();
    AnimationTimer gameLoop;
    @FXML
    Button endbutton;
    @FXML
    private AnchorPane plane;

    @FXML
    private Label score;

    @FXML
    private Label score1;
    @FXML
    private Label score2;

    @FXML
    public ImageView cherryImageView;

    private double accelerationTime = 0;
    private int gameTime = 0;
    private int scoreCounter = Score.getCurrentscore();
    private boolean gameStarted = false;
    private double stickEnd;
    private double rectangleRange;
    private Stage stage;
    private Scene scene;
    private Line stickLine;
    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Character character;
    private Shark shark;
    private double cherrySize = 20;
    private double planeWidth = 800;
    private double planeHeight = 730;
    private boolean isStickExtending = false;
    private Stick currentStick;
    @FXML
    private ImageView characterImageView;
    private double characterPosX = 100;
    @FXML
    private ImageView sharkImageView;
    private Timeline timeline;
    private MediaPlayer mediaPlayer;
    private Parent root;
    private boolean isSpaceBarPressed = false;
    private int firstTime = 0;
    Timeline running;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setStickLine(Line stickLine) {
        this.stickLine = stickLine;
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            isSpaceBarPressed = true;
            isStickExtending = true;
            stickLine.setEndY(stickLine.getEndY() - 10);

            stickLine.setOpacity(1.0);
        } else if (event.getCode() == KeyCode.DOWN) {
            characterImageView.setY(characterImageView.getY() + 60);
            characterImageView.setScaleY(characterImageView.getScaleY() * -1);
        } else if (event.getCode() == KeyCode.UP) {
            characterImageView.setY(characterImageView.getY() - 60);
            characterImageView.setScaleY(characterImageView.getScaleY() * -1);
        }
    }

    public boolean charLanded() {
        return rectangle2.getX() <= stickLine.getEndX() && rectangle2.getX() + rectangle2.getWidth() >= stickLine.getEndX();
    }
    @FXML
    private void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            isSpaceBarPressed = false;
            isStickExtending = false;

            double angle = Math.toRadians(stickLine.getRotate());

            double length = Math.abs(stickLine.getStartY() - stickLine.getEndY());
            double newEndX = stickLine.getStartX() + length * Math.cos(angle);
            double newEndY = stickLine.getStartY() - length * Math.sin(angle);

            stickEnd = newEndX;
            rectangleRange = rectangle2.getX() + rectangle2.getWidth();

            stickLine.setEndX(newEndX);
            stickLine.setEndY(newEndY);

            final int[] x = {(int) character.getPositionX()};
            running = new Timeline(new KeyFrame(Duration.millis(5), event1 -> {
                if (charLanded()) {
                    if (collectCherry()) {
                        cherryImageView.setOpacity(0);
                        scoreCounter++;
                        Score.setScore(Score.getScore()+1);


                        score.setText(String.valueOf(scoreCounter));
                        score1.setText(String.valueOf(scoreCounter));
                    }
                    if (x[0] < stickLine.getEndX() + 30) {
//                        System.out.println(stickLine.getEndX());
//                        System.out.println(stickLine.getStartX());
//                        System.out.println(x[0]);
                        moveCharacterX(characterImageView, 1, x[0]);
                        x[0]++;
//                        System.out.println(x[0]);
                    } else {
                        stop();
                        resetGame();
                    }
                } else {
                    if (x[0] < stickLine.getEndX() + 30) {
//                        System.out.println(stickLine.getEndX());
//                        System.out.println(stickLine.getStartX());
//                        System.out.println(x[0]);
                        moveCharacterX(characterImageView, 1, x[0]);
                        x[0]++;
//                        System.out.println(x[0]);
                    } else {
                        stop();
                        fallCharacter();
                        try {
                            switchToEnd();
                            Score.highscore();
                            System.out.println(Score.getScore());
                            if(score122!=null && score12!=null) {
                                score122.setText(String.valueOf(Score.getHighscore()));
                                score12.setText(String.valueOf(Score.getScore()));
                                Score.setCurrentscore(Score.getScore());
                                Score.setScore(0);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            })
            );
            running.setCycleCount(Animation.INDEFINITE);
            running.play();

            System.out.println("Character moved forward to positionX: " + character.getPositionX());
            character.moveUp();

            firstTime++;

            if (controller != null) {
                controller.setGameStarted(true);
            }

            double stickStartX = 157.0;
            double stickStartY = 375.0;

            currentStick = new Stick(stickStartX, stickStartY);
            plane.getChildren().add(currentStick.getStickLine());  // Add the next stick to the scene
        }
    }

    private void stop()
    {
        running.stop();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void switchToScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew2.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToScene3(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew3.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene4(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew4.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene5(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew5.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene6(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew6.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene7(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew7.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene8(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew8.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene9(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew9.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene10(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew10.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

    }

    public void switchToScene11(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Scenenew11.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(score1 != null && score != null) {
            System.out.println(Score.getCurrentscore());
            score1.setText(String.valueOf(scoreCounter));
            score.setText(String.valueOf(scoreCounter));

        }
        // Implementing Singleton Design Pattern, so that only one instance of MediaPlayer is created
        mediaPlayer();

        shark = new Shark(-6, 185, 100.0, sharkImageView);

        shark.translate(725);
        shark.moveDown();
//        System.out.println("Shark moved forward to positionX: " + shark.getPositionX());

        shark.moveForwardContinuously(); // Start the continuous movement

        double posX, posY, width, height;
        rectangle1 = new Rectangle(54, 385, 105, 248);
        rectangle1.setFill(Color.WHITE);

        rectangle2 = new Rectangle(330, 385, 105, 248);
        rectangle2.setFill(Color.WHITE);

        double planeHeight = 730;
        double planeWidth = 834;

        double startX = 157.0;
        double startY = 380.0;
        double endX = 157.0;
        double endY = 375.0;

        stickLine = new Line(startX, startY, endX, endY);

        stickLine.setStrokeWidth(10.0);
        stickLine.setStroke(Color.rgb(150, 75, 0));

        stickLine.setOpacity(0.0);

        scene = new Scene(new AnchorPane());

        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event -> {
            if (isStickExtending) {
                stickLine.setEndY(stickLine.getEndY() - 1);
            }
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        character = new Character(157.0, 400, 1.0, characterImageView);

        shark = new Shark(-6, 185, 100.0, sharkImageView);

        shark.translate(725);

        shark.moveDown();
        System.out.println("Shark moved forward to positionX: " + shark.getPositionX());

        double stickStartX = 157.0;
        double stickStartY = 375.0;

        currentStick = new Stick(stickStartX, stickStartY);
        if (plane != null) {
            plane.getChildren().add(currentStick.getStickLine());
            plane.getChildren().add(rectangle1);
            plane.getChildren().add(rectangle2);
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                try {
                    update(HelloController.this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        gameLoop.start();
    }

    Rectangle rectangle;
    public Image cherryImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/stickhero/cherry.png")));

    public boolean collectCherry() {
        return cherryImageView.getX() == characterImageView.getX();
    }

    private void resetGame() {
        character.moveDown();
        stickLine.setEndX(stickLine.getStartX());
        stickLine.setEndY(stickLine.getStartY());

//        rectangle = generateRandomRectangle();

        Random random1 = new Random();
        int x = random1.nextInt(75)+50;
//        int y = random1.nextInt(75)+50;
        cherryImageView.setOpacity(1);
        cherryImageView.setX(x);
//        cherryImageView.setY(y);
        characterImageView.setX(5);

    }

    private void mainReset() {

        Rectangle temp = rectangle2;
        rectangle1 = rectangle2;
        rectangle2 = rectangle;

    }
    boolean isDead = false;

    private void update(HelloController controller) throws IOException {
        gameTime++;
        accelerationTime++;
        controller.setGameStarted(true);

        if (madeContact(stickLine, rectangle2)) {
            System.out.println("Collision");
            scoreCounter++;
            Score.setScore(Score.getScore()+1);
            score.setText(String.valueOf(scoreCounter));
            score1.setText(String.valueOf(scoreCounter));
            score2.setText(String.valueOf(scoreCounter));
            gameLoop.stop();

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {
                resetGame();
                mainReset();

            });
            delay.play();
        } else {
            System.out.println("No Collision");
            gameLoop.stop();

            if (isStickExtending) {
                fallCharacter();
            }

            switchToEnd();
            System.out.println(Score.getScore());
            Score.highscore();
            if(score122!=null && score12!=null) {
                score122.setText(String.valueOf(Score.getHighscore()));
                score12.setText(String.valueOf(Score.getScore()));
                Score.setCurrentscore(Score.getScore());
                Score.setScore(0);
            }

        }
    }

    public void doNothing(ActionEvent event) throws IOException {
        switchToSceneOne(event);
        switchToSceneTwo(event);
        switchToSceneThree(event);
        switchToSceneFour(event);
        switchToSceneFive(event);
        switchToSceneSix(event);
        switchToSceneSeven(event);
        switchToSceneEight(event);
        switchToSceneNine(event);
        switchToSceneTen(event);
    }

    private void fallCharacter() {
        Timeline fallTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    moveCharacterY(characterImageView, 3.5);
                    isDead = true;
                })
        );

//        fallTimeline.setOnFinished(event -> {
//            try {
//                switchToEnd();
//                System.out.println(entities.score.getScore());
//                score122.setText(String.valueOf(entities.score.getScore()));
//                score12.setText(String.valueOf(entities.score.getScore()));
//                entities.score.setScore(0);
//
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });

        fallTimeline.setCycleCount((int) (planeHeight / 3.5)); // Adjust the cycle count based on the fall distance
        fallTimeline.play();
    }

    public void switchToStart(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("start.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene.getRoot().requestFocus();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    public void switchToHome() throws IOException {
        Score.setRevive(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        stage = (Stage) charstart.getScene().getWindow();

        // Get the controller from the loader
        HelloController controller = loader.getController();

        // Set the stage for the controller
        controller.setStage(stage);

        // Set the stickLine for the controller
        controller.setStickLine(stickLine);

        // Get the AnchorPane from the loaded root
        AnchorPane anchorPane = (AnchorPane) root;

        // Add the stickLine to the AnchorPane
        anchorPane.getChildren().add(stickLine);

        // Set up the scene with the loaded root
        Scene homeScene = new Scene(root);

        // Set up key event handlers
        homeScene.setOnKeyPressed(controller::handleKeyPress);
        homeScene.setOnKeyReleased(controller::handleKeyRelease);

        // Set the scene to the stage
        if (stage != null) {
            stage.setScene(homeScene);
            stage.show();
        } else {
            System.err.println("stage is null. Please check your FXML file.");
        }
    }
    public void revive(MouseEvent e) throws IOException {
        Score.setRevive(true);
        Score.setScore(Score.getCurrentscore());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        stage = (Stage) endbutton.getScene().getWindow();

        // Get the controller from the loader
        HelloController controller = loader.getController();

        // Set the stage for the controller
        controller.setStage(stage);

        // Set the stickLine for the controller
        controller.setStickLine(stickLine);

        // Get the AnchorPane from the loaded root
        AnchorPane anchorPane = (AnchorPane) root;

        // Add the stickLine to the AnchorPane
        anchorPane.getChildren().add(stickLine);

        // Set up the scene with the loaded root
        Scene homeScene = new Scene(root);

        // Set up key event handlers
        homeScene.setOnKeyPressed(controller::handleKeyPress);
        homeScene.setOnKeyReleased(controller::handleKeyRelease);

        // Set the scene to the stage
        if (stage != null) {
            stage.setScene(homeScene);
            stage.show();
        } else {
            System.err.println("stage is null. Please check your FXML file.");
        }
    }
    public void restart() throws IOException {
        Score.setRevive(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        stage = (Stage) endbutton.getScene().getWindow();

        // Get the controller from the loader
        HelloController controller = loader.getController();

        // Set the stage for the controller
        controller.setStage(stage);

        // Set the stickLine for the controller
        controller.setStickLine(stickLine);

        // Get the AnchorPane from the loaded root
        AnchorPane anchorPane = (AnchorPane) root;

        // Add the stickLine to the AnchorPane
        anchorPane.getChildren().add(stickLine);

        // Set up the scene with the loaded root
        Scene homeScene = new Scene(root);

        // Set up key event handlers
        homeScene.setOnKeyPressed(controller::handleKeyPress);
        homeScene.setOnKeyReleased(controller::handleKeyRelease);

        // Set the scene to the stage
        if (stage != null) {
            stage.setScene(homeScene);
            stage.show();
        } else {
            System.err.println("stage is null. Please check your FXML file.");
        }
    }

    public void switchToEnd() throws IOException {
        if ((isDead) && !(firstTime == 1)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end.fxml"));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("Cannot switch to end scene. Please check collision or game state.");
        }
    }

    private boolean madeContact(Line stick, Rectangle rectangle) {

        double stickEndX = stickEnd;
        double rectangleStartX = rectangle.getX();
        double rectangleEndX = rectangle.getX() + rectangle.getWidth();

        return stickEndX >= rectangleStartX && stickEndX <= rectangleEndX;
    }

    private void moveRectangleOutOfScreen(Rectangle rectangle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    movePlatform(rectangle, -3); // Adjust the amount based on your preference
                })
        );
        timeline.setCycleCount((int) ((int) rectangle.getWidth() / 1.5)); // Adjust the duration based on the width of the rectangle
        timeline.play();
    }

    private void movePlatform(Rectangle platform, double amount) {
        platform.setX(platform.getX() + amount);
    }

    private void moveCharacterX(ImageView characterImageView, double amount, int x) {
        characterImageView.setX(characterImageView.getX() + amount);
        x++;
    }

    private void moveCharacterY(ImageView characterImageView, double amount) {
        characterImageView.setY(characterImageView.getY() + amount);
    }



    private double widthMax = 160;
    private double widthMin = 45;

    private double spaceMin = 10;
    private double spaceMax = 250;

// posX = pos of rectangle2 + space

//    private Rectangle generateRandomRectangle() {
//        double randomWidth = random.nextDouble(50, 100);
//        double randomX = random.nextDouble(200, 400);
//
//        Rectangle newRandomRectangle = new Rectangle();
//        newRandomRectangle.setHeight(238);
//        newRandomRectangle.setWidth(randomWidth);
//        newRandomRectangle.setX(randomX);
//        newRandomRectangle.setY(385);
//        newRandomRectangle.setFill(Color.WHITE);
//
//        plane.getChildren().add(newRandomRectangle);
//
//        return newRandomRectangle;
//    }



    public void switchToSceneOne(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneTwo(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneThree(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneFour(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneFive(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneSix(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneSeven(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneEight(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneNine(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void switchToSceneTen(ActionEvent event) throws IOException{
        this.sceneSwitcher = sceneSwitcher;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void mediaPlayer() {
        Media media = new Media(String.valueOf(getClass().getResource("/com/example/stickhero/music.mp3")));

        // Create a MediaPlayer
        mediaPlayer = new MediaPlayer(media);

        // Set the volume (0.0 to 1.0)
        mediaPlayer.setVolume(0.5);

        // Set cycle count (MediaPlayer.INDEFINITE for indefinite looping)
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Play the music
        mediaPlayer.play();
    }
}