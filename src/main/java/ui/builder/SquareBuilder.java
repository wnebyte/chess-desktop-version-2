package ui.builder;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import model.Position;
import ui.Square;
import ui.StackSquare;

public class SquareBuilder<T extends Node>
{
    private double width = 75;

    private double height = 75;

    private Paint fill = null;

    private Paint stroke = null;

    private double strokeWidth = 0.0;

    private StrokeType strokeType = null;

    private double strokeRadius = 0;

    private Text xText;

    private Text yText;

    private boolean showXText = false;

    private boolean showYText = false;

    private Insets xTextInsets = null;

    private Insets yTextInsets = null;

    private Pos xTextPos = null;

    private Pos yTextPos = null;

    private Position position = null;

    private Effect effect = null;

    private T elementData = null;

    public SquareBuilder() { }

    public SquareBuilder<T> setWidth(double width) {
        this.width = 50;
        return this;
    }

    public SquareBuilder<T> setHeight(int height) {
        this.height = 50;
        return this;
    }

    public SquareBuilder<T> setSize(double size) {
        this.width = size;
        this.height = size;
        return this;
    }

    public SquareBuilder<T> setFill(Paint fill) {
        this.fill = fill;
        return this;
    }

    public SquareBuilder<T> setText(Text text, int axis) {
        switch (axis)
        {
            case Square.X_AXIS:
                xText = text;
                break;
            case Square.Y_AXIS:
                yText = text;
                break;
        }

        return this;
    }

    public SquareBuilder<T> showText(boolean show, int axis) {
        switch (axis)
        {
            case Square.X_AXIS:
                showXText = show;
                break;
            case Square.Y_AXIS:
                showYText = show;
                break;
        }

        return this;
    }

    public SquareBuilder<T> setTextMargin(Insets insets, int axis)
    {
        switch (axis)
        {
            case Square.X_AXIS:
                xTextInsets = insets;
                break;
            case Square.Y_AXIS:
                yTextInsets = insets;
                break;
        }

        return this;
    }

    public SquareBuilder<T> setTextAlignment(Pos pos, int axis)
    {
        switch (axis)
        {
            case Square.X_AXIS:
                xTextPos = pos;
                break;
            case Square.Y_AXIS:
                yTextPos = pos;
                break;
        }

        return this;
    }

    public SquareBuilder<T> setStroke(Paint stroke) {
        this.stroke = stroke;
        return this;
    }

    public SquareBuilder<T> setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public SquareBuilder<T> setStrokeType(StrokeType strokeType)
    {
        this.strokeType = strokeType;
        return this;
    }

    public SquareBuilder<T> setStrokeRadius(double radius) {
        strokeRadius = radius;
        return this;
    }

    public SquareBuilder<T> setPosition(Position position) {
        this.position = position;
        return this;
    }

    public SquareBuilder<T> setEffect(Effect effect) {
        this.effect = effect;
        return this;
    }

    public SquareBuilder<T> setElement(T t) {
        this.elementData = t;
        return this;
    }

    public StackSquare<T> build()
    {
        if (position == null) {
            throw new IllegalStateException(
                    "x and/or y have not initialized."
            );
        }
        if (fill == null) {
            throw new IllegalStateException(
                    "fill has not been initialized."
            );
        }
        if (stroke == null) {
            stroke = fill;
        }

        StackSquare<T> square =
                new StackSquare<T>(position, width, height, fill, stroke, strokeWidth, xText, yText);

        if (elementData != null) {
            square.addElement(elementData);
        }

        if (strokeType != null) {
            square.setStrokeType(strokeType);
        }

        if (xTextInsets != null) {
            square.setTextMargin(xTextInsets, Square.X_AXIS);
        }

        if (yTextInsets != null) {
            square.setTextMargin(yTextInsets, Square.Y_AXIS);
        }

        if (xTextPos != null) {
            square.setTextAlignment(xTextPos, Square.X_AXIS);
        }

        if (yTextPos != null) {
            square.setTextAlignment(yTextPos, Square.Y_AXIS);
        }

        if (effect != null) {
            square.addEffect(effect);
        }

        square.setStrokeRadius(strokeRadius);
        square.showText(showXText, Square.X_AXIS);
        square.showText(showYText, Square.Y_AXIS);

        return square;
    }

}