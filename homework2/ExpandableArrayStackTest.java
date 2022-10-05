package homework2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpandableArrayStackTest extends StackTest {

    @BeforeEach
    public void makeExpandableArrayStack() {
        s = new ExpandableArrayStack();
    }

    @Test
    public void testPushToFullStackGrowsTheStack() {
        int initialCapacity = ((ExpandableArrayStack) s).capacity();
        for (var i = 0; i < initialCapacity; i++) {
            assertEquals(initialCapacity, ((ExpandableArrayStack) s).capacity());
            s.push("abc");
        }
        assertEquals(s.size(), ((ExpandableArrayStack) s).capacity());
        s.push(Math.PI);
        assertEquals(s.size(), initialCapacity + 1);
        assertEquals(initialCapacity * 2, ((ExpandableArrayStack) s).capacity());
        while (s.size() < initialCapacity * 2) {
            assertEquals(initialCapacity * 2, ((ExpandableArrayStack) s).capacity());
            s.push("abc");
        }
        assertEquals(initialCapacity * 2, ((ExpandableArrayStack) s).capacity());
        s.push("abc");
        assertEquals(initialCapacity * 4, ((ExpandableArrayStack) s).capacity());
    }

    @Test
    public void testStackGetsSmallerWhenTooSparse() {
        int initialCapacity = ((ExpandableArrayStack) s).capacity();
        for (var i = 0; i < initialCapacity * 4; i++) {
            s.push("abc");
        }
        s.push("abc");
        assertEquals(initialCapacity * 4 + 1, s.size());
        assertEquals(initialCapacity * 8, ((ExpandableArrayStack) s).capacity());
        s.pop();
        assertEquals(initialCapacity * 4, s.size());
        assertEquals(initialCapacity * 8, ((ExpandableArrayStack) s).capacity());
        for (var i = 0; i < initialCapacity * 2; i++) {
            s.pop();
        }
        assertEquals(initialCapacity * 2, s.size());
        assertEquals(initialCapacity * 8, ((ExpandableArrayStack) s).capacity());
        s.pop();
        assertEquals(initialCapacity * 2 - 1, s.size());
        assertEquals(initialCapacity * 4, ((ExpandableArrayStack) s).capacity());
    }

    @Test
    public void testStackDoesNotShrinkWhenCapacityAtMinium() {
        int initialCapacity = ((ExpandableArrayStack) s).capacity();
        for (var i = 0; i < initialCapacity; i++) {
            s.push("abc");
            assertEquals(initialCapacity, ((ExpandableArrayStack) s).capacity());
        }
        for (var i = 0; i < initialCapacity; i++) {
            s.pop();
            assertEquals(initialCapacity, ((ExpandableArrayStack) s).capacity());
        }
    }
}
