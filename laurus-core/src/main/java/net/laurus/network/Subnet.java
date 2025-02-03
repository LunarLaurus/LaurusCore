package net.laurus.network;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import net.laurus.interfaces.NetworkData;

@Value
public class Subnet implements NetworkData {

    private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
    private IPv4Address baseIp;
    private SubnetMask subnetMask;

    /**
     * Creates a Subnet object from a CIDR string (e.g., "192.168.1.0/24").
     * 
     * @param cidr The CIDR notation string.
     * @return A Subnet object representing the CIDR block.
     */
    public static Subnet fromCIDR(String cidr) {
        String[] parts = cidr.split("/");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid CIDR format: " + cidr);
        }
        IPv4Address baseIp = new IPv4Address(parts[0]);
        SubnetMask subnetMask = SubnetMask.fromPrefixLength(Integer.parseInt(parts[1]));
        return new Subnet(baseIp, subnetMask);
    }

    /**
     * Calculates the starting address of the network as an unsigned 32-bit value.
     * 
     * @return The unsigned 32-bit representation of the network's start address.
     */
    public long calculateNetworkStart() {
        return baseIp.toUnsignedInteger() & subnetMask.toUnsignedInteger();
    }

    /**
     * Calculates the ending address of the network as an unsigned 32-bit value.
     * 
     * @return The unsigned 32-bit representation of the network's end address.
     */
    public long calculateNetworkEnd() {
        long mask = ~subnetMask.toUnsignedInteger() & 0xFFFFFFFFL;
        return calculateNetworkStart() | mask;
    }

    /**
     * Determines whether a given IPv4 address is within the range of the subnet.
     * 
     * @param address The IPv4 address to check.
     * @return True if the address is part of the subnet, false otherwise.
     */
    public boolean containsAddress(IPv4Address address) {
        long networkStart = calculateNetworkStart();
        long networkEnd = calculateNetworkEnd();
        long addressLong = address.toUnsignedInteger();
        return addressLong >= networkStart && addressLong <= networkEnd;
    }

    /**
     * Returns a list of all IPv4 addresses in the subnet.
     * 
     * @return A list of IPv4Address objects representing all addresses in the subnet.
     */
    public List<IPv4Address> getAllAddresses() {
        List<IPv4Address> addresses = new ArrayList<>();
        long start = calculateNetworkStart();
        long end = calculateNetworkEnd();
        for (long i = start; i <= end; i++) {
            addresses.add(IPv4Address.fromUnsignedInteger(i));
        }
        return addresses;
    }

    /**
     * Returns the string representation of the subnet in CIDR format (e.g., "192.168.1.0/24").
     * 
     * @return The CIDR representation of the subnet.
     */
    @Override
    public String toString() {
        return baseIp.toString() + "/" + subnetMask.toPrefixLength();
    }
}
