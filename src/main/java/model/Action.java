package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Action
{
    @SerializedName("start")
    private final Position startPosition;

    @SerializedName("end")
    private final Position endPosition;

    public Action(Position startPosition, Position endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public Position getEndPosition() {
        return endPosition;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Action)) {
            return false;
        }
        if (o == this) {
            return true;
        }

        Action action = (Action) o;
        return action.getStartPosition().equals(this.getStartPosition()) &&
                action.getEndPosition().equals(this.getEndPosition());
    }

    @Override
    public int hashCode() {
        return 31 + 5 + this.getStartPosition().hashCode() + 3 + this.getEndPosition().hashCode();
    }

    @Override
    public String toString()
    {
        return String.format("start=%s,end=%s", startPosition, endPosition);
    }
}
