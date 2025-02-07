package net.laurus.data.enums.ilo;

import java.util.HashMap;

/**
 * Enum representing various types of HP memory error correction mechanisms.
 */
public enum HpMemoryErrorCorrection {

    /** No error correction */
    NONE,

    /** Parity error correction */
    PARITY,

    /** Single-bit error correction */
    SINGLE_BIT_ECC,

    /** Multi-bit error correction */
    MULTI_BIT_ECC,

    /** Cyclic Redundancy Check (CRC) based error correction */
    CRC;

    /**
     * Maps each enum value to its corresponding name without underscores.
     */
    private static final HashMap<HpMemoryErrorCorrection, String> nameMap = new HashMap<>();

    /**
     * Maps string representations of correction types to enum values.
     */
    private static final HashMap<String, HpMemoryErrorCorrection> statusMap = new HashMap<>();

    /**
     * Retrieves the enum value corresponding to a given string.
     * 
     * @param ecc The string representation of the error correction type.
     * @return The corresponding {@link HpMemoryErrorCorrection} enum value, or {@code NONE} if not found.
     */
    public static HpMemoryErrorCorrection get(String ecc) {
        return statusMap.computeIfAbsent(ecc, o -> {
            for (HpMemoryErrorCorrection s : HpMemoryErrorCorrection.values()) {
                String name = nameMap.computeIfAbsent(s, p -> s.name().replaceAll("_", "").toUpperCase());
                if (name.equals(ecc.toUpperCase())) {
                    return s;
                }
            }
            return HpMemoryErrorCorrection.NONE;
        });
    }
}
