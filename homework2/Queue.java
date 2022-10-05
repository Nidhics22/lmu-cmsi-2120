package homework2;

/**
 * A small queue interface. You can query the size of the queue and ask whether
 * it is empty, add and remove items, and peek at the front item.
 */
public interface Queue {

    /**
     * Adds the given item to the rear of the queue.
     */
    void enqueue(Object item);

    /**
     * Removes the front item from the queue and returns it.
     *
     * @exception java.util.NoSuchElementException if the queue is empty.
     */
    Object dequeue();

    /**
     * Returns the front item from the queue without popping it.
     *
     * @exception java.util.NoSuchElementException if the queue is empty.
     */
    Object peek();

    /**
     * Returns the number of items currently in the queue.
     */
    int size();

    /**
     * Returns whether the queue is empty or not.
     */
    default boolean isEmpty() {
        return size() == 0;
    }
}