package model.ai;

import model.Action;
import model.Color;
import model.Position;
import model.piece.ChessPiece;
import model.state.QueryableBoardState;
import struct.Node;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Max implements MinMax {

    @Override
    public List<Node<QueryableBoardState>> generate(final Node<QueryableBoardState> node) {
        List<Node<QueryableBoardState>> children = new ArrayList<>();

        for (Map.Entry<Position, ChessPiece> kv : node.getData().getChessPieces().entrySet()) {
            if (kv.getValue().getColor() == Color.BLACK) { continue; }

            for (Position position : kv.getValue().legalMoves(node.getData())) {
                children.add(new Node<>(node.getData().update(new Action(kv.getKey(), position))));
            }
        }

        return children;
    }

    @Override
    public void minmax(final Node<QueryableBoardState> node, final UtilityFunction utilityFunction) {
        if (node.isLeaf()) {
            node.getData().setUtility(utilityFunction.get(node.getData()));
        }
        else {
            node.getData().setUtility(
                    node.getChildren().stream().max(Comparator.comparingDouble(val -> val.getData()
                            .getUtility())).orElseThrow()
                            .getData()
                            .getUtility()
            );
        }
    }
}
