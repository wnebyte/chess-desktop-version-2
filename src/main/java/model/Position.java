package model;

import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public class Position
{
    private final int x;

    private final int y;

    public final static Map<Character, Integer> of = new HashMap<>() {
        {
            put('A', 1); put('B', 2); put('C', 3); put('D', 4);
            put('E', 5); put('F', 6); put('G', 7); put('H', 8);
        }
    };

    public final static Map<Integer, Character> from = new HashMap<>() {
        {
            put(1, 'A'); put(2, 'B'); put(3, 'C'); put(4, 'D');
            put(5, 'E'); put(6, 'F'); put(7, 'G'); put(8, 'H');
        }
    };

    private final static int LOWER_BOUNDS = 1;

    private final static int UPPER_BOUNDS = 8;

    private static boolean outOfBounds(int i) {
        return !(LOWER_BOUNDS <= i && i <= UPPER_BOUNDS);
    }

    public Position(int x, int y) throws IllegalStateException
    {
        if ((outOfBounds(x)) || (outOfBounds(y))) {
            throw new IllegalStateException(
                    "x and/or y is out of bounds."
            );
        }
        this.x = x;
        this.y = y;
    }

    public Position(char x, int y) {
        this(of.get(Character.toUpperCase(x)), y);
    }

    public Position(Position position, int relX, int relY) {
        this(position.getX() + relX, position.getY() + relY);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Position)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        Position position = (Position) o;
        return position.getX() == this.getX() && position.getY() == this.getY();
    }

    @Override
    public int hashCode()
    {
        return 12 + 5 + this.getX() + 13 + this.getY();
    }

    @Override
    public String toString()
    {
        return String.format("x=%s,y=%d", from.get(x), y);
    }
}
