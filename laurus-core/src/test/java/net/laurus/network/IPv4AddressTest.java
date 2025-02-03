package net.laurus.network;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class IPv4AddressTest {

    @Test
    void testValidIPv4Constructor() {
        IPv4Address address = new IPv4Address("192.168.1.1");

        assertArrayEquals(new int[]{192, 168, 1, 1}, address.getOctets());
        assertEquals("192.168.1.1", address.toString());
    }

    @Test
    void testInvalidIPv4Constructor() {
        assertThrows(IllegalArgumentException.class, () -> new IPv4Address("999.999.999.999"));
        assertThrows(IllegalArgumentException.class, () -> new IPv4Address("InvalidIP"));
        assertThrows(IllegalArgumentException.class, () -> new IPv4Address("192.168"));
    }

    @Test
    void testOctetConstructor() {
        IPv4Address address = new IPv4Address(192, 168, 1, 1);

        assertArrayEquals(new int[]{192, 168, 1, 1}, address.getOctets());
    }

    @Test
    void testIsValidIPv4() {
        assertTrue(IPv4Address.isValidIPv4("192.168.1.1"));
        assertFalse(IPv4Address.isValidIPv4("invalid"));
        assertFalse(IPv4Address.isValidIPv4(null));
        assertThrows(IllegalArgumentException.class, () -> IPv4Address.isValidIPv4("256.256.256.256"));
    }

    @Test
    void testToUnsignedInteger() {
        IPv4Address address = new IPv4Address("192.168.1.1");
        assertEquals(0xC0A80101L, address.toUnsignedInteger());
    }

    @Test
    void testFromUnsignedInteger() {
        IPv4Address address = IPv4Address.fromUnsignedInteger(0xC0A80101L);
        assertEquals("192.168.1.1", address.toString());
    }

    @Test
    void testBroadcastAddress() {
        IPv4Address address = new IPv4Address("192.168.1.0");
        IPv4Address broadcast = address.getBroadcastAddress(24);

        assertEquals("192.168.1.255", broadcast.toString());
    }

    @Test
    void testFromBitmap() {
        IPv4Address base = new IPv4Address("192.168.1.0");
        List<Integer> activeIndexes = Arrays.asList(1, 2, 3);

        List<IPv4Address> result = IPv4Address.fromBitmap(base, activeIndexes);

        assertEquals(3, result.size());
        assertEquals("192.168.1.1", result.get(0).toString());
        assertEquals("192.168.1.2", result.get(1).toString());
        assertEquals("192.168.1.3", result.get(2).toString());
    }

    @Test
    void testValidateOctets() {
        assertTrue(IPv4Address.validateOctets(new int[]{192, 168, 1, 1}));
        assertThrows(IllegalArgumentException.class, () -> IPv4Address.validateOctets(new int[]{256, 0, 0, 0}));
    }
    
    @Nested
    class IPv4AddressSubnetTest {

        @Test
        void testIsInSameSubnet_ExactMatch() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.168.1.1");

            // /32 prefix means exact match
            assertTrue(address1.isInSameSubnet(address2, 32));
        }

        @Test
        void testIsInSameSubnet_SameSubnet24() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.168.1.254");

            // Same subnet for /24
            assertTrue(address1.isInSameSubnet(address2, 24));
        }

        @Test
        void testIsInSameSubnet_DifferentSubnet24() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.168.2.1");

            // Different subnets for /24
            assertFalse(address1.isInSameSubnet(address2, 24));
        }

        @Test
        void testIsInSameSubnet_SameSubnet16() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.168.5.1");

            // Same subnet for /16
            assertTrue(address1.isInSameSubnet(address2, 16));
        }

        @Test
        void testIsInSameSubnet_DifferentSubnet16() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.169.1.1");

            // Different subnets for /16
            assertFalse(address1.isInSameSubnet(address2, 16));
        }

        @Test
        void testIsInSameSubnet_SameSubnet8() {
            IPv4Address address1 = new IPv4Address("10.1.1.1");
            IPv4Address address2 = new IPv4Address("10.255.255.255");

            // Same subnet for /8
            assertTrue(address1.isInSameSubnet(address2, 8));
        }

        @Test
        void testIsInSameSubnet_DifferentSubnet8() {
            IPv4Address address1 = new IPv4Address("10.1.1.1");
            IPv4Address address2 = new IPv4Address("11.1.1.1");

            // Different subnets for /8
            assertFalse(address1.isInSameSubnet(address2, 8));
        }

        @Test
        void testIsInSameSubnet_EdgeCase0() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("10.0.0.1");

            // /0 means all IPs are in the same subnet
            assertTrue(address1.isInSameSubnet(address2, 0));
        }

        @Test
        void testIsInSameSubnet_InvalidPrefixNegative() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.168.1.254");

            // Invalid negative prefix length
            assertThrows(IllegalArgumentException.class, () -> address1.isInSameSubnet(address2, -1));
        }

        @Test
        void testIsInSameSubnet_InvalidPrefixTooLarge() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");
            IPv4Address address2 = new IPv4Address("192.168.1.254");

            // Invalid prefix length greater than 32
            assertThrows(IllegalArgumentException.class, () -> address1.isInSameSubnet(address2, 33));
        }

        @Test
        void testIsInSameSubnet_BroadcastAddress() {
            IPv4Address address1 = new IPv4Address("192.168.1.0");
            IPv4Address address2 = new IPv4Address("192.168.1.255");

            // Address1 and Address2 are in the same /24 subnet
            assertTrue(address1.isInSameSubnet(address2, 24));
        }

        @Test
        void testIsInSameSubnet_WildcardMask() {
            IPv4Address address1 = new IPv4Address("255.255.255.255");
            IPv4Address address2 = new IPv4Address("0.0.0.0");

            // /0 prefix includes all addresses
            assertTrue(address1.isInSameSubnet(address2, 0));
        }

        @Test
        void testIsInSameSubnet_SubnetBoundary() {
            IPv4Address address1 = new IPv4Address("192.168.1.0");
            IPv4Address address2 = new IPv4Address("192.168.2.0");

            // Address1 and Address2 are in different /24 subnets
            assertFalse(address1.isInSameSubnet(address2, 24));
        }

        @Test
        void testIsInSameSubnet_SameAddress() {
            IPv4Address address1 = new IPv4Address("192.168.1.1");

            // Same address with /32 prefix
            assertTrue(address1.isInSameSubnet(address1, 32));
        }
    }

}
