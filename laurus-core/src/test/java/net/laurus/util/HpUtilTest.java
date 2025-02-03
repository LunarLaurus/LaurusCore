package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HpUtilTest {

    @Test
    void testIsHp() {
        assertTrue(HpUtil.isHp("HP"));
        assertTrue(HpUtil.isHp("hpe"));
        assertFalse(HpUtil.isHp("Dell"));
    }
}
