package ui.adapter;

import javafx.scene.image.Image;
import model.piece.ChessPiece;
import model.Color;
import ui.builder.SVGFactory;

public class KnightAdapter extends ChessPieceAdapter
{
    private static final String PATH_WHITE = "/drawable/knight_white.svg";

    private static final String PATH_BLACK = "/drawable/knight_black.svg";

    public KnightAdapter(ChessPiece chessPiece) {
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
