package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Shear;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public final class Clock extends Parent
{
    private final Digit[] digits = new Digit[7];

    private Timeline delayTimeLine, secondTimeLine;

    public Clock() {
        this(Color.WHITESMOKE, Color.TRANSPARENT);
    }

    public Clock(Color onColor, Color offColor)
    {
        // create LED effect
        Glow onEffect = new Glow(1.7f);
        onEffect.setInput(new InnerShadow());
        Glow onDotEffect = new Glow(1.7f);
        onDotEffect.setInput(new InnerShadow(5,Color.BLACK));
        InnerShadow offEffect = new InnerShadow();

        // create digits
        for (int i = 0; i < 6; i++)
        {
            Digit digit = new Digit(onColor, offColor, onEffect, offEffect);
            digit.setLayoutX(i * 80 + ((i + 1) % 2) * 20);
            digits[i] = digit;
            getChildren().add(digit);
        }

        // create dots
        Group dots = new Group(
                new Circle(80 + 54 + 20, 44, 6, onColor),
                new Circle(80 + 54 + 17, 64, 6, onColor),
                new Circle((80 * 3) + 54 + 20, 44, 6, onColor),
                new Circle((80 * 3) + 54 + 17, 64, 6, onColor)
        );
        dots.setEffect(onDotEffect);
        getChildren().add(dots);

        // update digits to current time and start timer to update every second
        refreshClocks();
    }

    private Duration duration = Duration.minutes(5);

    private final Duration interval = Duration.millis(1000);

    private void refreshClocks()
    {
        duration = duration.subtract(interval);
        long millis = Math.round(duration.toMillis());

        long hours =
                TimeUnit.MILLISECONDS.toHours(millis);
        long minutes =
                (TimeUnit.MILLISECONDS.toMinutes(millis) -
                        (TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))));
        long seconds =
                (TimeUnit.MILLISECONDS.toSeconds(millis) -
                        (TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));

        digits[0].showNumber((int) (hours / 10));
        digits[1].showNumber((int) (hours % 10));
        digits[2].showNumber((int) (minutes / 10));
        digits[3].showNumber((int) (minutes % 10));
        digits[4].showNumber((int) (seconds / 10));
        digits[5].showNumber((int) (seconds % 10));
    }

    public void play() {
        // wait till start of next second then start a timeline to call refreshClocks() every second
        delayTimeLine = new Timeline();
        delayTimeLine.getKeyFrames().add(
                new KeyFrame(new Duration(1000 - (System.currentTimeMillis() % 1000)), event ->
                {
                    if (secondTimeLine != null) {
                        secondTimeLine.stop();
                    }
                    secondTimeLine = new Timeline();
                    secondTimeLine.setCycleCount(Timeline.INDEFINITE);
                    secondTimeLine.getKeyFrames().add(
                            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
                            {
                                @Override public void handle(ActionEvent event) {
                                    refreshClocks();
                                }
                            }));
                    secondTimeLine.play();
                })
        );
        delayTimeLine.play();
    }

    public void stop(){
        delayTimeLine.stop();
        if (secondTimeLine != null) {
            secondTimeLine.stop();
        }
    }

    private static final class Digit extends Parent
    {
        private static final boolean[][] DIGIT_COMBINATIONS = new boolean[][]
                {
                        new boolean[] { true, false, true, true, true, true, true },
                        new boolean[] { false, false, false, false, true, false, true },
                        new boolean[] { true, true, true, false, true, true, false },
                        new boolean[] { true, true, true, false, true, false, true },
                        new boolean[] { false, true, false, true, true, false, true },
                        new boolean[] { true, true, true, true, false, false, true },
                        new boolean[] { true, true, true, true, false, true, true },
                        new boolean[] { true, false, false, false, true, false, true },
                        new boolean[] { true, true, true, true, true, true, true },
                        new boolean[] { true, true, true, true, true, false, true }
                };

        private final Polygon[] polygons = new Polygon[]
                {
                        new Polygon(2, 0, 52, 0, 42, 10, 12, 10),
                        new Polygon(12, 49, 42, 49, 52, 54, 42, 59, 12f, 59f, 2f, 54f),
                        new Polygon(12, 98, 42, 98, 52, 108, 2, 108),
                        new Polygon(0, 2, 10, 12, 10, 47, 0, 52),
                        new Polygon(44, 12, 54, 2, 54, 52, 44, 47),
                        new Polygon(0, 56, 10, 61, 10, 96, 0, 106),
                        new Polygon(44, 61, 54, 56, 54, 106, 44, 96)
                };

        private final Color onColor;

        private final Color offColor;

        private final Effect onEffect;

        private final Effect offEffect;

        public Digit(Color onColor, Color offColor, Effect onEffect, Effect offEffect)
        {
            this.onColor = onColor;
            this.offColor = offColor;
            this.onEffect = onEffect;
            this.offEffect = offEffect;
            getChildren().addAll(polygons);
            getTransforms().add(new Shear(-0.1,0));
            showNumber(0);
        }

        public void showNumber(Integer num)
        {
            if ((num < 0) || (9 < num)) {
                num = 0;
            }

            for (int i = 0; i < 7; i++)
            {
                polygons[i].setFill(DIGIT_COMBINATIONS[num][i] ? onColor : offColor);
                polygons[i].setEffect(DIGIT_COMBINATIONS[num][i] ? onEffect : offEffect);
            }
        }
    }
}
