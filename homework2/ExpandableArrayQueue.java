package homework2;

import java.util.NoSuchElementException;

public class ExpandableArrayQueue implements Queue {

    private static final int initialCapacity = 16;
    private int currentCapacity;
    private Object[] queue;
    private int last;

    public ExpandableArrayQueue() {
        last = 0;
        currentCapacity = initialCapacity;
        queue = new Object[initialCapacity];
    }

    private void increaseCapacity() {
        currentCapacity = 2 * currentCapacity;
        Object[] largeQueue = new Object[currentCapacity];
        System.arraycopy(queue, 0, largeQueue, 0, queue.length);
        queue = largeQueue;
    }

    private void decreaseCapacity() {
        currentCapacity = currentCapacity/2;
        Object[] smallQueue = new Object[currentCapacity];
        System.arraycopy(queue, 0, smallQueue, 0, smallQueue.length);
        queue = smallQueue;
    }

    @Override
    public void enqueue(Object value) {
        if(last == currentCapacity) {
            increaseCapacity();
        }
        queue[last] = value;
        last++;
    }

    @Override
    public Object dequeue() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        Object dequeuedElement = queue[0];
        last--;
        System.arraycopy(queue, 1, queue, 0, queue.length-1);
        queue[last] = null;

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
        return queue[0];
    }

    @Override
    public int size() {
        return last;
    }

    public int capacity() {
        return currentCapacity;
    }
}

