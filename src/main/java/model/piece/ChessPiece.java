package model.piece;

import model.*;
import model.state.BasicQueryableBoardState;
import model.state.QueryableBoardState;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class ChessPiece
{
    private static final Random rand = new Random();

    private final UUID uuid = new UUID(rand.nextInt(1000), rand.nextInt(500));

    private final Color color;

    public ChessPiece(Color color) {
        this.color = color;
    }

    public abstract List<Position> moves(BasicQueryableBoardState state);

    public List<Position> legalMoves(QueryableBoardState state) {
        Position position = state.getPosition(this);

        return moves(state).stream()
                .filter(newPos -> !state.update(new Action(position, newPos)).check(Color.invert(getColor())))
                .collect(Collectors.toList());
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof ChessPiece)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        ChessPiece chessPiece = (ChessPiece) o;
        return chessPiece.uuid.equals(this.uuid) && chessPiece.color.equals(this.color);
    }

    @Override
    public int hashCode() {
        return 33 + this.uuid.hashCode() + 12 + this.color.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("class=%s,color=%s",
                getClass().getSimpleName(), color);
    }
}
