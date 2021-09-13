package app;

import controller.game.SinglePlayerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Color;
import model.player.Player;

import java.util.Arrays;

public class SinglePlayer extends Application
{
    private static final int SCENE_WIDTH = 600;

    private static final int SCENE_HEIGHT = 800;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader
                = FXMLLoader.load(getClass().getResource("/layout/layout_single_player_scene.fxml"));
        Parent parent = loader.getRoot();
        SinglePlayerController controller = loader.getController();
        controller.setPlayers(Arrays.asList(
                new Player(Color.WHITE), new Player(Color.BLACK)
        ));

        stage.setScene(new Scene(parent, SCENE_WIDTH, SCENE_HEIGHT));
        stage.show();
    }
}
