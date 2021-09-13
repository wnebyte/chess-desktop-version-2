package struct;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class represents a LIFO Stack/Queue.
 * @param <E>:
 */
public final class LastInFirstOutStack<E> implements Collection<E>
{
    private final ArrayDeque<E> deque = new ArrayDeque<E>();

    public LastInFirstOutStack() { }

    @SafeVarargs
    public LastInFirstOutStack(E... varargs) {
        if (varargs != null) {
            for (E e : varargs) {
                push(e);
            }
        }
    }

    public void push(E e) {
        deque.addLast(e);
    }

    public E pop() {
        return deque.removeLast();
    }

    public E peek() {
        return deque.peekLast();
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