package model;

public enum Color
{
    WHITE,
    BLACK;

    public static Color invert(Color color) {
        return color.equals(WHITE) ? BLACK : WHITE;
    }

    public static Color of(String color) {
        if (color.toUpperCase().equals(WHITE.toString())) {
            return WHITE;
        }
        else if (color.toUpperCase().equals(BLACK.toString())) {
            return BLACK;
        }
        else {
            throw new IllegalArgumentException(
                    "color does not match either of Color.WHITE or Color.BLACK."
            );
        }
    }
}
