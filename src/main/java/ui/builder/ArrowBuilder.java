package ui.builder;

import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

public class ArrowBuilder
{
    private Paint fill = Color.BLUEVIOLET;

    private Paint stroke = fill;

    private double strokeWidth = 5.5;

    private StrokeType strokeType = StrokeType.CENTERED;

    private StrokeLineCap strokeLineCap = StrokeLineCap.ROUND;

    private StrokeLineJoin strokeLineJoin = StrokeLineJoin.BEVEL;

    private BlendMode blendMode = BlendMode.SRC_OVER;

    private double startX;

    private double startY;

    private double endX;

    private double endY;

    public ArrowBuilder setFill(Paint fill)
    {
        this.fill = fill;
        return this;
    }

    public ArrowBuilder setStroke(Paint stroke)
    {
        this.stroke = stroke;
        return this;
    }

    public ArrowBuilder setStrokeWidth(double strokeWidth)
    {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public ArrowBuilder setStrokeType(StrokeType strokeType)
    {
        this.strokeType = strokeType;
        return this;
    }

    public ArrowBuilder setStrokeLineCap(StrokeLineCap strokeLineCap)
    {
        this.strokeLineCap = strokeLineCap;
        return this;
    }

    public ArrowBuilder setStrokeLineJoin(StrokeLineJoin strokeLineJoin)
    {
        this.strokeLineJoin = strokeLineJoin;
        return this;
    }

    public ArrowBuilder setBlendMode(BlendMode blendMode)
    {
        this.blendMode = blendMode;
        return this;
    }

    public ArrowBuilder setStartX(double startX) {
        this.startX = startX;
        return this;
    }

    public ArrowBuilder setStartY(double startY) {
        this.startY = startY;
        return this;
    }

    public ArrowBuilder setEndX(double endX) {
        this.endX = endX;
        return this;
    }

    public ArrowBuilder setEndY(double endY) {
        this.endY = endY;
        return this;
    }

    public List<Line> build()
    {
        List<Line> components = new ArrayList<>(3);

        // get the slope of the line and find its angle
        double slope = (startY - endY) / (startX - endX);
        double lineAngle = Math.atan(slope);

        double arrowAngle = startX > endX ? Math.toRadians(45) : -Math.toRadians(225); // 30, 205

        if ((endX - startX) == 0)
        {
            arrowAngle = (360 - arrowAngle);
        }

        double lineLength = Math.sqrt(Math.pow(startX - endX, 2) + Math.pow(startY - endY, 2));
        double arrowLength = 20;

        Line line = new Line(startX, startY, endX, endY);
        line.setFill(fill);
        line.setStroke(stroke);
        line.setStrokeWidth(strokeWidth);
        line.setStrokeType(strokeType);
        line.setStrokeLineCap(strokeLineCap);
        line.setStrokeLineJoin(strokeLineJoin);
        line.setBlendMode(blendMode);

        // create the arrow legs
        Line arrow1 = new Line();
        arrow1.setFill(fill);
        arrow1.setStroke(stroke);
        arrow1.setStrokeWidth(strokeWidth);
        arrow1.setStrokeType(strokeType);
        arrow1.setStrokeLineCap(strokeLineCap);
        arrow1.setStrokeLineJoin(strokeLineJoin);
        arrow1.setBlendMode(blendMode);
        arrow1.setStartX(line.getEndX());
        arrow1.setStartY(line.getEndY());
        arrow1.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle - arrowAngle));
        arrow1.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle - arrowAngle));

        Line arrow2 = new Line();
        arrow2.setFill(fill);
        arrow2.setStroke(stroke);
        arrow2.setStrokeWidth(strokeWidth);
        arrow2.setStrokeType(strokeType);
        arrow2.setStrokeLineCap(strokeLineCap);
        arrow2.setStrokeLineJoin(strokeLineJoin);
        arrow2.setBlendMode(blendMode);
        arrow2.setStartX(line.getEndX());
        arrow2.setStartY(line.getEndY());
        arrow2.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle + arrowAngle));
        arrow2.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle + arrowAngle));

        components.add(line);
        components.add(arrow1);
        components.add(arrow2);
        return components;
    }
}
