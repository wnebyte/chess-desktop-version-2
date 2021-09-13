package model.piece;

import model.Color;
import model.state.BasicQueryableBoardState;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece
{
    public Knight(Color color) {
        super(color);
    }

    @Override
    public List<Position> moves(BasicQueryableBoardState state)
    {
        Position position = state.getPosition(this);
        List<Position> reachable = new ArrayList<>();

        try
        {
            Position pos1 = new Position(position, 1, 2);

            if (state.isEmpty(pos1) || state.hasEnemy(this, pos1))
            {
                reachable.add(pos1);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos2 = new Position(position, 2, 1);

            if (state.isEmpty(pos2) || state.hasEnemy(this, pos2))
            {
                reachable.add(pos2);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos3 = new Position(position, 1, -2);

            if (state.isEmpty(pos3) || state.hasEnemy(this, pos3))
            {
                reachable.add(pos3);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos4 = new Position(position, 2, -1);

            if (state.isEmpty(pos4) || state.hasEnemy(this, pos4))
            {
                reachable.add(pos4);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos5 = new Position(position, -1, 2);

            if (state.isEmpty(pos5) || state.hasEnemy(this, pos5))
            {
                reachable.add(pos5);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos6 = new Position(position, -2, 1);

            if (state.isEmpty(pos6) || state.hasEnemy(this, pos6))
            {
                reachable.add(pos6);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos7 = new Position(position, -1, -2);

            if (state.isEmpty(pos7) || state.hasEnemy(this, pos7))
            {
                reachable.add(pos7);
            }

        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos8 = new Position(position, -2, -1);

            if (state.isEmpty(pos8) || state.hasEnemy(this, pos8))
            {
                reachable.add(pos8);
            }

        } catch (IllegalStateException ignored) {}

        return reachable;
    }
}
