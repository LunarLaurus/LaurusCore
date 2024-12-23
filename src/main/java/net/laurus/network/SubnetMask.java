package net.laurus.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

/**
 * The SubnetMask class represents a subnet mask and provides utility methods to manipulate
 * and validate the mask, as well as convert it to a prefix length and integer format.
 * 
 * The class implements {@link NetworkData}, providing version control and ensuring consistency
 * across network data types.
 */
@Getter
@EqualsAndHashCode
public class SubnetMask implements NetworkData {

    private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    // Regular expression pattern to match valid subnet mask addresses
    private static final Pattern SUBNET_MASK_PATTERN = Pattern.compile(
            "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");

    // The subnet mask is stored as an array of integers (octets)
    private final int[] octets;

    /**
     * Constructor that initializes a SubnetMask from an array of integers representing the octets.
     * 
     * @param oct An array of four integers representing the subnet mask octets.
     */
    public SubnetMask(int[] oct) {
        this(oct[0], oct[1], oct[2], oct[3]);
    }

    /**
     * Constructor that initializes a SubnetMask from individual octets.
     * 
     * @param octet1 The first octet of the subnet mask.
     * @param octet2 The second octet of the subnet mask.
     * @param octet3 The third octet of the subnet mask.
     * @param octet4 The fourth octet of the subnet mask.
     */
    public SubnetMask(int octet1, int octet2, int octet3, int octet4) {
        this.octets = new int[] { octet1, octet2, octet3, octet4 };
    }

    /**
     * Constructor that initializes a SubnetMask from a string in the format "x.x.x.x" where x is an integer.
     * 
     * @param mask The subnet mask in string format.
     * @throws IllegalArgumentException If the provided string is not a valid subnet mask.
     */
    public SubnetMask(String mask) {
        String[] octetStrings = mask.split("\\.");

        // Check if the string contains exactly 4 octets
        if (octetStrings.length != 4) {
            throw new IllegalArgumentException("Invalid subnet mask format: " + mask);
        }

        // Validate the mask format using regex
        if (!isValidSubnetMask(mask)) {
            throw new IllegalArgumentException("Invalid subnet mask: " + mask);
        }

        // Parse the mask string into an array of integers representing the octets
        octets = parseMask(mask);
    }

    /**
     * Parses a subnet mask string into an array of integers representing the octets.
     * 
     * @param mask The subnet mask string (e.g., "255.255.255.0").
     * @return An array of integers representing the octets of the subnet mask.
     */
    private static int[] parseMask(String mask) {
        String[] parts = mask.split("\\.");
        return new int[] {
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        };
    }

    /**
     * Validates if the given string is a valid subnet mask.
     * A valid subnet mask has a series of ones followed by a series of zeros in binary representation.
     * 
     * @param mask The string representing the subnet mask.
     * @return True if the mask is valid, false otherwise.
     */
    public static boolean isValidSubnetMask(String mask) {
        if (mask == null) return false;

        Matcher matcher = SUBNET_MASK_PATTERN.matcher(mask);
        if (!matcher.matches()) return false;

        // Ensure that the mask has a valid sequence of 1's followed by 0's in binary representation
        int value = 0;
        for (String octet : mask.split("\\.")) {
            value = (value << 8) + Integer.parseInt(octet);
        }

        // Check if the mask is a valid binary representation (ones followed by zeros)
        String binary = String.format("%32s", Integer.toBinaryString(value)).replace(' ', '0');
        return binary.indexOf("01") == -1;  // Ensure no '1' bits follow '0' bits in binary form
    }

    /**
     * Converts the subnet mask to its 32-bit integer representation.
     * 
     * @return The integer representation of the subnet mask.
     */
    public int toInteger() {
        return (octets[0] << 24) | (octets[1] << 16) | (octets[2] << 8) | octets[3];
    }

    /**
     * Converts the subnet mask to a prefix length (CIDR notation).
     * 
     * @return The prefix length (e.g., 24 for "255.255.255.0").
     */
    public int toPrefixLength() {
        int mask = toInteger();
        int prefixLength = 0;

        // Count the number of leading 1's in the mask
        while (mask != 0) {
            prefixLength++;
            mask <<= 1;
        }

        return prefixLength;
    }

    /**
     * Returns the string representation of the subnet mask in the format "x.x.x.x".
     * 
     * @return The subnet mask as a string.
     */
    private String get() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }

    /**
     * Converts the subnet mask to a string representation.
     * 
     * @return The subnet mask in the format "x.x.x.x".
     */
    @Override
    public String toString() {
        return get();
    }

    /**
     * Returns the string representation of the subnet mask. This is an alias for the {@link #toString()} method.
     * 
     * @return The string representation of the subnet mask.
     */
    public String getMask() {
        return get();
    }
}
