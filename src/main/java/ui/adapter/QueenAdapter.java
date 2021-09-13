package ui.adapter;

import model.Color;
import model.piece.ChessPiece;
import ui.builder.SVGFactory;
import javafx.scene.image.Image;

public class QueenAdapter extends ChessPieceAdapter
{
    private static final String PATH_WHITE = "/drawable/queen_white.svg";

    private static final String PATH_BLACK = "/drawable/queen_black.svg";

    public QueenAdapter(ChessPiece chessPiece) {
        super(chessPiece);
    }

    @Override
    protected Image createViewHolder() {
        return new SVGFactory(getPath()).load();
    }

    private String getPath() {
        return getChessPiece().getColor() == Color.WHITE ?
                PATH_WHITE :
                PATH_BLACK;
    }
}
