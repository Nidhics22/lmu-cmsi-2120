package homework2;

import java.util.NoSuchElementException;

public class ExpandableArrayQueue implements Queue{

    private static final int initialCapacity = 16;
    private int currentCapacity;
    private Object[] q;
    private int last;

    public ExpandableArrayQueue() {
        last = 0;
        currentCapacity = initialCapacity;
        q = new Object[initialCapacity];
    }

    private void increaseCapacity() {
        currentCapacity = 2 * currentCapacity;
        Object[] largeQ = new Object[currentCapacity];
        System.arraycopy(q, 0, largeQ, 0, q.length);
        q = largeQ;
    }

    private void decreaseCapacity() {
        currentCapacity = currentCapacity/2;
        Object[] smallQ = new Object[currentCapacity];
        System.arraycopy(q, 0, smallQ, 0, smallQ.length);
        q = smallQ;
    }

    @Override
    public void enqueue(Object value) {
        if(last == currentCapacity) {
            increaseCapacity();
        }
        q[last] = value;
        last++;
    }

    @Override
    public Object dequeue() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Object dequeuedElement = q[0];
        last--;
        System.arraycopy(q, 1, q, 0, q.length-1);
        q[last] = null;

        if(currentCapacity > initialCapacity && last < currentCapacity/4) {
            decreaseCapacity();
        }
        return dequeuedElement;
    }

    @Override
    public Object peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return q[0];
    }

    @Override
    public int size() {
        return last;
    }

    public int capacity() {
        return currentCapacity;
    }
}

