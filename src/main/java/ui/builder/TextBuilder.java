package ui.builder;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextBuilder
{
    private String text;

    private Font font;

    private Paint fill;

    private boolean bold = false;

    public TextBuilder(){}

    public TextBuilder(String text) {
        setText(text);
    }

    public TextBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextBuilder setFont(Font font) {
        this.font = font;
        return this;
    }

    public TextBuilder setFill(Paint fill) {
        this.fill = fill;
        return this;
    }

    public TextBuilder setBold(boolean value) {
        this.bold = value;
        return this;
    }

    public Text build()
    {
        Text text = new Text();

        if (this.text != null) {
            text.setText(this.text);
        }

        if (font != null) {
            text.setFont(font);
        }

        if (fill != null) {
            text.setFill(fill);
        }

        if (bold) {
            text.setStyle("-fx-font-weight: bold;");
        }

        return text;
    }

}
