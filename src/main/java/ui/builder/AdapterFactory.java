package ui.builder;

import model.Color;
import model.Position;
import model.piece.*;
import ui.adapter.*;

import java.util.HashMap;
import java.util.Map;


public class AdapterFactory
{
    private static final Map<Position, ChessPieceAdapter> initial = new HashMap<>(32) {
        {
            put(new Position('A', 1), new RookAdapter(new Rook(Color.WHITE)));
            put(new Position('B', 1), new KnightAdapter(new Knight(Color.WHITE)));
            put(new Position('C', 1), new BishopAdapter(new Bishop(Color.WHITE)));
            put(new Position('D', 1), new QueenAdapter(new Queen(Color.WHITE)));
            put(new Position('E', 1), new KingAdapter(new King(Color.WHITE)));
            put(new Position('F', 1), new BishopAdapter(new Bishop(Color.WHITE)));
            put(new Position('G', 1), new KnightAdapter(new Knight(Color.WHITE)));
            put(new Position('H', 1), new RookAdapter(new Rook(Color.WHITE)));

            put(new Position('A' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('B' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('C' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('D' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('E' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('F' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('G' ,2), new PawnAdapter(new Pawn(Color.WHITE)));
            put(new Position('H' ,2), new PawnAdapter(new Pawn(Color.WHITE)));

            put(new Position('A', 8), new RookAdapter(new Rook(Color.BLACK)));
            put(new Position('B', 8), new KnightAdapter(new Knight(Color.BLACK)));
            put(new Position('C', 8), new BishopAdapter(new Bishop(Color.BLACK)));
            put(new Position('D', 8), new QueenAdapter(new Queen(Color.BLACK)));
            put(new Position('E', 8), new KingAdapter(new King(Color.BLACK)));
            put(new Position('F', 8), new BishopAdapter(new Bishop(Color.BLACK)));
            put(new Position('G', 8), new KnightAdapter(new Knight(Color.BLACK)));
            put(new Position('H', 8), new RookAdapter(new Rook(Color.BLACK)));

            put(new Position('A' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('B' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('C' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('D' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('E' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('F' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('G' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
            put(new Position('H' ,7), new PawnAdapter(new Pawn(Color.BLACK)));
        }
    };

    public static ChessPieceAdapter newAdapter(Position position)
    {
       return initial.getOrDefault(position, null);
    }

    public static ChessPieceAdapter newAdapter(ChessPiece chessPiece)
    {
        switch (chessPiece.getClass().getSimpleName())
        {
            case "Bishop":
                return new BishopAdapter(chessPiece);
            case "King":
                return new KingAdapter(chessPiece);
            case "Knight":
                return new KnightAdapter(chessPiece);
            case "Pawn":
                return new PawnAdapter(chessPiece);
            case "Queen":
                return new QueenAdapter(chessPiece);
            case "Rook":
                return new RookAdapter(chessPiece);
            default:
                return null;
        }
    }
}
