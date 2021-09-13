package model.piece;

import model.Action;
import model.Color;
import model.state.BasicQueryableBoardState;
import model.Position;

import java.util.*;

public class Rook extends ChessPiece
{
    public static final Position INIT_KS_POSITION_WHITE = new Position('H', 1);

    public static final Position INIT_KS_POSITION_BLACK = new Position('H', 8);

    public static final Position INIT_QS_POSITION_WHITE = new Position('A', 1);

    public static final Position INIT_QS_POSITION_BLACK = new Position('A', 8);

    public static final Position CASTLE_KS_POSITION_WHITE = new Position('F', 1);

    public static final Position CASTLE_KS_POSITION_BLACK = new Position('F', 8);

    public static final Position CASTLE_QS_POSITION_WHITE = new Position('D', 1);

    public  static final Position CASTLE_QS_POSITION_BLACK = new Position('D', 8);

    public static final Action CASTLE_KS_ACTION_WHITE =
            new Action(INIT_KS_POSITION_WHITE, CASTLE_KS_POSITION_WHITE);

    public  static final Action CASTLE_KS_ACTION_BLACK =
            new Action(INIT_KS_POSITION_BLACK, CASTLE_KS_POSITION_BLACK);

    public static final Action CASTLE_QS_ACTION_WHITE =
            new Action(INIT_QS_POSITION_WHITE, CASTLE_QS_POSITION_WHITE);

    public  static final Action CASTLE_QS_ACTION_BLACK =
            new Action(INIT_QS_POSITION_BLACK, CASTLE_QS_POSITION_BLACK);

    public  static final List<Action> CASTLE_ACTIONS_WHITE =
            Arrays.asList(CASTLE_KS_ACTION_WHITE, CASTLE_QS_ACTION_WHITE);

    public  static final List<Action> CASTLE_ACTIONS_BLACK =
            Arrays.asList(CASTLE_KS_ACTION_BLACK, CASTLE_QS_ACTION_BLACK);

    public static final Map<Action, Action> CASTLE_RESPONSE = new HashMap<>(4)
    {
        {
            put(King.CASTLE_KS_ACTION_WHITE, Rook.CASTLE_KS_ACTION_WHITE);
            put(King.CASTLE_QS_ACTION_WHITE, Rook.CASTLE_QS_ACTION_WHITE);
            put(King.CASTLE_KS_ACTION_BLACK, Rook.CASTLE_KS_ACTION_BLACK);
            put(King.CASTLE_QS_ACTION_BLACK, Rook.CASTLE_QS_ACTION_BLACK);
        }
    };

    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Position> moves(BasicQueryableBoardState state)
    {
        Position position = state.getPosition(this);
        List<Position> reachable = new ArrayList<>();

        var x = 1;
        var y = 0;
        var isEmpty = true;
        try
        {
            while (x <= 8 && isEmpty)
            {
                Position newPos = new Position(position, x, y);
                isEmpty = state.isEmpty(newPos);

                if (isEmpty || state.hasEnemy(this, newPos))
                {
                    reachable.add(newPos);
                } else {
                    break;
                }
                x++;
            }
        } catch (IllegalStateException ignored) {}

        x = -1;
        y = 0;
        isEmpty = true;
        try
        {
            while (-8 <= x && isEmpty)
            {
                Position newPos = new Position(position, x, y);
                isEmpty = state.isEmpty(newPos);

                if (isEmpty || state.hasEnemy(this, newPos))
                {
                    reachable.add(newPos);
                } else {
                    break;
                }
                x--;
            }
        } catch (IllegalStateException ignored) {}

        x = 0;
        y = 1;
        isEmpty = true;
        try
        {
            while (y <= 8 && isEmpty)
            {
                Position newPos = new Position(position, x, y);
                isEmpty = state.isEmpty(newPos);

                if (isEmpty || state.hasEnemy(this, newPos))
                {
                    reachable.add(newPos);
                } else {
                    break;
                }
                y++;
            }
        } catch (IllegalStateException ignored) {}

        x = 0;
        y = -1;
        isEmpty = true;
        try
        {
            while (-8 <= y && isEmpty)
            {
                Position newPos = new Position(position, x, y);
                isEmpty = state.isEmpty(newPos);

                if (isEmpty || state.hasEnemy(this, newPos))
                {
                    reachable.add(newPos);
                } else {
                    break;
                }
                y--;
            }
        } catch (IllegalStateException ignored) {}

        return reachable;
    }
}