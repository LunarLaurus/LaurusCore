package net.laurus.network;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SubnetMaskTest {

    @Test
    void testValidSubnetMask() {
        SubnetMask mask = new SubnetMask("255.255.255.0");

        assertArrayEquals(new int[]{255, 255, 255, 0}, mask.getOctets());
        assertEquals(24, mask.toPrefixLength());
    }

    @Test
    void testInvalidSubnetMask() {
        assertThrows(IllegalArgumentException.class, () -> new SubnetMask("255.255.255.256"));
        assertThrows(IllegalArgumentException.class, () -> new SubnetMask("InvalidMask"));
    }

    @Test
    void testToUnsignedInteger() {
        SubnetMask mask = new SubnetMask("255.255.255.0");

        assertEquals(0xFFFFFF00L, mask.toUnsignedInteger());
    }

    @Test
    void testFromUnsignedInteger() {
        SubnetMask mask = SubnetMask.fromUnsignedInteger(0xFFFFFF00L);

        assertEquals("255.255.255.0", mask.toString());
    }

    @Test
    void testFromPrefixLength() {
        SubnetMask mask = SubnetMask.fromPrefixLength(24);

        assertEquals("255.255.255.0", mask.toString());
    }

    @Test
    void testIsValidSubnetMask() {
        assertTrue(SubnetMask.isValidSubnetMask("255.255.255.0"));
        assertFalse(SubnetMask.isValidSubnetMask("255.255.0.255"));
    }
}
