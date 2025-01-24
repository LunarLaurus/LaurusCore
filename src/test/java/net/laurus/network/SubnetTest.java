package net.laurus.network;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SubnetTest {

    @Test
    void testFromCIDR() {
        Subnet subnet = Subnet.fromCIDR("192.168.1.0/24");

        assertEquals("192.168.1.0/24", subnet.toString());
    }

    @Test
    void testCalculateNetworkStartAndEnd() {
        Subnet subnet = new Subnet(new IPv4Address("192.168.1.0"), new SubnetMask("255.255.255.0"));

        assertEquals(0xC0A80100L, subnet.calculateNetworkStart());
        assertEquals(0xC0A801FFL, subnet.calculateNetworkEnd());
    }

    @Test
    void testContainsAddress() {
        Subnet subnet = Subnet.fromCIDR("192.168.1.0/24");

        assertTrue(subnet.containsAddress(new IPv4Address("192.168.1.100")));
        assertFalse(subnet.containsAddress(new IPv4Address("192.168.2.100")));
    }

    @Test
    void testGetAllAddresses() {
        Subnet subnet = Subnet.fromCIDR("192.168.1.0/30");
        assertEquals(4, subnet.getAllAddresses().size());
    }
}
