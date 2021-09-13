package controller.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Action;
import model.Color;
import model.Position;
import net.Session;
import ui.util.GUIUtils;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiPlayerController extends GameController
{
    private static final URL layout = MultiPlayerController.class.getResource(
            "/layout/multi_player_scene.fxml");

    private static final String title = "Multi-Player Game";

    private Session session;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private static final Action INIT_DATA = new Action(new Position(1, 1), new Position(1, 1));

    private static final int READ_TIMEOUT = 30000;

    @Override
    protected boolean updateBoard(Action action) {
        boolean end = super.updateBoard(action);
        executor.execute(() -> {
            try {
                session.send(action);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if (end) {
            showTerminalStateDialog();
        }
        return end;
    }

    /**
     * Called internally to init class.
     */
    private void init() {
        if (session == null) {
            throw new IllegalStateException(
                    "Session must not be null at this stage."
            );
        }

        if (session.getLocalPlayer().getColor() == Color.BLACK) {
            board.rotate();
        }

        new Thread(() -> {
            try {
                session.send(INIT_DATA);
                session.read(READ_TIMEOUT);
                setPlayers(session.getPlayers());
                while (true) {
                    Action action = session.read();
                    GUIUtils.runSafe(() -> {
                        boolean end = super.updateBoard(action);
                        if (end) {
                            showTerminalStateDialog();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("EXIT!");
            }
        }).start();
    }

    private void setSession(Session session) {
        if (session == null) {
            throw new IllegalArgumentException(
                    "Session must not be null."
            );
        }
        this.session = session;
        init();
    }

    public static void launch(Stage stage, Scene scene, Session session) throws IOException {
        if ((stage == null) || (scene == null)) {
            throw new IllegalArgumentException(
                    "stage and/or scene must not be null."
            );
        }
        FXMLLoader loader = new FXMLLoader(layout);
        Parent root = loader.load();
        MultiPlayerController controller = (MultiPlayerController) loader.getController();
        controller.setSession(session);
        scene.setRoot(root);
        stage.setScene(scene);
        stage.setTitle(title);
        root.requestFocus();
        stage.show();
    }
}
