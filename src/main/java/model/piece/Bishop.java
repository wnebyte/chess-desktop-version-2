package model.piece;

import model.Color;
import model.state.BasicQueryableBoardState;
import model.Position;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece
{
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public List<Position> moves(BasicQueryableBoardState state)
    {
        Position position = state.getPosition(this);
        List<Position> reachable = new ArrayList<>();

        var x = 1;
        var y = 1;
        var isEmpty = true;
        try
        {
            while (x <= 8 && y <= 8 && isEmpty)
            {
                Position newPos = new Position(position, x, y);
                isEmpty = state.isEmpty(newPos);

                if (isEmpty || state.hasEnemy(this, newPos))
                {
                    reachable.add(newPos);
                }
                else {
                    break;
                }
                x++;
                y++;
            }
        } catch (IllegalStateException ignored) {}

        x = 1;
        y = -1;
        isEmpty = true;
        try
        {
            while (x <= 8 && -8 <= y && isEmpty)
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
                y--;
            }
        } catch (IllegalStateException ignored) {}

        x = -1;
        y = 1;
        isEmpty = true;
        try
        {
            while (-8 <= x && y <= 8 && isEmpty)
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
                y++;
            }
        } catch (IllegalStateException ignored) {}

        x = -1;
        y = -1;
        isEmpty = true;
        try
        {
            while (-8 <= x && -8 <= y && isEmpty)
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
                y--;
            }
        } catch (IllegalStateException ignored) {}

        return reachable;
    }
}
