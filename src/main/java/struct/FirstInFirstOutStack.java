package struct;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

/**
 * This class represents a FIFO Stack.
 * @param <E> the Type of this stack's elements.
 */
public final class FirstInFirstOutStack<E> implements Collection<E> {

    private final ArrayDeque<E> deque = new ArrayDeque<>();

    @SafeVarargs
    public FirstInFirstOutStack(E... varargs) {
        if (varargs != null)
        {
            for (E e : varargs)
            {
                push(e);
            }
        }
    }

    public FirstInFirstOutStack(Iterator<E> iterator) {
        if (iterator != null)
        {
            while (iterator.hasNext())
            {
                push(iterator.next());
            }
        }
    }

    public FirstInFirstOutStack(Collection<E> collection) {
        if (collection != null)
        {
            collection.forEach(this::push);
        }
    }

    /**
     * Pushes the specified element to the end of this Stack.
     * @param e the element to be pushed to the end of this Stack.
     */
    public void push(E e) {
        deque.addLast(e);
    }

    public E pop() {
        return deque.pollFirst();
    }

    public E peek() {
        return deque.peekFirst();
    }

    public int size() {
        return deque.size();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return deque.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return deque.iterator();
    }

    @Override
    public Object[] toArray() {
        return deque.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return deque.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return deque.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return deque.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return deque.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return deque.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return deque.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return deque.retainAll(c);
    }

    @Override
    public void clear() {
        deque.clear();
    }
}
