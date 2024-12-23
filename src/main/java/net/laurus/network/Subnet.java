package net.laurus.network;

import lombok.Value;
import net.laurus.interfaces.NetworkData;

/**
 * The Subnet class represents a network subnet defined by a base IP address and a subnet mask.
 * It provides methods for calculating the network's start and end addresses, checking if a given IP address is
 * part of the subnet, and converting the subnet mask to its integer representation.
 * 
 * This class implements the {@link NetworkData} interface and is immutable due to the use of Lombok's @Value annotation.
 */
@Value
public class Subnet implements NetworkData {

    private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    private IPv4Address baseIp;        // The base IP address (e.g., 192.168.1.0)
    private SubnetMask subnetMask;    // The subnet mask (e.g., 255.255.255.0)

    /**
     * Calculates the starting address of the network based on the base IP and subnet mask.
     * The result is an integer representation of the starting address of the network.
     * 
     * @return The integer representation of the network's start address.
     */
    public int calculateNetworkStart() {
        return baseIp.toInteger() & subnetMask.toInteger();
    }

    /**
     * Calculates the ending address of the network based on the base IP and subnet mask.
     * The result is an integer representation of the ending address of the network.
     * 
     * @return The integer representation of the network's end address.
     */
    public int calculateNetworkEnd() {
        int mask = ~subnetMask.toInteger();
        return calculateNetworkStart() | mask;
    }

    /**
     * Determines whether a given IP address is within the range of the subnet.
     * This is done by checking if the IP address lies between the network start and network end addresses.
     * 
     * @param address The IPv4 address to check.
     * @return True if the address is part of the subnet, false otherwise.
     */
    public boolean containsAddress(IPv4Address address) {
        int networkStart = calculateNetworkStart();
        int networkEnd = calculateNetworkEnd();
        int addressInt = address.toInteger();
        return addressInt >= networkStart && addressInt <= networkEnd;
    }

    /**
     * Converts the subnet mask (e.g., "255.255.255.0") into its integer representation.
     * This is used for performing operations with the subnet, such as calculating network start and end addresses.
     * 
     * @return The integer representation of the subnet mask.
     */
    public int toInt() {
        String[] parts = subnetMask.toString().split("\\.");  // Split the subnet mask into its octets
        int mask = 0;
        for (String part : parts) {
            mask = (mask << 8) + Integer.parseInt(part);  // Shift each octet to the left and combine them
        }
        return mask;
    }
}
