package homework2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpandableArrayQueueTest extends QueueTest {

    @BeforeEach
    public void makeExpandableArrayQueue() {
        q = new ExpandableArrayQueue();
    }

    @Test
    public void testenqueueToFullQueueGrowsTheQueue() {
        int initialCapacity = ((ExpandableArrayQueue) q).capacity();
        for (var i = 0; i < initialCapacity; i++) {
            assertEquals(initialCapacity, ((ExpandableArrayQueue) q).capacity());
            q.enqueue("abc");
        }
        assertEquals(q.size(), ((ExpandableArrayQueue) q).capacity());
        q.enqueue(Math.PI);
        assertEquals(q.size(), initialCapacity + 1);
        assertEquals(initialCapacity * 2, ((ExpandableArrayQueue) q).capacity());
        while (q.size() < initialCapacity * 2) {
            assertEquals(initialCapacity * 2, ((ExpandableArrayQueue) q).capacity());
            q.enqueue("abc");
        }
        assertEquals(initialCapacity * 2, ((ExpandableArrayQueue) q).capacity());
        q.enqueue("abc");
        assertEquals(initialCapacity * 4, ((ExpandableArrayQueue) q).capacity());
    }

    @Test
    public void testQueueGetsSmallerWhenTooSparse() {
        int initialCapacity = ((ExpandableArrayQueue) q).capacity();
        for (var i = 0; i < initialCapacity * 4; i++) {
            q.enqueue("abc");
        }
        q.enqueue("abc");
        assertEquals(initialCapacity * 4 + 1, q.size());
        assertEquals(initialCapacity * 8, ((ExpandableArrayQueue) q).capacity());
        q.dequeue();
        assertEquals(initialCapacity * 4, q.size());
        assertEquals(initialCapacity * 8, ((ExpandableArrayQueue) q).capacity());
        for (var i = 0; i < initialCapacity * 2; i++) {
            q.dequeue();
        }
        assertEquals(initialCapacity * 2, q.size());
        assertEquals(initialCapacity * 8, ((ExpandableArrayQueue) q).capacity());
        q.dequeue();
        assertEquals(initialCapacity * 2 - 1, q.size());
        assertEquals(initialCapacity * 4, ((ExpandableArrayQueue) q).capacity());
    }

    @Test
    public void testQueueDoesNotShrinkWhenCapacityAtMinium() {
        int initialCapacity = ((ExpandableArrayQueue) q).capacity();
        for (var i = 0; i < initialCapacity; i++) {
            q.enqueue("abc");
            assertEquals(initialCapacity, ((ExpandableArrayQueue) q).capacity());
        }
        for (var i = 0; i < initialCapacity; i++) {
            q.dequeue();
            assertEquals(initialCapacity, ((ExpandableArrayQueue) q).capacity());
        }
    }
}