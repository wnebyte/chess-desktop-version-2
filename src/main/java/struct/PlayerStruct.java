package struct;

import model.Color;
import model.player.Player;

import java.util.Collection;
import java.util.Iterator;

public class PlayerStruct {

    private final Player[] array = new Player[2];

    private int index = 0;

    public PlayerStruct(Player pl1, Player pl2) {
        if ((pl1 == null) || (pl2 == null) || (pl1.getColor() == pl2.getColor())) {
            throw new IllegalArgumentException(
                    "neither Player may be null."
            );
        }
        array[0] = (pl1.getColor() == Color.WHITE) ? pl1 : pl2;
        array[1] = (array[0].getColor() == pl1.getColor()) ? pl2 : pl1;
    }

    public final Player peekNext() {
        return array[index];
    }

    public final void update() {
        incrementIndex();
    }

    private int incrementIndex() {
        return (index == 0) ? ++index : --index;
    }

}
