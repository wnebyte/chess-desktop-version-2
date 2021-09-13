package app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ui.Clock;

public class ClockTest extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        Group root = new Group();
        Scene scene = new Scene(root, 480, 400);

        Clock clock = new Clock(Color.ORANGERED, Color.TRANSPARENT);
        clock.setLayoutX(45);
        clock.setLayoutY(186);
        clock.getTransforms().add(new Scale(0.83f, 0.83f, 0, 0));
        root.getChildren().add(clock);
        stage.setScene(scene);
        stage.show();
    }
}
