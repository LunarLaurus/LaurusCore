package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloTpmModuleType {

    UNSPECIFIED("Unspecified"),
    TPM1_2("TPM1.2"),
    TPM2_0("TPM2.0"),
    TM1_0("TM1.0"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloTpmModuleType get(String x) {
        for (IloTpmModuleType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
