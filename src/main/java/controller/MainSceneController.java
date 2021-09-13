package controller;

import controller.config.MultiPlayerConfigController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public final class MainSceneController
{
    private static final URL layout = MainSceneController.class.getResource("/layout/layout_main_scene.fxml");

    private static final String title = "Home";

    @FXML
    private Region root;

    @FXML
    private Button multiPlayerButton;

    @FXML
    private Button homeButton;

    public void initialize() {
        multiPlayerButton.setOnAction(this::handleMultiPlayerButtonActionEvent);
    }

    private void handleMultiPlayerButtonActionEvent(ActionEvent event)
    {
        try {
            Scene scene = root.getScene();
            Stage stage = (Stage) scene.getWindow();
            MultiPlayerConfigController.setScene(stage, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(Stage stage, Scene scene) throws IOException {
        Parent root = FXMLLoader.load(layout);
        scene.setRoot(root);
        stage.setTitle(title);
        root.requestFocus();
        stage.show();
    }

    public static void setNewScene(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(layout);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        root.requestFocus();
        stage.show();
    }

    public static void launch(Stage stage) throws IOException {
        if (Objects.isNull(stage)) {
            throw new IllegalArgumentException(
                    "Stage must not be null."
            );
        }
        Parent root = FXMLLoader.load(layout);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle(title);
        root.requestFocus();
        stage.show();
    }
}
