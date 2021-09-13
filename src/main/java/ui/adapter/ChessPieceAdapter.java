package ui.adapter;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.piece.ChessPiece;

public abstract class ChessPieceAdapter extends ImageView
{
    private final ChessPiece chessPiece;

    protected ChessPieceAdapter(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
        super.setImage(createViewHolder());
        super.setSmooth(true);
        super.setPreserveRatio(true);
        super.setFitHeight(50);
        super.setFitWidth(50);
    }

    protected abstract Image createViewHolder();

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof ChessPieceAdapter)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        ChessPieceAdapter adapter = (ChessPieceAdapter) o;
        return adapter.getChessPiece().equals(this.getChessPiece());
    }

    @Override
    public int hashCode()
    {
        return 55 + this.getChessPiece().hashCode();
    }

}
