package ui.util;

import javafx.application.Platform;

public class GUIUtils
{
    public static void runSafe(Runnable runnable)
    {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        }
        else {
            Platform.runLater(runnable);
        }
    }
}
