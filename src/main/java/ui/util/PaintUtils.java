package ui.util;

import javafx.scene.paint.Paint;
import model.Color;

public class PaintUtils {

    public static Paint toPaint(Color color) {
        switch (color) {
            case WHITE:
                return javafx.scene.paint.Color.WHITE;
            case BLACK:
                return javafx.scene.paint.Color.BLACK;
            default:
                throw new IllegalArgumentException(
                        "color not supported."
                );
        }
    }
}
