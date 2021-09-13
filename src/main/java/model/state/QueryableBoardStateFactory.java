package model.state;

import model.Action;
import model.Color;
import model.Position;
import model.piece.*;

import java.util.Collections;
import java.util.HashMap;

public class QueryableBoardStateFactory
{
    public static HashMap<Position, ChessPiece> initial = new HashMap<>(32)
    {
        {
            put(new Position('A', 1), new Rook(Color.WHITE));
            put(new Position('H', 1), new Rook(Color.WHITE));
            put(new Position('A', 8), new Rook(Color.BLACK));
            put(new Position('H', 8), new Rook(Color.BLACK));
        }
    };

    private static final HashMap<Position, ChessPiece> checkmate_white = new HashMap<>()
    {
        {
            put(new Position('H', 1), new King(Color.WHITE));
            put(new Position('A', 1), new Rook(Color.WHITE));
            put(new Position('C', 1), new Rook(Color.WHITE));
            put(new Position('D', 3), new Queen(Color.WHITE));
            put(new Position('B', 8), new King(Color.BLACK));
        }
    };

    public static QueryableBoardState ofCheckmateWhite()
    {
        Origin origin = new Origin(new Action(new Position('B', 7), new Position('B', 8)),
                Color.BLACK, null, null,
                Collections.singletonList(StateDescription.MOVE));

        return new State(new HashMap<>(checkmate_white), null, origin);
    }

    public static HashMap<Position, ChessPiece> getInitialAsMap()
    {
        return initial;
    }

    public static QueryableBoardState ofInitial()
    {
        return new State(new HashMap<>(getInitialAsMap()), null, null);
    }

}
