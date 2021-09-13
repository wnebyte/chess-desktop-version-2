package ui.builder;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class DropShadowBuilder
{
    private double offsetX = 0;

    private double offsetY = 0;

    private Color color = Color.BLACK;

    public DropShadowBuilder setOffsetX(double offsetX) {
        this.offsetX = offsetX;
        return this;
    }

    public DropShadowBuilder setOffsetY(double offsetY) {
        this.offsetY = offsetY;
        return this;
    }

    public DropShadowBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public DropShadow build()
    {
        DropShadow ds = new DropShadow();
        ds.setOffsetX(offsetX);
        ds.setOffsetY(offsetY);
        ds.setColor(color);
        return ds;
    }
}
