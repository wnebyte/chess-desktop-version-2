package controller.config;

import com.google.gson.JsonSyntaxException;
import common.Request;
import common.Response;
import common.util.JsonObjectBuilder;
import controller.MainSceneController;
import controller.game.MultiPlayerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.Color;
import model.player.LocalPlayer;
import model.player.RemotePlayer;
import net.Session;
import net.config.Connection;
import net.config.Port;
import net.Client;
import org.kordamp.ikonli.javafx.FontIcon;
import ui.builder.DialogBuilder;
import ui.util.GUIUtils;
import util.NetworkUtils;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MultiPlayerConfigController
{
    private static final URL layout = MultiPlayerConfigController.class
            .getResource("/layout/layout_multi_player_config_scene.fxml");

    private static final String title = "Multi-Player";

    @FXML
    private Region root;

    @FXML
    private Button joinMatchmakingButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button backButton;

    private final Dialog<String> networkUnavailable = new DialogBuilder<String>()
            .setTitle("Network Issues")
            .setContentText("There is currently no network connected to the Internet")
            .setGraphic(FontIcon.of(new FontIcon("mdi2n-network-off-outline")
                    .getIconCode(), 16))
            .addButtonType(new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE))
            .build();

    private final String HOST = "localhost";

    private final int PORT = 8080;

    private Client client;

    private boolean queued = false;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public void initialize() {
        EventHandler<ActionEvent> goBackEventHandler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Scene scene = root.getScene();
                    Stage stage = (Stage) scene.getWindow();
                    MainSceneController.setScene(stage, scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
       homeButton.setOnAction(goBackEventHandler);
       backButton.setOnAction(goBackEventHandler);
       joinMatchmakingButton.setOnAction(this::handleJoin);
    }

    private void handleJoin(ActionEvent event) {
        if (!(hasInternetConnection())) {
            networkUnavailable.showAndWait();
        }
        else if (!(queued)) {
            executor.submit(() -> {
                try {
                    client = new Client(HOST, PORT);
                    Request request = new Request(new Request.Header(Request.JOIN));

                    client.send(request);
                    Response response = client.read();

                    if (response.getHeader().getResultCode() == Response.OK) {
                        GUIUtils.runSafe(this::updateUIQueued);

                        try {
                            response = client.read();

                            if (response.getHeader().getResultCode() == Response.SESSION_INITIALIZED)
                            {
                                try {
                                    Session session = Session.fromJsonObject(response.getBody());
                                    GUIUtils.runSafe(() -> launchMultiPlayer(session));
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (SocketException ignored) {
                        } // socket closed outside of scope
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    client.close();
                }
            });
        } else {
            executor.submit(() -> {
               try {
                   if (isConnected(client)) {
                       Connection closed = close(client);
                       GUIUtils.runSafe(this::updateUINotQueued);
                       sendClosePacket(closed);
                   }
               } catch (IOException e) {
                //   e.printStackTrace();
               }
            });
        }
    }

    private void launchMultiPlayer(Session session) {
        try {
            LocalPlayer localPlayer = new LocalPlayer(session.getColor());
            session.setLocalPlayer(localPlayer);
            session.setPlayers(Arrays.asList(
                    localPlayer,
                    new RemotePlayer(Color.invert(session.getColor()))
            ));
            session.setClient(client);
            MultiPlayerController
                    .launch((Stage) root.getScene().getWindow(), root.getScene(), session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendClosePacket(Connection connection) throws IOException {
        Client client = new Client(HOST, PORT);
        client.send(new Request(new Request.Header(Request.LEAVE), new JsonObjectBuilder()
                .add("host", connection.getHost())
                .add("port", connection.getPort().getNumber())
                .build()));
        client.close();
    }

    private Connection close(Client client) {
        String host = client.getLocalHostAddress();
        int port = client.getLocalPort();
        client.close();
        return new Connection(host, new Port(port));
    }

    private boolean isConnected(Client client) {
        return (client != null) && (client.isConnected());
    }

    private boolean hasInternetConnection() {
        return NetworkUtils.isConnected();
    }

    private void updateUIQueued() {
        queued = true;
        joinMatchmakingButton.setText("Leave Matchmaking");
    }

    private void updateUINotQueued() {
        queued = false;
        joinMatchmakingButton.setText("Join Matchmaking");
    }

    public static void setScene(Stage stage, Scene scene) throws IOException {
        Parent root = FXMLLoader.load(layout);
        scene.setRoot(root);
        stage.setTitle(title);
        root.requestFocus();
        stage.show();
    }
}
