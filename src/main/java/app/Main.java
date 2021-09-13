package app;

import controller.MainSceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.config.Configuration;
import net.config.ConfigurationLoader;
import net.config.ConfigurationManager;

import javax.xml.bind.JAXBContext;
import java.io.*;
import java.util.Objects;
import java.util.concurrent.Callable;

public class Main extends Application
{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        new Thread(() -> {
            try {
                ConfigurationManager.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        MainSceneController.launch(stage);
    }

}
