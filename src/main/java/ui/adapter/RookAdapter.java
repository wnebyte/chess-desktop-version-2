package ui.adapter;

import model.Color;
import model.piece.ChessPiece;
import ui.builder.SVGFactory;
import javafx.scene.image.Image;

public class RookAdapter extends ChessPieceAdapter
{
    private final static String PATH_WHITE = "/drawable/rook_white.svg";

    private final static String PATH_BLACK = "/drawable/rook_black.svg";

    public RookAdapter(ChessPiece chessPiece) {
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
