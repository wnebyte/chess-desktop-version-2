package model.ai;

import model.state.QueryableBoardState;
import struct.Node;
import java.util.List;

public interface MinMax {

    List<Node<QueryableBoardState>> generate(final Node<QueryableBoardState> node);

    void minmax(final Node<QueryableBoardState> node, final UtilityFunction utilityFunction);
}
