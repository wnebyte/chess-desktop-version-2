package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import model.Position;

import java.util.Objects;

public class StackSquare<T extends Node> extends StackPane implements Square<T>
{
    public static final int X_AXIS = 1;

    public static final int Y_AXIS = 2;

    private T elementData = null;

    private final Rectangle rectangle;

    private final Paint fill;

    private final Paint stroke;

    private final double strokeWidth;

    private final Text xText;

    private final Text yText;

    private final Position position;

    public StackSquare(Position position, double width, double height, Paint fill, Paint stroke, double strokeWidth,
                       Text xText, Text yText)
    {
        this.rectangle = new Rectangle(width, height);
        this.rectangle.setFill(fill);
        this.rectangle.setStroke(stroke);
        this.rectangle.setStrokeWidth(strokeWidth);
        this.fill = fill;
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
        this.xText = Objects.requireNonNullElseGet(xText, Text::new);
        this.yText = Objects.requireNonNullElseGet(yText, Text::new);
        this.position = position;
        getChildren().addAll(this.rectangle, this.xText, this.yText);
    }

    public void setFill(Paint fill) {
        rectangle.setFill(fill);
    }

    public void resetFill() {
        rectangle.setFill(fill);
    }

    public void setStroke(Paint stroke) {
        rectangle.setStroke(stroke);
    }

    public void resetStroke() {
        rectangle.setStroke(stroke);
    }

    public void setStrokeWidth(double strokeWidth) {
        rectangle.setStrokeWidth(strokeWidth);
    }

    public void resetStrokeWidth() {
        rectangle.setStrokeWidth(strokeWidth);
    }

    public void setStrokeType(StrokeType strokeType)
    {
        rectangle.setStrokeType(strokeType);
    }

    public void setStrokeRadius(double radius) {
        rectangle.setArcWidth(radius);
        rectangle.setArcHeight(radius);
    }

    public void resetStrokeRadius() {
        rectangle.setArcWidth(0);
        rectangle.setArcHeight(0);
    }

    public void showText(boolean show, int axis) {
        switch (axis)
        {
            case X_AXIS:
                xText.setVisible(show);
                break;
            case Y_AXIS:
                yText.setVisible(show);
        }
    }

    public void setTextMargin(Insets insets, int axis)
    {
        switch (axis)
        {
            case Square.X_AXIS:
                StackPane.setMargin(xText, insets);
                break;
            case Square.Y_AXIS:
                StackPane.setMargin(yText, insets);
                break;
        }
    }

    public void setTextAlignment(Pos pos, int axis)
    {
        switch (axis)
        {
            case Square.X_AXIS:
                StackPane.setAlignment(xText, pos);
                break;
            case Square.Y_AXIS:
                StackPane.setAlignment(yText, pos);
                break;
        }
    }

    public void addCircle(Circle circle)
    {
        getChildren().add(circle);

        if (hasElement())
        {
            getElement().toFront();
        }
    }

    public void removeCircle()
    {
        getChildren().removeIf(child -> child instanceof Circle);
    }

    public double layoutX()
    {
        return getLayoutX();
    }

    public double layoutY()
    {
        return getLayoutY();
    }

    public double getCenterX()
    {
        return getLayoutX() + (rectangle.getWidth() / 2.0);
    }

    public double getCenterY()
    {
        return getLayoutY() + (rectangle.getHeight() / 2.0);
    }

    public void addEffect(Effect effect)
    {
        rectangle.setEffect(effect);
    }

    public void rotate() {
        double currentValue = getRotate();
        double newValue = (currentValue == 180) ? 0 : 180;
        setRotate(newValue);
        getChildren().forEach(child -> setRotate(newValue));
    }

    public T getElement() {
        return elementData;
    }

    public void addElement(T t)
    {
        if (hasElement()) {
            removeElement();
        }
        elementData = t;
        getChildren().add(elementData);
    }

    public T removeElement()
    {
        if (elementData != null) {
            getChildren().remove(elementData);
        }
        T t = elementData;
        elementData = null;
        return t;
    }

    public boolean hasElement() {
        return elementData != null;
    }

    public T setElement(T t)
    {
        T val = removeElement();
        addElement(t);
        return val;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StackSquare)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        @SuppressWarnings("unchecked")
        StackSquare<T> square = (StackSquare<T>) o;
        return square.getPosition().equals(this.position);
    }

    @Override
    public int hashCode() {
       return 12 + this.getPosition().hashCode() + 3;
    }

    @Override
    public String toString()
    {
        return getPosition().toString();
    }
}
