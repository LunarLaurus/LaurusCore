package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GeneralUtilTest {

    @Test
    void testTimeDifference() {
        assertTrue(GeneralUtil.timeDifference(10000L, 0L, 5));
        assertFalse(GeneralUtil.timeDifference(1000L, 0L, 5));
    }

    @Test
    void testCombineAndSort() {
        String[] array1 = {"a", "c"};
        String[] array2 = {"b", "a"};

        String[] result = GeneralUtil.combineAndSort(array1, array2);
        assertArrayEquals(new String[]{"a", "b", "c"}, result);
    }
}
