package homework3;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


public class SimpleLinkedListTest {

    // A utility to make many of the tests easier
    private String asString(SimpleLinkedList list) {
        var builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    @Test
    public void testListIsEmptyOnConstruction() {
        var list = new SimpleLinkedList();
        assertEquals(0, list.size());
    }

    @Test
    public void testGetAndIndexOf() {
        var list = new SimpleLinkedList();
        list.addFirst(3);
        list.addFirst(2);
        list.addLast(4);
        list.addLast(5);
        list.addFirst(1);
        assertEquals(5, list.size());
        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));
        assertEquals(0, list.indexOf(1));
        assertEquals(1, list.indexOf(2));
        assertEquals(2, list.indexOf(3));
        assertEquals(3, list.indexOf(4));
        assertEquals(4, list.indexOf(5));
    }

    @Test
    public void testAddAtTheEnds() {
        var list = new SimpleLinkedList();
        list.addFirst(3);
        list.addFirst(2);
        list.addLast(4);
        list.addLast(5);
        list.addFirst(1);
        assertEquals(5, list.size());
        assertEquals("12345", asString(list));
    }

    @Test
    public void testAddAtPosition() {
        var list = new SimpleLinkedList();
        for (var i = 1; i <= 5; i++) {
            list.add(i - 1, i);
        } // 1 2 3 4 5
        list.add(1, 9); // 1 9 2 3 4 5
        list.add(6, 8); // 1 9 2 3 4 5 8
        list.add(3, 7); // 1 9 2 7 3 4 5 8
        assertEquals("19273458", asString(list));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "oops"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(9, "oops"));
    }

    @Test
    public void testSet() {
        var list = new SimpleLinkedList();
        for (var i = 1; i <= 5; i++) {
            list.add(i - 1, i);
        } // 1 2 3 4 5
        for (var i = 1; i <= 5; i++) {
            list.set(i - 1, i * 10);
        } // 10 20 30 40 50
        assertEquals(5, list.size());
        assertEquals("1020304050", asString(list));
    }

    @Test
    public void testRemoves() {
        var list = new SimpleLinkedList();
        for (var i = 1; i <= 10; i++) {
            list.addLast(i);
        }
        list.remove(7); // 1 2 3 4 5 6 7 9 10
        list.remove(3); // 1 2 3 5 6 7 9 10
        list.remove(7); // 1 2 3 5 6 7 9
        list.remove(0); // 2 3 5 6 7 9
        list.remove(1); // 2 5 6 7 9
        assertEquals(5, list.size());
        assertEquals("25679", asString(list));
    }

    @Test
    public void testForEach() {
        var list = new SimpleLinkedList();
        var builder = new StringBuilder();
        list.forEach(builder::append);
        // First make sure it has no effect if empty
        assertEquals("", builder.toString());
        for (var i = 1; i <= 10; i++) {
            list.addLast(i);
        }
        list.forEach(builder::append);
        assertEquals("12345678910", builder.toString());
    }

    @Test
    public void testDropAndTake() {
        var list = new SimpleLinkedList();
        list.take(0);
        assert (list.size() == 0);
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        assertEquals("1234", asString(list));
        assertThrows(IllegalArgumentException.class, () -> list.take(-1));
        assertThrows(IllegalArgumentException.class, () -> list.take(5));
        list.take(2);
        assert (list.size() == 2);
        assertEquals("12", asString(list));
        list.drop(1);
        assert (list.size() == 1);
        assertEquals("2", asString(list));
        list.drop(1);
        assert (list.size() == 0);
        list.take(0);
        assertThrows(IllegalArgumentException.class, () -> list.take(1));
    }

    @Test
    public void testReverse() {
        var list = new SimpleLinkedList();
        // Reverse the empty list
        list.reverse();
        assertEquals(0, list.size());
        assertEquals("", asString(list));
        // Reverse a one-element list
        list.addLast(1);
        list.reverse();
        assertEquals(1, list.size());
        assertEquals("1", asString(list));
        // Reverse a four-element list
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        assertEquals(4, list.size());
        list.reverse();
        assertEquals("4321", asString(list));
    }

    @Test
    public void testAppend() {
        var list = new SimpleLinkedList();
        var other = new SimpleLinkedList();
        // Empty appended with empty
        list.append(other);
        assertEquals(0, list.size());
        other.addLast(2);
        other.addLast(5);
        // Empty appended with nonempty
        list.append(other);
        assertEquals(2, list.size());
        assertEquals("25", asString(list));
        // Do it again to make sure other list did not change
        list.append(other);
        assertEquals(4, list.size());
        assertEquals("2525", asString(list));
        // Append to self
        assertThrows(IllegalArgumentException.class, () -> list.append(list));
        // Append an empty list
        list.append(new SimpleLinkedList());
        assertEquals("2525", asString(list));
    }

    @Test
    public void testMap() {
        var list = new SimpleLinkedList();
        // Map the empty list
        list.map(x -> 2 * (Integer) x);
        assertEquals(0, list.size());
        assertEquals("", asString(list));
        // Map a one-element list
        list.addLast(1);
        list.map(x -> 2 * (Integer) x);
        assertEquals(1, list.size());
        assertEquals("2", asString(list));
        // map a four-element list
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        assertEquals(4, list.size());
        list.map(x -> 2 * (Integer) x);
        assertEquals("4468", asString(list));
    }

    @Test
    public void testFilter() {
        var list = new SimpleLinkedList();
        for (var i = 1; i <= 10; i++) {
            list.addLast(i);
        }
        list.filter(x -> (Integer) x % 5 != 0);
        assertEquals(8, list.size());
        assertEquals("12346789", asString(list));
        list.filter(x -> (Integer) x % 3 != 0);
        assertEquals(5, list.size());
        assertEquals("12478", asString(list));
        // Remove the first element through a filter
        list.filter(x -> (Integer) x != 1);
        assertEquals(4, list.size());
        assertEquals("2478", asString(list));
        // Take it down to one element
        list.filter(x -> (Integer) x >= 8);
        assertEquals(1, list.size());
        assertEquals("8", asString(list));
        // Filter down to empty
        list.filter(x -> false);
        assertEquals(0, list.size());
        assertEquals("", asString(list));
        // Filter on empty has no effect
        list.filter(x -> true);
        assertEquals(0, list.size());
        assertEquals("", asString(list));
    }

    @Test
    public void testOf() {
        var list = SimpleLinkedList.of();
        assertEquals(0, list.size());
        assertEquals("", asString(list));
        list = SimpleLinkedList.of(100);
        assertEquals(1, list.size());
        assertEquals("100", asString(list));
        list = SimpleLinkedList.of(13, 8, 21, 13, 55);
        assertEquals(5, list.size());
        assertEquals("138211355", asString(list));
    }

    @Test
    public void testLast() {
        var list = new SimpleLinkedList();
        assertThrows(NoSuchElementException.class, list::last);
        list.addLast(1);
        assertEquals(1, list.last());
        list.addLast(2);
        list.addLast(3);
        assertEquals(3, list.last());
    }

    @Test
    public void testEveryAndSome() {
        var list = new SimpleLinkedList();
        assertTrue(list.every(x -> true));
        assertFalse(list.some(x -> true));
        for (var i = 1; i <= 10; i++) {
            list.addLast(i);
        }
        assertTrue(list.every(x -> (Integer) x > 0));
        assertFalse(list.every(x -> (Integer) x > 5));
        assertTrue(list.some(x -> (Integer) x > 5));
        assertFalse(list.some(x -> (Integer) x > 10));
    }
}
