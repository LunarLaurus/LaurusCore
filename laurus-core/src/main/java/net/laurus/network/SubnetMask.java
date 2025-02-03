package net.laurus.network;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

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
     * @throws IllegalArgumentException If the octets are invalid.
     */
    public SubnetMask(int[] oct) {
        if (oct == null || oct.length != 4) {
            throw new IllegalArgumentException("Subnet mask must have exactly 4 octets.");
        }
        validateOctets(oct);
        this.octets = oct.clone(); // Ensure immutability
    }

    /**
     * Constructor that initializes a SubnetMask from individual octets.
     *
     * @param octet1 The first octet of the subnet mask.
     * @param octet2 The second octet of the subnet mask.
     * @param octet3 The third octet of the subnet mask.
     * @param octet4 The fourth octet of the subnet mask.
     * @throws IllegalArgumentException If any of the octets are invalid.
     */
    public SubnetMask(int octet1, int octet2, int octet3, int octet4) {
        this(new int[]{octet1, octet2, octet3, octet4});
    }

    /**
     * Constructor that initializes a SubnetMask from a string representation (e.g., "255.255.255.0").
     *
     * @param mask The subnet mask in string format.
     * @throws IllegalArgumentException If the provided string is not a valid subnet mask.
     */
    public SubnetMask(String mask) {
        if (!isValidSubnetMask(mask)) {
            throw new IllegalArgumentException("Invalid subnet mask: " + mask);
        }
        this.octets = parseMask(mask);
    }

    /**
     * Creates a SubnetMask from a prefix length (CIDR notation).
     *
     * @param prefixLength The prefix length (e.g., 24 for "255.255.255.0").
     * @return A SubnetMask object representing the given prefix length.
     * @throws IllegalArgumentException If the prefix length is not between 0 and 32.
     */
    public static SubnetMask fromPrefixLength(int prefixLength) {
        if (prefixLength < 0 || prefixLength > 32) {
            throw new IllegalArgumentException("Prefix length must be between 0 and 32.");
        }
        long value = ~((1L << (32 - prefixLength)) - 1) & 0xFFFFFFFFL;
        return fromUnsignedInteger(value);
    }

    /**
     * Creates a SubnetMask from its unsigned 32-bit integer representation.
     *
     * @param value The unsigned integer representation of the subnet mask.
     * @return A SubnetMask object.
     * @throws IllegalArgumentException If the value is invalid.
     */
    public static SubnetMask fromUnsignedInteger(long value) {
        if (value < 0 || value > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("Value must be between 0 and 4294967295 (unsigned 32-bit integer range).");
        }
        return new SubnetMask(
                (int) ((value >> 24) & 0xFF),
                (int) ((value >> 16) & 0xFF),
                (int) ((value >> 8) & 0xFF),
                (int) (value & 0xFF)
        );
    }

    /**
     * Converts the SubnetMask to its unsigned 32-bit integer representation.
     *
     * @return The unsigned 32-bit integer representation of the subnet mask as a long.
     */
    public long toUnsignedInteger() {
        return ((long) (octets[0] & 0xFF) << 24) |
               ((long) (octets[1] & 0xFF) << 16) |
               ((long) (octets[2] & 0xFF) << 8) |
               ((long) (octets[3] & 0xFF));
    }

    /**
     * Converts the SubnetMask to a prefix length (CIDR notation).
     *
     * @return The prefix length (e.g., 24 for "255.255.255.0").
     */
    public int toPrefixLength() {
        long mask = toUnsignedInteger();
        int prefixLength = 0;

        // Count the number of leading 1's in the mask
        while ((mask & 0x80000000L) != 0) {
            prefixLength++;
            mask <<= 1;
        }

        return prefixLength;
    }

    /**
     * Validates if the given string is a valid subnet mask.
     *
     * @param mask The string representing the subnet mask.
     * @return True if the mask is valid, false otherwise.
     */
    public static boolean isValidSubnetMask(String mask) {
        if (mask == null) return false;

        Matcher matcher = SUBNET_MASK_PATTERN.matcher(mask);
        if (!matcher.matches()) return false;

        try {
            int[] octets = parseMask(mask);
            validateOctets(octets);

            long value = ((long) (octets[0] & 0xFF) << 24) |
                         ((long) (octets[1] & 0xFF) << 16) |
                         ((long) (octets[2] & 0xFF) << 8) |
                         ((long) (octets[3] & 0xFF));

            String binary = String.format("%32s", Long.toBinaryString(value)).replace(' ', '0');
            return binary.indexOf("01") == -1;  // Ensure no '1' bits follow '0' bits
        } catch (NumberFormatException e) {
            return false; // Invalid numbers in the mask
        }
    }

    private static int[] parseMask(String mask) {
        String[] parts = mask.split("\\.");
        return new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        };
    }

    private static void validateOctets(int[] octets) {
        for (int octet : octets) {
            if (octet < 0 || octet > 255) {
                throw new IllegalArgumentException("Each octet must be between 0 and 255.");
            }
        }
    }

    @Override
    public String toString() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }
}
