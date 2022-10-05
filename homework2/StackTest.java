package homework2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

/**
 * Base test for any class that implements the Stack interface.
 */
public abstract class StackTest {

    /**
     * The stack to use in all the tests: set this in subclasses.
     */
    protected Stack s;

    @Test
    public void testNewStackIsEmpty() {
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void testPushesToEmptyStack() {
        int numberOfPushes = 8;
        for (var i = 0; i < numberOfPushes; i++) {
            s.push("zzz");
        }
        assertFalse(s.isEmpty());
        assertEquals(numberOfPushes, s.size());
    }

    @Test
    public void testPushThenPop() {
        var message = "hello";
        s.push(message);
        assertEquals(message, s.pop());
    }

    @Test
    public void testPushThenPeek() {
        var message = "hello";
        s.push(message);
        var size = s.size();
        assertEquals(message, s.peek());
        assertEquals(size, s.size());
    }

    @Test
    public void testPoppingDownToEmpty() {
        int numberOfPushes = 8;
        for (var i = 0; i < numberOfPushes; i++) {
            s.push("zzz");
        }
        for (var i = 0; i < numberOfPushes; i++) {
            s.pop();
        }
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
    }

    @Test
    public void testPopOnEmptyStackThrows() {
        assertTrue(s.isEmpty());
        assertThrows(NoSuchElementException.class, () -> s.pop());
    }

    @Test
    public void testPeekIntoEmptyStackThrows() {
        assertTrue(s.isEmpty());
        assertThrows(NoSuchElementException.class, () -> s.peek());
    }
}
