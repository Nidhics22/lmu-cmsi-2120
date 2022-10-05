package homework2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

/**
 * Base test for any class that implements the Queue interface.
 */
public abstract class QueueTest {

    /**
     * The queue to use in all the tests: set this in subclasses.
     */
    protected Queue q;

    @Test
    public void testNewQueueIsEmpty() {
        assertTrue(q.isEmpty());
        assertEquals(q.size(), 0);
    }

    @Test
    public void testInsertsToEmptyQueue() {
        int numberOfInserts = 8;
        for (var i = 0; i < numberOfInserts; i++) {
            q.enqueue("zzz");
        }
        assertTrue(!q.isEmpty());
        assertEquals(q.size(), numberOfInserts);
    }

    @Test
    public void testEnqueueThenDequeue() {
        var message = "hello";
        q.enqueue(message);
        assertEquals(q.dequeue(), message);
    }

    @Test
    public void testEnqueueThenPeek() {
        var message = "hello";
        q.enqueue(message);
        var size = q.size();
        assertEquals(q.peek(), message);
        assertEquals(q.size(), size);
    }

    @Test
    public void testFiftyInThenFiftyOut() {
        for (int i = 0; i < 50; i++) {
            q.enqueue(i);
        }
        for (int i = 0; i < 50; i++) {
            assertEquals(((Integer) q.dequeue()).intValue(), i);
        }
    }

    @Test
    public void testRemovingDownToEmpty() {
        int numberOfRemoves = (int) (Math.random() * 20 + 1);
        for (int i = 0; i < numberOfRemoves; i++) {
            q.enqueue("zzz");
        }
        for (int i = 0; i < numberOfRemoves; i++) {
            q.dequeue();
        }
        assertTrue(q.isEmpty());
        assertEquals(q.size(), 0);
    }

    @Test
    public void testRemoveFromEmptyQueueThrows() {
        assertTrue(q.isEmpty());
        assertThrows(NoSuchElementException.class, () -> q.dequeue());
    }

    @Test
    public void testPeekIntoEmptyQueueThrows() {
        assertTrue(q.isEmpty());
        assertThrows(NoSuchElementException.class, () -> q.peek());
    }

}
