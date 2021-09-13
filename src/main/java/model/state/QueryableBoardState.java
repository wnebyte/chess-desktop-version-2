package model.state;

import model.Action;
import model.Position;
import model.piece.ChessPiece;
import model.Color;
import model.piece.King;
import model.piece.Pawn;
import java.util.*;

public interface QueryableBoardState extends BasicQueryableBoardState
{
    Map<Position, ChessPiece> getChessPieces();

    Map<Position, Boolean> getHasMoved();

    void setUtility(final double utility);

    double getUtility();

    Action getAction();

    Origin getOrigin();

    boolean canCastle(final ChessPiece chessPiece, final Position position);

    boolean check(final Color color);

    boolean checkmate(final Color color);

    boolean checkmate();

    boolean stalemate(final Color color);

    boolean stalemate();

    QueryableBoardState update(final Action action);

    boolean insufficientMaterial();

    static boolean isPawnPromotion(ChessPiece chessPiece, Position position)
    {
        return ((chessPiece instanceof Pawn) &&
                (Pawn.PROMOTION_POSITIONS.contains(position)));
    }

    static boolean isCastle(ChessPiece chessPiece, Action action)
    {
        return ((chessPiece instanceof King) &&
                (King.CASTLE_ACTIONS.contains(action)));
    }

    static boolean isPawnTwoStepMove(ChessPiece chessPiece, Action action)
    {
        return ((chessPiece instanceof Pawn) &&
                (Pawn.INIT_POSITIONS.contains(action.getStartPosition()) &&
                        Pawn.END_TWO_SQUARE_MOVE_POSITIONS.contains(action.getEndPosition())));
    }

    static boolean isEnPassant(ChessPiece chessPiece, Action action, Origin origin)
    {
        return (chessPiece instanceof Pawn) &&
                (origin.getStateDescriptions().contains(StateDescription.PAWN_TWO_STEP_MOVE)) &&
                (origin.hasPassedPosition()) &&
                (origin.getPassedThroughPosition().equals(action.getEndPosition()));
    }

    static boolean fiftyMovesWithoutPawnMoveOrCapture(Collection<QueryableBoardState> states)
    {
        if (states.size() < 50) {
            return false;
        }

        return states.stream().noneMatch(s ->
                (s.getOrigin().getStateDescriptions().contains(StateDescription.PAWN_MOVE)) ||
                        (s.getOrigin().getStateDescriptions().contains(StateDescription.CAPTURE))
        );
    }

    static boolean threefoldRepetition(Collection<QueryableBoardState> states)
    {
        int size = states.size();

        if (size < 6) {
            return false;
        }

        return new ArrayList<>(states).subList(size - 6, size).stream().distinct().count() == 0;
    }
}