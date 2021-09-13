package ui;

import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import model.Position;

public interface Square<T extends Node>
{
    int X_AXIS = 1;

    int Y_AXIS = 2;

    void setFill(Paint fill);

    void resetFill();

    void setStroke(Paint stroke);

    void resetStroke();

    void setStrokeWidth(double strokeWidth);

    void resetStrokeWidth();

    void setStrokeRadius(double radius);

    void resetStrokeRadius();

    void addCircle(Circle circle);

    void removeCircle();

    void setStrokeType(StrokeType strokeType);

    double getCenterX();

    double getCenterY();

    void addEffect(Effect eff);

    T getElement();

    T removeElement();

    void addElement(T t);

    T setElement(T t);

    boolean hasElement();

    Position getPosition();
}
