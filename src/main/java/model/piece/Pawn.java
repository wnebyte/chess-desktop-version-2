package model.piece;

import model.Color;
import model.state.BasicQueryableBoardState;
import model.Position;
import util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn extends ChessPiece
{
    public static final List<Position> INIT_POSITIONS_WHITE = Arrays.asList
            (
                    new Position('A', 2),
                    new Position('B', 2),
                    new Position('C', 2),
                    new Position('D', 2),
                    new Position('E', 2),
                    new Position('F', 2),
                    new Position('G', 2),
                    new Position('H', 2)
            );

    public static final List<Position> INIT_POSITIONS_BLACK = Arrays.asList
            (
                    new Position('A', 7),
                    new Position('B', 7),
                    new Position('C', 7),
                    new Position('D', 7),
                    new Position('E', 7),
                    new Position('F', 7),
                    new Position('G', 7),
                    new Position('H', 7)
            );

    public static final List<Position> INIT_POSITIONS = CollectionUtils.merge
            (
                    INIT_POSITIONS_WHITE,
                    INIT_POSITIONS_BLACK
            );

    public static final List<Position> PROMOTION_POSITIONS_WHITE = Arrays.asList
            (
                    new Position('A', 8),
                    new Position('B', 8),
                    new Position('C', 8),
                    new Position('D', 8),
                    new Position('E', 8),
                    new Position('F', 8),
                    new Position('G', 8),
                    new Position('H', 8)
            );

    public static final List<Position> PROMOTION_POSITIONS_BLACK = Arrays.asList
            (
                    new Position('A', 1),
                    new Position('B', 1),
                    new Position('C', 1),
                    new Position('D', 1),
                    new Position('E', 1),
                    new Position('F', 1),
                    new Position('G', 1),
                    new Position('H', 1)
            );

    public static final List<Position> PROMOTION_POSITIONS = CollectionUtils.merge
            (
                    PROMOTION_POSITIONS_WHITE,
                    PROMOTION_POSITIONS_BLACK
            );

    public static final List<Position> FOURTH_RANK = Arrays.asList
            (
                    new Position('A', 4),
                    new Position('B', 4),
                    new Position('C', 4),
                    new Position('D', 4),
                    new Position('E', 4),
                    new Position('F', 4),
                    new Position('G', 4),
                    new Position('H', 4)
            );

    public static final List<Position> FIFTH_RANK = Arrays.asList
            (
                    new Position('A', 5),
                    new Position('B', 5),
                    new Position('C', 5),
                    new Position('D', 5),
                    new Position('E', 5),
                    new Position('F', 5),
                    new Position('G', 5),
                    new Position('H', 5)
            );

    public static final List<Position> END_TWO_SQUARE_MOVE_POSITIONS = CollectionUtils.merge
            (
                    FOURTH_RANK,
                    FIFTH_RANK
            );

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Position> moves(BasicQueryableBoardState state)
    {
        Position position = state.getPosition(this);
        List<Position> reachable = new ArrayList<>();

        if (getColor() == Color.WHITE)
        {
            try
            {
                Position pos1 = new Position(position, 0, 1);

                if (state.isEmpty(pos1))
                {
                    reachable.add(pos1);
                }

                if (INIT_POSITIONS_WHITE.contains(position))
                {
                    Position pos2 = new Position(position, 0, 2);

                    if (state.isEmpty(pos1, pos2))
                    {
                        reachable.add(pos2);
                    }
                }
            } catch (IllegalStateException ignored) {}

            try
            {
                Position pos3 = new Position(position, 1, 1);

                if ((state.hasEnemy(this, pos3)) ||
                        (FIFTH_RANK.contains(position) && state.canCaptureEnPassant(this, pos3)))
                {
                    reachable.add(pos3);
                }
            } catch (IllegalStateException ignored) {}

            try
            {
                Position pos4 = new Position(position, -1, 1);

                if ((state.hasEnemy(this, pos4)) ||
                        (FIFTH_RANK.contains(position) && state.canCaptureEnPassant(this, pos4)))
                {
                    reachable.add(pos4);
                }
            } catch (IllegalStateException ignored) {}

        }

        else if (getColor() == Color.BLACK)
        {
            try
            {
                Position pos1 = new Position(position, 0, -1);

                if (state.isEmpty(new Position[]{pos1}))
                {
                    reachable.add(pos1);
                }

                if (INIT_POSITIONS_BLACK.contains(position))
                {
                    Position pos2 = new Position(position, 0, -2);

                    if (state.isEmpty(pos1, pos2))
                    {
                        reachable.add(pos2);
                    }
                }
            } catch (IllegalStateException ignored) {}

            try
            {
                Position pos3 = new Position(position, 1, -1);

                if ((state.hasEnemy(this, pos3)) ||
                        (FOURTH_RANK.contains(position) && state.canCaptureEnPassant(this, pos3)))
                {
                    reachable.add(pos3);
                }
            } catch (IllegalStateException ignored) {}

            try {
                Position pos4 = new Position(position, -1, -1);

                if ((state.hasEnemy(this, pos4)) ||
                        (FOURTH_RANK.contains(position) && state.canCaptureEnPassant(this, pos4)))
                {
                    reachable.add(pos4);
                }
            } catch (IllegalStateException ignored) {}

        }

        return reachable;
    }
}