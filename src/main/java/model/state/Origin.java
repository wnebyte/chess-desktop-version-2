package model.state;

import model.Action;
import model.Position;
import model.piece.ChessPiece;
import model.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Origin
{
    private final Action action;

    private final Color creator;

    private final ChessPiece capturedChessPiece;

    private final List<StateDescription> stateDescriptions;

    private final Position passedThroughPosition;

    public static Origin init()
    {
        return new Origin(null, null, null, null,
                Collections.singletonList(StateDescription.INIT)
        );
    }

    public Origin(final Action action,
                  final Color creator,
                  final ChessPiece capturedChessPiece,
                  final Position passedThroughPosition,
                  final List<StateDescription> stateDescriptions)
    {
        this.action = action;
        this.creator = creator;
        this.stateDescriptions = stateDescriptions;
        this.capturedChessPiece = capturedChessPiece;
        this.passedThroughPosition = passedThroughPosition;
    }

    /**
     * @return the <code>Action</code> associated with this <code>ModificationResult</code>.
     */
    public Action getAction() {
        return action;
    }

    /**
     * @return true if the <code>ChessPiece</code> associated with this <code>ModificationResult</code>
     * is not <code>null</code>.
     */
    public boolean hasCapturedChessPiece() {
        return capturedChessPiece != null;
    }

    /**
     * @return the <code>ChessPiece</code> associated with this <code>ModificationResult</code>.
     */
    public ChessPiece getCapturedChessPiece() {
        return capturedChessPiece;
    }

    /**
     * @return the {@link StateDescription} associated with this <code>ModificationResult</code>.
     */
    public List<StateDescription> getStateDescriptions() {
        return stateDescriptions;
    }

    public boolean hasPassedPosition() {
        return passedThroughPosition != null;
    }

    public Position getPassedThroughPosition()
    {
        return passedThroughPosition;
    }

    public Color getCreator() {
        return creator;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Origin)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Origin origin = (Origin) o;

        return Objects.requireNonNullElse(this.action, false)
                .equals(Objects.requireNonNullElse(origin.action, false)) &&
                Objects.requireNonNullElse(this.creator, false)
                        .equals(Objects.requireNonNullElse(origin.creator, false)) &&
                Objects.requireNonNullElse(this.capturedChessPiece, false)
                        .equals(Objects.requireNonNullElse(origin.capturedChessPiece, false)) &&
                Objects.requireNonNullElse(this.passedThroughPosition, false)
                        .equals(Objects.requireNonNullElse(origin.passedThroughPosition, false)) &&
                this.stateDescriptions.equals(origin.stateDescriptions);
    }

    @Override
    public int hashCode()
    {
        return 15 + action.hashCode() + creator.hashCode() + 2 + capturedChessPiece.hashCode() +
                5 + passedThroughPosition.hashCode() + stateDescriptions.hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("model.state.Metadata[action=%s, color=%s, capturedChessPiece=%s, passedThrough=%s, " +
                "descriptions=%s]",
                action, creator, capturedChessPiece, passedThroughPosition, Arrays.toString(stateDescriptions.toArray()));
    }

}
