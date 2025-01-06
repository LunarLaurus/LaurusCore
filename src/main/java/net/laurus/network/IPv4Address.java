package net.laurus.network;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

/**
 * The IPv4Address class represents an IPv4 address and provides various utility methods to manipulate
 * and validate the address. It supports parsing from string representations, converting to and from integer format,
 * validating IPv4 addresses, and performing network-related operations like subnet matching.
 * 
 * The class implements {@link NetworkData}, providing version control and ensuring consistency across network data types.
 */
@Getter
@EqualsAndHashCode
public class IPv4Address implements NetworkData {

    private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    // Regular expression pattern to match valid IPv4 addresses
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");

    // The IPv4 address stored as an array of integers (octets)
    private final int[] octets;

    /**
     * Constructor that initializes an IPv4 address from an array of integers representing the octets.
     * 
     * @param oct An array of four integers representing the IPv4 address octets.
     */
    public IPv4Address(int[] oct) {
        this(oct[0], oct[1], oct[2], oct[3]);
    }

    /**
     * Constructor that initializes an IPv4 address from individual octets.
     * 
     * @param octet1 The first octet of the IPv4 address.
     * @param octet2 The second octet of the IPv4 address.
     * @param octet3 The third octet of the IPv4 address.
     * @param octet4 The fourth octet of the IPv4 address.
     */
    public IPv4Address(int octet1, int octet2, int octet3, int octet4) {
        this.octets = new int[] { octet1, octet2, octet3, octet4 };
    }

    /**
     * Constructor that initializes an IPv4 address from a string in the format "x.x.x.x" where x is an integer.
     * 
     * @param address The IPv4 address in string format.
     * @throws IllegalArgumentException If the provided string is not a valid IPv4 address.
     */
    public IPv4Address(String address) {
        String[] octetStrings = address.split("\\.");

        // Check if the string contains exactly 4 octets
        if (octetStrings.length != 4) {
            throw new IllegalArgumentException("Invalid IPv4 address format. " + address);
        }

        // Validate the address format using regex
        if (!isValidIPv4(address)) {
            throw new IllegalArgumentException("Invalid IPv4 address: " + address);
        }

        // Parse the address string into an array of integers representing the octets
        octets = parseAddress(address);
    }

    /**
     * Parses an IPv4 address string into an array of integers representing the octets.
     * 
     * @param address The IPv4 address string (e.g., "192.168.1.1").
     * @return An array of integers representing the octets of the address.
     */
    private static int[] parseAddress(String address) {
        String[] parts = address.split("\\.");
        return new int[] {
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        };
    }

    /**
     * Validates if the given string is a valid IPv4 address.
     * 
     * @param address The string representing the IPv4 address.
     * @return True if the address is a valid IPv4 address, false otherwise.
     */
    public static boolean isValidIPv4(String address) {
        if (address == null) return false;

        Matcher matcher = IPV4_PATTERN.matcher(address);
        if (!matcher.matches()) return false;

        // Ensure each octet is in the range 0-255
        return matcher.results()
                .mapToInt(m -> Integer.parseInt(m.group()))
                .allMatch(octet -> octet >= 0 && octet <= 255);
    }

    /**
     * Converts the IPv4 address to its 32-bit integer representation.
     * 
     * @return The 32-bit integer representation of the IPv4 address.
     */
    public int toInteger() {
        return (octets[0] << 24) | (octets[1] << 16) | (octets[2] << 8) | octets[3];
    }

    /**
     * Converts an integer to an IPv4 address object.
     * 
     * @param value The integer representation of an IPv4 address.
     * @return The corresponding IPv4Address object.
     */
    public static IPv4Address fromInteger(int value) {
        return new IPv4Address(
                (value >> 24) & 0xFF,
                (value >> 16) & 0xFF,
                (value >> 8) & 0xFF,
                value & 0xFF
        );
    }

    /**
     * Checks if this IPv4 address and another IPv4 address are in the same subnet based on the given prefix length.
     * 
     * @param other The other IPv4 address to compare with.
     * @param prefixLength The subnet mask prefix length (e.g., 24 for a /24 subnet).
     * @return True if both addresses are in the same subnet, false otherwise.
     */
    public boolean isInSameSubnet(IPv4Address other, int prefixLength) {
        // Create a mask based on the prefix length and check if the two addresses match
        int mask = ~((1 << (32 - prefixLength)) - 1);
        return (this.toInteger() & mask) == (other.toInteger() & mask);
    }

    /**
     * Returns the string representation of the IPv4 address in the format "x.x.x.x".
     * 
     * @return The IPv4 address as a string.
     */
    private String get() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }

    /**
     * Converts the IPv4 address to a string representation.
     * 
     * @return The IPv4 address in the format "x.x.x.x".
     */
    @Override
    public String toString() {
        return get();
    }

    /**
     * Returns the string representation of the IPv4 address. This is an alias for the {@link #toString()} method.
     * 
     * @return The string representation of the IPv4 address.
     */
    public String getAddress() {
        return get();
    }

	public static List<IPv4Address> fromBitmap(IPv4Address baseAddress, List<Integer> activeIndexes) {
		List<IPv4Address> activeAddresses = new ArrayList<>();
		int[] baseOctets = baseAddress.octets;
		for (int x : activeIndexes) {
			activeAddresses.add(new IPv4Address(new int[] {baseOctets[0], baseOctets[1], baseOctets[2], x}));
		}
		return activeAddresses;
	}
}
