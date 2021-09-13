package controller.misc;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.Clock;

public class CardController
{
    @FXML
    private Text playerName;

    @FXML
    private Clock clock;

    public void initialize() {

    }

    public final void setText(String text) {
        playerName.setText(text);
    }

    public final void setTextColor(Color color) {
        playerName.setFill(color);
    }

    public final void stopClock() {
        clock.stop();
    }

    public final void startClock() {
        clock.play();
    }
}
