package model.piece;

import model.*;
import model.state.BasicQueryableBoardState;
import model.state.QueryableBoardState;
import util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends ChessPiece
{
    public static final Position INIT_POSITION_WHITE = new Position('E', 1);

    public static final Position INIT_POSITION_BLACK = new Position('E', 8);

    public static final List<Position> INIT_POSITIONS = Arrays.asList
            (
                    INIT_POSITION_WHITE,
                    INIT_POSITION_BLACK
            );

    public static final Position CASTLE_KS_POSITION_WHITE = new Position('G', 1);

    public static final Position CASTLE_KS_POSITION_BLACK = new Position('G', 8);

    public static final Position CASTLE_QS_POSITION_WHITE = new Position('C', 1);

    public static final Position CASTLE_QS_POSITION_BLACK = new Position('C', 8);

    public static final Action CASTLE_KS_ACTION_WHITE = new Action
            (
                    INIT_POSITION_WHITE,
                    CASTLE_KS_POSITION_WHITE
            );

    public static final Action CASTLE_KS_ACTION_BLACK = new Action
            (
                    INIT_POSITION_BLACK,
                    CASTLE_KS_POSITION_BLACK
            );

    public static final Action CASTLE_QS_ACTION_WHITE = new Action
            (
                    INIT_POSITION_WHITE,
                    CASTLE_QS_POSITION_WHITE
            );

    public static final Action CASTLE_QS_ACTION_BLACK = new Action
            (
                    INIT_POSITION_BLACK,
                    CASTLE_QS_POSITION_BLACK
            );

    public static final List<Action> CASTLE_ACTIONS_WHITE = Arrays.asList
            (
                    CASTLE_KS_ACTION_WHITE,
                    CASTLE_QS_ACTION_WHITE
            );

    public static final List<Action> CASTLE_ACTIONS_BLACK = Arrays.asList
            (
                    CASTLE_KS_ACTION_BLACK,
                    CASTLE_QS_ACTION_BLACK
            );

    public static final List<Action> CASTLE_ACTIONS = CollectionUtils.merge
            (
                    CASTLE_ACTIONS_WHITE,
                    CASTLE_ACTIONS_BLACK
            );

    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> moves(BasicQueryableBoardState state)
    {
        if ((state == null)) {
            throw new IllegalArgumentException(
                    "BasicQueryableBoardState must not be null."
            );
        }

        Position position = state.getPosition(this);
        List<Position> reachable = new ArrayList<>(8);

        try
        {
            Position pos1 = new Position(position, 1, 0);

            if (state.isEmpty(pos1) || state.hasEnemy(this, pos1))
            {
                reachable.add(pos1);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos2 = new Position(position, -1, 0);

            if (state.isEmpty(pos2) || state.hasEnemy(this, pos2))
            {
                reachable.add(pos2);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos3 = new Position(position, 0, 1);

            if (state.isEmpty(pos3) || state.hasEnemy(this, pos3))
            {
                reachable.add(pos3);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos4 = new Position(position, 0, -1);

            if (state.isEmpty(pos4) || state.hasEnemy(this, pos4))
            {
                reachable.add(pos4);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos5 = new Position(position, 1, 1);

            if (state.isEmpty(pos5) || state.hasEnemy(this, pos5))
            {
                reachable.add(pos5);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos6 = new Position(position, 1, -1);

            if (state.isEmpty(pos6) || state.hasEnemy(this, pos6))
            {
                reachable.add(pos6);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos7 = new Position(position, -1, 1);

            if (state.isEmpty(pos7) || state.hasEnemy(this, pos7))
            {
                reachable.add(pos7);
            }
        } catch (IllegalStateException ignored) {}

        try
        {
            Position pos8 = new Position(position, -1, -1);

            if (state.isEmpty(pos8) || state.hasEnemy(this, pos8))
            {
                reachable.add(pos8);
            }
        } catch (IllegalStateException ignored) {}

        return reachable;
    }

    @Override
    public List<Position> legalMoves(QueryableBoardState state)
    {
        if ((state == null)) {
            throw new IllegalArgumentException(
                    "QueryableBoardState must not be null."
            );
        }

        Position position = state.getPosition(this);
        List<Position> legalMoves = super.legalMoves(state);

        if (INIT_POSITIONS.contains(position))
        {
            try
            {
                Position ksPos = new Position(position, 2, 0);

                if (state.canCastle(this, ksPos))
                {
                    legalMoves.add(ksPos);
                }

            } catch (IllegalStateException ignored) {}

            try
            {
                Position qsPos = new Position(position, -2, 0);

                if (state.canCastle(this, qsPos))
                {
                    legalMoves.add(qsPos);
                }

            } catch (IllegalStateException ignored) {}
        }

        return legalMoves;
    }
}
