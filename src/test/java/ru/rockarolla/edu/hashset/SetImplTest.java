package ru.rockarolla.edu.hashset;

import org.junit.Before;
import org.junit.Test;
import ru.rockarolla.edu.hashset.Set;
import ru.rockarolla.edu.hashset.SetImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by davydov on 10-Sep-16.
 */
public class SetImplTest {

    private Set s;

    @Before
    public void setUp() {
        s = new SetImpl();
    }

    @Test
    public void sizeTest() {
        assertEquals(0, s.size());
        s.add("a");
        assertEquals(1, s.size());
        s.add("b");
        assertEquals(2, s.size());
        s.add(44);
        assertEquals(3, s.size());
        s.add("a");
        assertEquals(3, s.size());
    }

    @Test
    public void testContains() {
        assertFalse(s.contains("a"));
        assertTrue(s.add("a"));
        assertTrue(s.contains("a"));
        assertFalse(s.add("a"));
        assertTrue(s.contains("a"));
    }

    @Test
    public void testRemotve() {
        String vals = "abcdefghijklmnopr";
        for (int i = 0; i < vals.length(); i++) {
            s.add(String.valueOf(vals.charAt(i)));
        }

        assertEquals(17, s.size());
        assertTrue(s.remove("d"));
        assertEquals(16, s.size());
        assertTrue(s.remove("a"));
        assertTrue(s.remove("k"));
        assertTrue(s.remove("i"));
        assertEquals(13, s.size());

        assertFalse(s.contains("d"));
        assertFalse(s.contains("a"));
        assertFalse(s.contains("k"));
        assertFalse(s.contains("i"));
        assertTrue(s.contains("b"));

        assertFalse(s.remove("y"));
        assertEquals(13, s.size());
    }
}
