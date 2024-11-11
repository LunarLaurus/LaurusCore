package net.laurus.data.enums.system;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SystemArchitecture implements Serializable {

    X86(0),
    X64(1),
    ARM(2),
    ARM64(3),
    WASM(4),
    S390X(5),
    LOONGARCH64(6),
    ARMV6(7),
    PPC64LE(8);

    private final int value;

    // Method to get the string representation of the enum constant
    public String getName() {
        return name();
    }

    public static SystemArchitecture fromValue(int value) {
        for (SystemArchitecture arch : SystemArchitecture.values()) {
            if (arch.getValue() == value) {
                return arch;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    public static SystemArchitecture fromName(String name) {
        try {
            return SystemArchitecture.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown name: " + name, e);
        }
    }
}
