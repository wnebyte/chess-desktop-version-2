package ui.adapter;

import model.piece.ChessPiece;
import model.Color;
import ui.builder.SVGFactory;
import javafx.scene.image.Image;

public class KingAdapter extends ChessPieceAdapter
{
    private static final String PATH_WHITE = "/drawable/king_white.svg";

    private static final String PATH_BLACK = "/drawable/king_black.svg";

    public KingAdapter(ChessPiece chessPiece) {
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
