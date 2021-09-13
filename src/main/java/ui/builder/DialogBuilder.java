package ui.builder;

import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class DialogBuilder<R> {

    private final Dialog<R> dialog = new Dialog<>();

    public DialogBuilder<R> setGraphic(Node graphic) {
        dialog.setGraphic(graphic);
        return this;
    }

    public DialogBuilder<R> setHeaderText(String headerText) {
        dialog.setHeaderText(headerText);
        return this;
    }

    public DialogBuilder<R> setContentText(String contentText) {
        dialog.setContentText(contentText);
        return this;
    }

    public DialogBuilder<R> addButtonType(ButtonType buttonType) {
        dialog.getDialogPane().getButtonTypes().add(buttonType);
        return this;
    }

    public DialogBuilder<R> setTitle(String title) {
        dialog.setTitle(title);
        return this;
    }

    public Dialog<R> build() {
        return dialog;
    }
}
