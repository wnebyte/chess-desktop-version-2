package model.ai;

import model.Action;
import model.Color;
import model.piece.ChessPiece;
import model.player.Player;
import model.state.QueryableBoardState;
import struct.Node;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchSpace {

    private final Node<QueryableBoardState> root;

    private final Player player;

    private final MinMax friendly;

    private final MinMax opposition;

    private final UtilityFunction utilityFunction;

    private final int terminalDepth = 3;

    public SearchSpace(final Node<QueryableBoardState> root, final Player player) {
        this.root = root;
        this.player = player;
        this.friendly = (player.getColor() == Color.WHITE) ? new Max() : new Min();
        this.opposition = (player.getColor() == Color.WHITE) ? new Min() : new Max();
        this.utilityFunction = state -> {
            double utility = 0.0;
            for (ChessPiece chessPiece : state.getChessPieces().values()) {
                if (chessPiece.getColor() == Color.WHITE) {
                    utility += 5; // stand in
                }
                else {
                    utility -= 5; // stand in
                }
            }
            return utility;
        };
    }

    public Action decision() {
        populateTree(root);
        return minmax().getAction();
    }

    private boolean cutoffTest(final Node<QueryableBoardState> node, final int depth) {
        return (terminalDepth <= depth) || (node.getData().checkmate()) || (node.getData().stalemate());
    }

    private void populateTree(final Node<QueryableBoardState> node) {
        int depth = node.getDepth();

        if (cutoffTest(node, depth)) {
            if (depth % 2 == 0) {
                opposition.minmax(node, utilityFunction);
            }
            else {
                friendly.minmax(node, utilityFunction);
            }
            return;
        }

        List<Node<QueryableBoardState>> nodes = (depth % 2 == 0) ?
                friendly.generate(node) :
                opposition.generate(node);

        for (Node<QueryableBoardState> n : nodes) {
            node.addChild(n);
            populateTree(n);
        }
    }

    private QueryableBoardState minmax() {
        for (int depth = terminalDepth; depth >= 0; depth--) {
            for (Node<QueryableBoardState> node : root.nthDepthNodes(depth)) {
                if (depth % 2 == 0) {
                    friendly.minmax(node, utilityFunction);
                }
                else {
                    opposition.minmax(node, utilityFunction);
                }
            }
        }
        Collections.shuffle(root.getChildren());
        if (friendly instanceof Min) {
            return root.getChildren().stream().min(Comparator.comparingDouble(val -> val.getData()
                    .getUtility())).orElseThrow()
                    .getData();
        }
        else {
            return root.getChildren().stream().max(Comparator.comparingDouble(val -> val.getData()
                    .getUtility())).orElseThrow()
                    .getData();
        }
    }
}