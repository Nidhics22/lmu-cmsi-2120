package homework6;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;

public class WordFinderTest {



    WordFinder finder = new WordFinder();

    @Test
    public void testAddingEmptyWordsThrows() {
        assertThrows(IllegalArgumentException.class, () -> finder.add(""));
        assertThrows(IllegalArgumentException.class, () -> finder.add("   "));
    }

    @Test
    public void testNewFinderIsEmpty() {
        assertFalse(finder.contains("dog"));
        assertEquals(List.of(), finder.allWords());
        assertThrows(IllegalArgumentException.class, () -> finder.suggestions(""));
        assertEquals(List.of(), finder.suggestions("i"));
        assertEquals(List.of(), finder.suggestions("dog"));
    }

    @Test
    public void testSingleWordFinder() {
        finder.add("dog");
        assertTrue(finder.contains("dog"));
        assertTrue(finder.contains("  dog"));
        assertTrue(finder.contains("dog "));
        assertTrue(finder.contains("  dog   "));
        assertFalse(finder.contains("cat"));
        assertEquals(List.of("dog"), finder.allWords());
        assertEquals(List.of(), finder.suggestions("i"));
        assertThrows(IllegalArgumentException.class, () -> finder.suggestions(""));
        assertEquals(List.of("dog"), finder.suggestions("d"));
        assertEquals(List.of("dog"), finder.suggestions("do"));
        assertEquals(List.of("dog"), finder.suggestions("dog"));
        assertEquals(List.of("dog"), finder.suggestions("dog   "));
    }

    @Test
    public void testMultiwordFinder() {
        finder.add("dog");
        finder.add("role");
        finder.add("  door  ");
        finder.add("rat ");
        finder.add("dishes");
        finder.add("  roller");
        assertTrue(finder.contains("rat"));
        assertTrue(finder.contains("roller "));
        assertThrows(IllegalArgumentException.class, () -> finder.suggestions(""));
        assertEquals(List.of("dishes", "dog", "door", "rat", "role", "roller"), finder.allWords());
        assertEquals(List.of("dishes", "dog", "door"), finder.suggestions("d"));
        assertEquals(List.of("dog", "door"), finder.suggestions("do"));
        assertEquals(List.of("role", "roller"), finder.suggestions("ro"));
        assertEquals(List.of("role", "roller"), finder.suggestions("rol  "));
        assertEquals(List.of("roller"), finder.suggestions("roll"));
        assertEquals(List.of("role"), finder.suggestions("role"));
        assertEquals(List.of(), finder.suggestions("rolex"));
        assertEquals(List.of(), finder.suggestions("parsimonious"));
    }
    
}
