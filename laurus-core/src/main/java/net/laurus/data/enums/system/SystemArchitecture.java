package net.laurus.data.enums.system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

/**
 * Enum representing different system architectures.
 */
@AllArgsConstructor
@Getter
public enum SystemArchitecture implements NetworkData {

    X86(0),
    X64(1),
    ARM(2),
    ARM64(3),
    WASM(4),
    S390X(5),
    LOONGARCH64(6),
    ARMV6(7),
    PPC64LE(8);

    private static final long serialVersionUID = 0;

    private final int value;

    /**
     * Retrieves an enum value based on its integer representation.
     *
     * @param value The integer value.
     * @return The corresponding SystemArchitecture or throws an exception if unknown.
     */
    public static SystemArchitecture fromValue(int value) {
        for (SystemArchitecture arch : values()) {
            if (arch.value == value) {
                return arch;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    /**
     * Retrieves an enum value based on its string name.
     *
     * @param name The name of the architecture.
     * @return The corresponding SystemArchitecture or throws an exception if unknown.
     */
    public static SystemArchitecture fromName(String name) {
        try {
            return SystemArchitecture.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
