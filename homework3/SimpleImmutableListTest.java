package homework3;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SimpleImmutableListTest {

    // A utility to make many of the tests easier
    private String asString(SimpleImmutableList list) {
        var builder = new StringBuilder();
        list.forEach(builder::append);
        return builder.toString();
    }

    @Test
    public void testListIsEmptyOnConstruction() {
        var list = SimpleImmutableList.of();
        assertEquals(0, list.size());
    }

    @Test
    public void testOf() {
        assertEquals("", asString(SimpleImmutableList.of()));
        assertEquals("5", asString(SimpleImmutableList.of(5)));
        assertEquals("58132", asString(SimpleImmutableList.of(5, 8, 13, 2)));
    }

    @Test
    public void testFrom() {
        var list = SimpleImmutableList.from(5, new EmptyList());
        assertEquals(1, list.size());
        assertEquals("5", asString(list));
        list = SimpleImmutableList.from(8, list);
        list = SimpleImmutableList.from(13, list);
        list = SimpleImmutableList.from(2, list);
        assertEquals(4, list.size());
        assertEquals("21385", asString(list));
    }

    @Test
    public void testHeadAndTail() {
        assertThrows(NoSuchElementException.class, () -> new EmptyList().head());
        assertThrows(NoSuchElementException.class, () -> new EmptyList().tail());
        var list = SimpleImmutableList.of(100);
        assertEquals(100, list.head());
        assertTrue(list.tail() instanceof EmptyList);
        assertEquals(0, list.tail().size());
        list = SimpleImmutableList.of(55, 34, 13, 21);
        assertEquals(55, list.head());
        assertEquals(3, list.tail().size());
        assertEquals("341321", asString(list.tail()));
    }

    @Test
    public void testAt() {
        assertThrows(NoSuchElementException.class, () -> new EmptyList().at(1));
        var list = SimpleImmutableList.of(3, 5, 8, 3, 3, 13, 2);
        assertThrows(NoSuchElementException.class, () -> list.at(-1));
        assertThrows(NoSuchElementException.class, () -> list.at(7));
        assertEquals(3, list.at(0));
        assertEquals(5, list.at(1));
        assertEquals(8, list.at(2));
        assertEquals(3, list.at(3));
        assertEquals(13, list.at(5));
        assertEquals(2, list.at(6));
    }

    @Test
    public void testForEach() {
        var list = SimpleImmutableList.of();
        var builder = new StringBuilder();
        list.forEach(builder::append);
        assertEquals("", builder.toString());
        list = SimpleImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.forEach(builder::append);
        assertEquals("12345678910", builder.toString());
    }

    @Test
    public void testDropAndTake() {
        // Take 0 from empty is ok
        assertEquals(0, SimpleImmutableList.of().take(0).size());
        assertEquals(0, SimpleImmutableList.of().drop(0).size());
        // But you cannot take or drop even 1 from empty
        assertThrows(IllegalArgumentException.class, () -> new EmptyList().take(1));
        assertThrows(IllegalArgumentException.class, () -> new EmptyList().drop(1));
        // Do all the takes and drops on non-empties
        var oneThroughFour = SimpleImmutableList.of(1, 2, 3, 4);
        assertThrows(IllegalArgumentException.class, () -> oneThroughFour.take(-1));
        assertEquals("", asString(oneThroughFour.take(0)));
        assertEquals("1", asString(oneThroughFour.take(1)));
        assertEquals("12", asString(oneThroughFour.take(2)));
        assertEquals("123", asString(oneThroughFour.take(3)));
        assertEquals("1234", asString(oneThroughFour.take(4)));
        assertThrows(IllegalArgumentException.class, () -> oneThroughFour.take(5));
        assertThrows(IllegalArgumentException.class, () -> oneThroughFour.drop(-1));
        assertEquals("1234", asString(oneThroughFour.drop(0)));
        assertEquals("234", asString(oneThroughFour.drop(1)));
        assertEquals("34", asString(oneThroughFour.drop(2)));
        assertEquals("4", asString(oneThroughFour.drop(3)));
        assertEquals("", asString(oneThroughFour.drop(4)));
        assertThrows(IllegalArgumentException.class, () -> oneThroughFour.drop(5));
    }

    @Test
    public void testReversed() {
        // Reverse the empty list
        assertEquals("", asString(SimpleImmutableList.of().reversed()));
        // Reverse a one-element list
        assertEquals("1", asString(SimpleImmutableList.of(1).reversed()));
        // Reverse a four-element list
        assertEquals("4321", asString(SimpleImmutableList.of(1, 2, 3, 4).reversed()));
    }

    @Test
    public void testAppend() {
        var empty = SimpleImmutableList.of();
        var twoFive = SimpleImmutableList.of(2, 5);
        var emptyTwoFive = empty.append(twoFive);
        var twoFiveEmpty = twoFive.append(empty);
        var twoFiveTwoFive = twoFive.append(twoFive);
        assertEquals("25", asString(emptyTwoFive));
        assertEquals("25", asString(twoFiveEmpty));
        assertEquals("2525", asString(twoFiveTwoFive));
    }

    @Test
    public void testMap() {
        var list = SimpleImmutableList.of();
        // Map the empty list
        list = list.map(x -> 2 * (Integer) x);
        assertEquals(0, list.size());
        assertEquals("", asString(list));
        // Map a one-element list
        list = SimpleImmutableList.of(1);
        list = list.map(x -> 2 * (Integer) x);
        assertEquals(1, list.size());
        assertEquals("2", asString(list));
        // map a four-element list
        list = SimpleImmutableList.of(2, 2, 3, 4);
        assertEquals(4, list.size());
        list = list.map(x -> 2 * (Integer) x);
        assertEquals("4468", asString(list));
    }

    @Test
    public void testFilter() {
        var list = SimpleImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var threes = list.filter(x -> (Integer) x % 3 == 0);
        assertEquals(3, threes.size());
        assertEquals("369", asString(threes));
        // Filter without leading elements
        var biggerHalf = list.filter(x -> (Integer) x > 5);
        assertEquals(5, biggerHalf.size());
        assertEquals("678910", asString(biggerHalf));
        // Filter is empty
        var negatives = list.filter(x -> (Integer) x < 0);
        assertEquals(0, negatives.size());
        assertEquals("", asString(negatives));
        // Filter on empty has no effect
        var nothing = new EmptyList().filter(x -> true);
        assertEquals(0, nothing.size());
        assertEquals("", asString(nothing));
    }

    @Test
    public void testLast() {
        var list = SimpleImmutableList.of();
        assertThrows(NoSuchElementException.class, list::last);
        list = SimpleImmutableList.from(1, list);
        assertEquals(1, list.last());
        list = SimpleImmutableList.from(2, list);
        list = SimpleImmutableList.from(3, list);
        assertEquals(1, list.last());
    }

    @Test
    public void testEveryAndSome() {
        var list = SimpleImmutableList.of();
        assertTrue(list.every(x -> true));
        assertFalse(list.some(x -> true));
        list = SimpleImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertTrue(list.every(x -> (Integer) x > 0));
        assertFalse(list.every(x -> (Integer) x > 5));
        assertTrue(list.some(x -> (Integer) x > 5));
        assertFalse(list.some(x -> (Integer) x > 10));
    }
}