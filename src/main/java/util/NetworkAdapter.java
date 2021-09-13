package util;

import model.Action;
import model.Position;

import java.nio.ByteBuffer;

public class NetworkAdapter
{
    public static Action fromBytes(final byte[] bytes)
    {
        if ((bytes == null) || !(bytes.length == 16)) {
            throw new IllegalArgumentException(
                    "bytes must not be null and must be of length 16."
            );
        }

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return new Action(
                new Position(buffer.getInt(), buffer.getInt()),
                new Position(buffer.getInt(), buffer.getInt()));
    }

    public static byte[] toBytes(final Action action)
    {
        if (action == null) {
            throw new IllegalArgumentException(
                    "action must not be null."
            );
        }

        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putInt(action.getStartPosition().getX());
        buffer.putInt(action.getStartPosition().getY());
        buffer.putInt(action.getEndPosition().getX());
        buffer.putInt(action.getEndPosition().getY());
        return buffer.array();
    }
}
