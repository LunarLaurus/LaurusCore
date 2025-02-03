package net.laurus.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class BitmapTest {

    @Test
    void testBitmapInitialization() {
        Bitmap bitmap = new Bitmap(10);

        assertEquals(10, bitmap.getSize());
        assertEquals("0000000000", bitmap.toString());
    }

    @Test
    void testSetAndGetBit() {
        Bitmap bitmap = new Bitmap(10);
        bitmap.setBit(3);

        assertTrue(bitmap.getBit(3));
        assertEquals("0001000000", bitmap.toString());
    }

    @Test
    void testClearBit() {
        Bitmap bitmap = new Bitmap(10);
        bitmap.setBit(3);
        bitmap.clearBit(3);

        assertFalse(bitmap.getBit(3));
        assertEquals("0000000000", bitmap.toString());
    }

    @Test
    void testFlipBit() {
        Bitmap bitmap = new Bitmap(10);
        bitmap.flipBit(3);

        assertTrue(bitmap.getBit(3));
        assertEquals("0001000000", bitmap.toString());

        bitmap.flipBit(3);

        assertFalse(bitmap.getBit(3));
        assertEquals("0000000000", bitmap.toString());
    }

    @Test
    void testOutOfBoundsAccess() {
        Bitmap bitmap = new Bitmap(10);

        assertThrows(IndexOutOfBoundsException.class, () -> bitmap.getBit(10));
        assertThrows(IndexOutOfBoundsException.class, () -> bitmap.setBit(10));
    }

    @Test
    void testActiveIndexes() {
        Bitmap bitmap = new Bitmap(10);
        bitmap.setBit(2);
        bitmap.setBit(5);

        List<Integer> activeIndexes = bitmap.getActiveIndexes();

        assertEquals(List.of(2, 5), activeIndexes);
    }

    @Test
    void testClearAll() {
        Bitmap bitmap = new Bitmap(10);
        bitmap.setBit(3);
        bitmap.setBit(7);
        bitmap.clearAll();

        assertEquals("0000000000", bitmap.toString());
    }
}
