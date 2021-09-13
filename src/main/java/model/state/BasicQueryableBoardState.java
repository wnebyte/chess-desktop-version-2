package model.state;

import model.Position;
import model.piece.ChessPiece;

/*
used by the chess piece classes
 */
public interface BasicQueryableBoardState
{
    Position getPosition(final ChessPiece chessPiece);

    boolean isEmpty(final Position... positions);

    boolean isEmpty(final Position position);

    boolean hasEnemy(final ChessPiece chessPiece, final Position position);

    boolean canCaptureEnPassant(final ChessPiece chessPiece, final  Position position);
}