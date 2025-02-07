package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the types of TPM (Trusted Platform Module) available in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloTpmModuleType {

    /** TPM module type is unspecified */
    UNSPECIFIED("Unspecified"),

    /** TPM version 1.2 */
    TPM1_2("TPM1.2"),

    /** TPM version 2.0 */
    TPM2_0("TPM2.0"),

    /** TM version 1.0 (alternative module type) */
    TM1_0("TM1.0"),

    /** TPM module type is unknown */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the TPM module type.
     * @return The matching {@link IloTpmModuleType} value, or {@code UNKNOWN} if not found.
     */
    public static IloTpmModuleType get(String x) {
        for (IloTpmModuleType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
