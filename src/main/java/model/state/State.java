package model.state;

import model.Action;
import model.Color;
import model.Position;
import model.piece.*;
import util.CollectionUtils;
import java.util.*;
import java.util.stream.Collectors;

public class State implements QueryableBoardState
{
    private final HashMap<Position, ChessPiece> squares;

    private final HashMap<Position, Boolean> hasMoved;

    private final Origin origin;

    private final InnerState innerState = new InnerState();

    private double utility;

    private Action action;

    public State(HashMap<Position, ChessPiece> squares,
                 HashMap<Position, Boolean> hasMoved,
                 Origin origin)
    {
        hasMoved = new HashMap<>(CollectionUtils.copyOfVal(hasMoved));
        this.squares = Objects.requireNonNullElseGet(squares, QueryableBoardStateFactory::getInitialAsMap);
        this.hasMoved = Objects.requireNonNullElseGet(hasMoved, () -> new HashMap<Position, Boolean>()
        {
            {
                put(new Position('A', 1), false);
                put(new Position('E', 1), false);
                put(new Position('H', 1), false);
                put(new Position('A', 8), false);
                put(new Position('E', 8), false);
                put(new Position('H', 8), false);
            }
        });
        this.origin = Objects.requireNonNullElse(origin, Origin.init());
    }

    @Override
    public Map<Position, ChessPiece> getChessPieces()
    {
        return squares;
    }

    @Override
    public void setUtility(final double utility) {
        this.utility = utility;
    }

    @Override
    public double getUtility() {
        return utility;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public Origin getOrigin()
    {
        return origin;
    }

    @Override
    public Map<Position, Boolean> getHasMoved()
    {
        return hasMoved;
    }

    @Override
    public Position getPosition(ChessPiece chessPiece) {
        return innerState.getPosition(chessPiece);
    }

    @Override
    public boolean isEmpty(Position position) {
        return innerState.isEmpty(position);
    }

    @Override
    public boolean isEmpty(Position... positions) {
        return innerState.isEmpty(positions);
    }

    @Override
    public boolean hasEnemy(ChessPiece chessPiece, Position position) {
        return innerState.hasEnemy(chessPiece, position);
    }

    @Override
    public boolean canCastle(ChessPiece chessPiece, Position position)
    {
        if (!(chessPiece instanceof King)) {
            return false;
        }

        if ((position.equals(King.CASTLE_KS_POSITION_WHITE)) ||
                (position.equals(King.CASTLE_KS_POSITION_BLACK)))
        {
            return innerState.canCastleKingSide(chessPiece);
        }

        if ((position.equals(King.CASTLE_QS_POSITION_WHITE)) ||
                (position.equals(King.CASTLE_QS_POSITION_BLACK)))
        {
            return innerState.canCastleQueenSide(chessPiece);
        }

        return false;
    }

    @Override
    public boolean canCaptureEnPassant(ChessPiece chessPiece, Position position)
    {
        return innerState.canCaptureEnPassant(chessPiece, position);
    }

    @Override
    public boolean checkmate(Color color) {
        return innerState.checkmate(color);
    }

    @Override
    public boolean checkmate() {
        return (innerState.checkmate(Color.WHITE)) || (innerState.checkmate(Color.BLACK));
    }

    @Override
    public boolean stalemate(Color color) {
        return innerState.stalemate(color);
    }

    @Override
    public boolean stalemate() {
        return (innerState.stalemate(Color.WHITE)) || (innerState.stalemate(Color.BLACK));
    }

    public boolean check(Color color)
    {
        return innerState.check(color);
    }

    /**
     * Creates a copy of <b>this</b> <code>State</code> and updates it using the specified <code>action</code>.
     * @param action the update to apply.
     * @return the new <code>State</code>.
     */
    @Override
    public State update(Action action)
    {
        State state = copyOf(this);
        state.innerState.update(action);
        state.action = action;
        return state;
    }

    public State copyAndUpdate(Action action)
    {
        return copyOf(this).update(action);
    }

    public static State copyOf(final State state)
    {
        return new State
                (
                        new HashMap<>(state.squares),
                        new HashMap<>(state.hasMoved),
                        state.origin
                );
    }

    @Override
    public boolean insufficientMaterial()
    {
        return false;
    }

    /**
     * Inner class is responsible for direct access of [Position, ChessPiece] entries.
     */
    private final class InnerState
    {
        public void update(Action action)
        {
            ChessPiece chessPiece = squares.remove(action.getStartPosition());
            squares.put(action.getEndPosition(), chessPiece);
            hasMoved.replace(action.getStartPosition(), true);

            if ((chessPiece instanceof King) && (King.CASTLE_ACTIONS.contains(action)))
            {
                Action response = Rook.CASTLE_RESPONSE.get(action);
                ChessPiece rook = squares.remove(response.getStartPosition());
                squares.put(response.getEndPosition(), rook);
                hasMoved.replace(response.getStartPosition(), true);
            }
        }

        public Position getPosition(ChessPiece chessPiece)
        {
            return squares.entrySet().stream().filter(kv -> kv.getValue().equals(chessPiece))
                    .findFirst().orElseThrow().getKey();
        }

        public boolean isEmpty(Position position) {
            return !squares.containsKey(position);
        }

        public boolean isEmpty(Position... positions)
        {
            for (Position position : positions) {
                if (!isEmpty(position)) {
                    return false;
                }
            }
            return true;
        }

        public boolean hasEnemy(ChessPiece chessPiece, Position position)
        {
            return squares.containsKey(position) && squares.get(position).getColor() != chessPiece.getColor();
        }

        public boolean hasNotMoved(Position... initialPositions)
        {
            for (Position position : initialPositions) {
                if (hasMoved.containsKey(position) && hasMoved.get(position).equals(true)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isNotAttacked(Color color, Position... positions)
        {
            for (Position position : positions) {
                if (isAttacked(color, position)) {
                    return false;
                }
            }
            return true;
        }

        public boolean isAttacked(Color color, Position... positions)
        {
            for (Position position : positions) {
                if (isAttacked(color, position)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isAttacked(Color color, Position position)
        {
            return getOpponent(color).stream().anyMatch(c -> c.moves(State.this)
                    .contains(position));
        }

        public boolean stalemate(Color color)
        {
            return (!check(Color.invert(color))) &&
                    (getFriendly(color).stream().allMatch(c -> c.legalMoves(State.this).isEmpty()));
        }

        public boolean checkmate(Color color)
        {
            return (check(color)) &&
                    (getOpponent(color).stream().allMatch(c -> c.legalMoves(State.this).isEmpty()));
        }

        public boolean check(Color color)
        {
            ChessPiece opposition = getOpponent(color).stream().filter(c -> c instanceof King)
                    .findFirst().orElseThrow();
            Position position = getPosition(opposition);

            return getFriendly(color).stream().anyMatch(c -> c.moves(State.this)
                    .contains(position));
        }

        public long getNumberOfLegalMoves(Color color)
        {
            return getFriendly(color).stream()
                    .mapToInt(value -> value.legalMoves(State.this).size())
                    .sum();
        }

        public boolean canCaptureEnPassant(ChessPiece chessPiece, Position position)
        {
            if ((chessPiece instanceof Pawn) &&
                    (origin.getStateDescriptions().contains(StateDescription.PAWN_TWO_STEP_MOVE)))
            {
                Action last = origin.getAction();

                if (Pawn.INIT_POSITIONS_WHITE.contains(last.getStartPosition()))
                {
                    Position passed = new Position(last.getStartPosition().getX(), 3);
                    return position.equals(passed) &&
                            getPosition(chessPiece).getY() == last.getEndPosition().getY();
                }
                else if (Pawn.INIT_POSITIONS_BLACK.contains(last.getStartPosition()))
                {
                    Position passed = new Position(last.getStartPosition().getX(), 6);
                    return position.equals(passed) &&
                            getPosition(chessPiece).getY() == last.getEndPosition().getY();
                }
            }

            return false;
        }

        public boolean canCastleKingSide(ChessPiece chessPiece)
        {
            if (!(chessPiece instanceof King)) {
                return false;
            }

            if (chessPiece.getColor() == Color.WHITE)
            {
                Position[] init
                        = { King.INIT_POSITION_WHITE, Rook.INIT_KS_POSITION_WHITE };

                Position[] positions
                        = { Rook.CASTLE_KS_POSITION_WHITE, King.CASTLE_KS_POSITION_WHITE };

                return (hasNotMoved(init)) && (isEmpty(positions)) && (isNotAttacked(Color.WHITE, positions)) &&
                        (!check(Color.BLACK)) && (!copyAndUpdate(King.CASTLE_KS_ACTION_WHITE).check(Color.BLACK));
            }
            else
            {
                Position[] init
                        = { King.INIT_POSITION_BLACK, Rook.INIT_KS_POSITION_BLACK };

                Position[] positions
                        = { Rook.CASTLE_KS_POSITION_BLACK, King.CASTLE_KS_POSITION_BLACK };

                return (hasNotMoved(init)) && (isEmpty(positions)) && (isNotAttacked(Color.BLACK, positions)) &&
                        (!check(Color.WHITE)) && (!copyAndUpdate(King.CASTLE_KS_ACTION_BLACK).check(Color.WHITE));
            }
        }

        public boolean canCastleQueenSide(ChessPiece chessPiece)
        {
            if (!(chessPiece instanceof King)) {
                return false;
            }

            if (chessPiece.getColor() == Color.WHITE)
            {
                Position[] init
                        = { King.INIT_POSITION_WHITE, Rook.INIT_QS_POSITION_WHITE };
                Position[] positions
                        = { Rook.CASTLE_QS_POSITION_WHITE, King.CASTLE_QS_POSITION_WHITE, new Position('B', 1) };

                return (hasNotMoved(init)) && (isEmpty(positions)) && (isNotAttacked(Color.WHITE, positions)) &&
                        (!check(Color.BLACK)) && (!copyAndUpdate(King.CASTLE_QS_ACTION_WHITE).check(Color.BLACK));
            }
            else
            {
                Position[] init
                        = { King.INIT_POSITION_BLACK, Rook.INIT_QS_POSITION_BLACK };
                Position[] positions
                        = { Rook.CASTLE_QS_POSITION_BLACK, King.CASTLE_QS_POSITION_BLACK, new Position('B', 8) };

                return (hasNotMoved(init)) && (isEmpty(positions)) && (isNotAttacked(Color.BLACK, positions)) &&
                        (!check(Color.WHITE)) && (!copyAndUpdate(King.CASTLE_QS_ACTION_BLACK).check(Color.WHITE));
            }
        }

        private List<ChessPiece> getFriendly(Color color)
        {
            return squares.values().stream().filter(c -> c.getColor() == color)
                    .collect(Collectors.toList());
        }

        private List<ChessPiece> getOpponent(Color color)
        {
            return squares.values().stream().filter(c -> c.getColor() != color)
                    .collect(Collectors.toList());
        }
    }
}
