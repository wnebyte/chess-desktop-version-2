package ui.adapter;

import javafx.scene.image.Image;
import model.piece.ChessPiece;
import model.Color;
import ui.builder.SVGFactory;

public class BishopAdapter extends ChessPieceAdapter
{
    private static final String PATH_WHITE = "/drawable/bishop_white.svg";

    private static final String PATH_BLACK = "/drawable/bishop_black.svg";

    public BishopAdapter(ChessPiece chessPiece) {
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
