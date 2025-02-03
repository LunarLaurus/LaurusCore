package net.laurus.network;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class IPv4Address implements NetworkData {

    private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    // Regular expression pattern to match valid IPv4 addresses
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");

    // The IPv4 address stored as an array of integers (octets)
    private int[] octets;

    /**
     * Constructor that initializes an IPv4 address from a string in the format "x.x.x.x" where x is an integer.
     *
     * @param address The IPv4 address in string format.
     * @throws IllegalArgumentException If the provided string is not a valid IPv4 address.
     */
    public IPv4Address(String address) {
        this(parseAddress(address));
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
        this(new int[]{ octet1, octet2, octet3, octet4 });
    }

    /**
     * Constructor that initializes an IPv4 address from an array of integers representing the octets.
     * 
     * @param oct An array of four integers representing the IPv4 address octets.
     */
    public IPv4Address(int[] oct) {
    	validateOctets(oct);
    	this.octets = oct;
    }

    /**
     * Parses an IPv4 address string into an array of integers representing the octets.
     * 
     * @param address The IPv4 address string (e.g., "192.168.1.1").
     * @return An array of integers representing the octets of the address.
     */
    private static int[] parseAddress(String address) {
        String[] parts = address.split("\\.");
        validateOctetLength(parts);
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
        String[] parts = address.split("\\.");
        int[] octets = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
        return validateOctets(octets);
    }


    /**
     * Converts the IPv4 address to its unsigned 32-bit integer representation.
     * 
     * @return The unsigned 32-bit integer representation of the IPv4 address as a long.
     */
    public long toUnsignedInteger() {
    	if (validateOctets(octets)) {
        return ((long) (octets[0] & 0xFF) << 24) |
               ((long) (octets[1] & 0xFF) << 16) |
               ((long) (octets[2] & 0xFF) << 8) |
               ((long) (octets[3] & 0xFF));
    	}
		return 0;
    }
    
    /**
     * Converts an unsigned integer to an IPv4 address object.
     * 
     * @param value The unsigned 32-bit integer representation of an IPv4 address.
     * @return The corresponding IPv4Address object.
     */
    public static IPv4Address fromUnsignedInteger(long value) {
        if (value < 0 || value > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("Value must be between 0 and 4294967295 (unsigned 32-bit integer range).");
        }
        return new IPv4Address(
                (int) ((value >> 24) & 0xFF),
                (int) ((value >> 16) & 0xFF),
                (int) ((value >> 8) & 0xFF),
                (int) (value & 0xFF)
        );
    }

    /**
     * Checks if this IPv4 address and another IPv4 address are in the same subnet based on the given prefix length.
     * 
     * @param other The other IPv4 address to compare with.
     * @param prefixLength The subnet mask prefix length (e.g., 24 for a /24 subnet).
     * @return True if both addresses are in the same subnet, false otherwise.
     * @throws IllegalArgumentException If the prefix length is not between 0 and 32.
     */
    public boolean isInSameSubnet(IPv4Address other, int prefixLength) {
        if (prefixLength < 0 || prefixLength > 32) {
            throw new IllegalArgumentException("Prefix length must be between 0 and 32.");
        }

        long mask = prefixLength == 0 ? 0 : ~((1L << (32 - prefixLength)) - 1) & 0xFFFFFFFFL;

        long thisAddress = this.toUnsignedInteger();
        long otherAddress = other.toUnsignedInteger();

        return (thisAddress & mask) == (otherAddress & mask);
    }


    /**
     * Returns the string representation of the IPv4 address in the format "x.x.x.x".
     * 
     * @return The IPv4 address as a string.
     */
    @Override
    public String toString() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }

	public static List<IPv4Address> fromBitmap(IPv4Address baseAddress, List<Integer> activeIndexes) {
		List<IPv4Address> activeAddresses = new ArrayList<>();
		int[] baseOctets = baseAddress.octets;
		for (int x : activeIndexes) {
			activeAddresses.add(new IPv4Address(new int[] {baseOctets[0], baseOctets[1], baseOctets[2], x}));
		}
		return activeAddresses;
	}
	
	public static boolean validateOctets(int[] octets) {
        return validateOctetLength(octets) && validateOctetValues(octets);
	}
	
	public static boolean validateOctetValues(int[] octets) {
        for (int octet : octets) {
            if (octet < 0 || octet > 255) {
                throw new IllegalArgumentException("Each octet must be between 0 and 255.");
            }
        }	
        return true;
	}

	/**
	 * Validates that the given array is not null and has exactly 4 elements.
	 *
	 * @param array The array to validate.
	 * @return True if the array has exactly 4 elements.
	 * @throws IllegalArgumentException If the input is null or does not have exactly 4 elements.
	 */
	public static boolean validateOctetLength(Object array) {
	    if (array == null || !array.getClass().isArray() || Array.getLength(array) != 4) {
	        throw new IllegalArgumentException(
	            "IPv4 address must have exactly 4 elements. Data: " +
	            (array == null ? "null" : Arrays.deepToString((Object[]) array))
	        );
	    }
	    return true;
	}
	
	/**
	 * Calculates the broadcast address for the current IPv4 address given a subnet mask.
	 * 
	 * @param prefixLength The subnet mask prefix length.
	 * @return The broadcast address as an IPv4Address object.
	 */
	public IPv4Address getBroadcastAddress(int prefixLength) {
	    if (prefixLength < 0 || prefixLength > 32) {
	        throw new IllegalArgumentException("Prefix length must be between 0 and 32.");
	    }
	    long mask = prefixLength == 0 ? 0 : ~((1L << (32 - prefixLength)) - 1);
	    long broadcast = this.toUnsignedInteger() | ~mask;
	    return fromUnsignedInteger(broadcast);
	}


}
