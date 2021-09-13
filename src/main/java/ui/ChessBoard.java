package ui;

import model.Action;
import model.state.QueryableBoardState;
import javafx.scene.Node;

public interface ChessBoard
{
    void queue(Square<? super Node> square);

    void deque();

    QueryableBoardState<? super Object, ? super Object> update(Action action);

    void apply(QueryableBoardState<? super Object, ? super Object> state);
}
