package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionUtils
{
    /**
     * Adds the elements of a & b to the returning <code>List</code>.
     * @param a the first <code>List</code>.
     * @param b the second <code>List</code>.
     * @param <T> the component type of a & b.
     * @return a new <code>List</code> containing the elements of a & b.
     */
    public static <T> List<T> merge(final List<T> a, final List<T> b)
    {
        if ((a == null) || (b == null))
        {
            return null;
        }

        List<T> newList = new ArrayList<T>((a.size() + b.size()));
        newList.addAll(a);
        newList.addAll(b);
        return newList;
    }

    public static <T> Map<T, Boolean> copyOfVal(final Map<T, Boolean> map)
    {
        Map<T, Boolean> newMap = new HashMap<>(map.size());

        map.forEach((t, val) ->
        {
            newMap.put(t, (val == true) ? true : false);
        });

        return newMap;
    }
}
