package ui;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.kordamp.ikonli.javafx.FontIcon;

public final class NetworkUnavailableDialog extends Dialog<String>
{
    private static final String title = "Network Issues";

    private static final Node graphic = FontIcon.of(new FontIcon("mdi2n-network-off-outline")
            .getIconCode(), 16);

    private static final String contentText = "There is currently no network connected to the Internet.";

    private static final ButtonType buttonType = new ButtonType("CANCEL", ButtonBar.ButtonData.CANCEL_CLOSE);

    public NetworkUnavailableDialog()
    {
        setTitle(title);
        setGraphic(graphic);
        setContentText(contentText);
        getDialogPane().getButtonTypes().add(buttonType);
    }

    /*
    mdi2h-help-network
    mdi2i-information-outline
    mdi2l-lan-disconnect
    mdi2m-minus-circle-off
    mdi2m-minus-network-outline
    mdi2r-router-wireless-off
    mdi2s-signal-off
    mdi2s-server-off
    mdi2s-server-network-off
    mdi2a-access-point-network-off
    mdi2n-network-off
    mdi2n-network-off-outline
     */
}
