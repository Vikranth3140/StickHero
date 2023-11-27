// HelloController.java
package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Line stickLine;

    private Timeline timeline;

    private boolean isSpaceBarPressed = false;
    private boolean isStickExtending = false;

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
        }
    }

    @FXML
    private void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            isSpaceBarPressed = false;
            isStickExtending = false;
        }
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set initial coordinates for the stick
        double startX = 157.0;
        double startY = 475.0;
        double endX = 157.0;
        double endY = 475.0;

        // Create a new Line object
        stickLine = new Line(startX, startY, endX, endY);

        // Create a KeyFrame to update the stick line position
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event -> {
            if (isStickExtending) {
                // Update the position of the stick line
                stickLine.setEndY(stickLine.getEndY() - 1); // Adjust the speed as needed
            }
        });

        // Create a timeline with the key frame
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
    }

    public void switchToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

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

        // Create a KeyFrame to update the stick line position
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10), event -> {
            if (isStickExtending) {
                // Update the position of the stick line
                stickLine.setEndY(stickLine.getEndY() - 1); // Adjust the speed as needed
            }
        });

        // Create a timeline with the key frame
        controller.setTimeline(new Timeline(keyFrame));
        controller.getTimeline().setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely

        // Set up the scene with the loaded root
        Scene homeScene = new Scene(root);

        // Set up key event handlers
        homeScene.setOnKeyPressed(controller::handleKeyPress);
        homeScene.setOnKeyReleased(controller::handleKeyRelease);

        // Set the scene to the stage
        stage.setScene(homeScene);
        stage.show();
    }




    public void switchToEnd() throws IOException {
        if (stickLine != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("end.fxml"));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } else {
            System.err.println("stickLine is null. Please check your FXML file.");
        }
    }
}