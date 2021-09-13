package model.ai;

import model.state.QueryableBoardState;

public interface UtilityFunction {

    double get(final QueryableBoardState state);
}
