package net.laurus.network;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@EqualsAndHashCode
public class IPv4Address implements Serializable {
	
    private final int[] octets;

    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$");

    public IPv4Address(int[] oct) {
        this(oct[0], oct[1], oct[2], oct[3]);
    }
    
    public IPv4Address(int octet1, int octet2, int octet3, int octet4) {
        this.octets = new int[] { octet1, octet2, octet3, octet4 };
    }

    public IPv4Address(String address) {
        String[] octetStrings = address.split("\\.");

        if (octetStrings.length != 4) {
            throw new IllegalArgumentException("Invalid IPv4 address format. "+address);
        }
        if (!isValidIPv4(address)) {
            throw new IllegalArgumentException("Invalid IPv4 address: " + address);
        }
        octets = parseAddress(address);
    }

    private static int[] parseAddress(String address) {
        String[] parts = address.split("\\.");
        return new int[] {
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3])
        };
    }

    public static boolean isValidIPv4(String address) {
        if (address == null) return false;

        Matcher matcher = IPV4_PATTERN.matcher(address);
        if (!matcher.matches()) return false;

        return matcher.results()
                .mapToInt(m -> Integer.parseInt(m.group()))
                .allMatch(octet -> octet >= 0 && octet <= 255);
    }

    public int toInteger() {
        return (octets[0] << 24) | (octets[1] << 16) | (octets[2] << 8) | octets[3];
    }

    public static IPv4Address fromInteger(int value) {
        return new IPv4Address(
                (value >> 24) & 0xFF,
                (value >> 16) & 0xFF,
                (value >> 8) & 0xFF,
                value & 0xFF
        );
    }

    public boolean isInSameSubnet(IPv4Address other, int prefixLength) {
        int mask = ~((1 << (32 - prefixLength)) - 1);
        return (this.toInteger() & mask) == (other.toInteger() & mask);
    }
    
    private String get() {
    	return octets[0]+"."+octets[1]+"."+octets[2]+"."+octets[3];
    }

	@Override
	public String toString() {
		return get();
	}

	public String getAddress() {
		return get();
	}
}

