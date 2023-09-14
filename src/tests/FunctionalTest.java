package src.tests;

import src.Shortener;

import org.junit.Test;
import src.strategy.*;

import static org.junit.Assert.*;

public class FunctionalTest {
    public void testStorage(Shortener shortener) {
        String testString1 = "Test string 1";
        String testString2 = "Test string 2";
        String testString3 = "Test string 1";

        Long testID1 = shortener.getId(testString1);
        Long testID2 = shortener.getId(testString2);
        Long testID3 = shortener.getId(testString3);

        assertNotEquals(testID1, testID2);
        assertNotEquals(testID3, testID2);
        assertEquals(testID1, testID3);

        assertEquals(testString1, shortener.getString(testID1));
        assertEquals(testString2, shortener.getString(testID2));
        assertEquals(testString3, shortener.getString(testID3));
    }

    @Test
    public void testHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }
}
