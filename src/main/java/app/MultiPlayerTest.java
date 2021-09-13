package app;

import controller.game.MultiPlayerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Color;
import model.player.LocalPlayer;
import model.player.Player;
import model.player.RemotePlayer;
import net.Session;

import java.util.Arrays;

public class MultiPlayerTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Session session = Session.fromJSON("{color: WHITE, connection-id: 1, socket-id: 2}");
        session.setPlayers(Arrays.asList(new LocalPlayer(Color.WHITE), new RemotePlayer(Color.BLACK)));
        MultiPlayerController.launch(stage, new Scene(new AnchorPane()), session);
    }
}
