package model.piece;

import model.Color;
import model.state.BasicQueryableBoardState;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece
{
    public Queen(Color color) {
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

        x = 1;
        y = 1;
        isEmpty = true;
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