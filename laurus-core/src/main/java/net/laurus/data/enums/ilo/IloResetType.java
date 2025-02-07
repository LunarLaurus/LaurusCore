package net.laurus.data.enums.ilo;

import javax.management.InvalidApplicationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing different types of reset actions for an HP iLO device.
 */
@AllArgsConstructor
@Getter
public enum IloResetType {

    /** Power on the system */
    ON("On"),

    /** Force the system to power off */
    FORCE_OFF("ForceOff"),

    /** Force the system to restart */
    FORCE_RESTART("ForceRestart"),

    /** Trigger a non-maskable interrupt (NMI) */
    NMI("Nmi"),

    /** Simulate pressing the power button */
    PUSH_POWER_BUTTON("PushPowerButton");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the reset type.
     * @return The matching {@link IloResetType} value.
     * @throws InvalidApplicationException if the reset type is not found.
     */
    public static IloResetType get(String x) throws InvalidApplicationException {
        for (IloResetType resetType : values()) {
            if (resetType.value.equalsIgnoreCase(x)) {
                return resetType;
            }
        }
        throw new InvalidApplicationException("Invalid reset type: " + x);
    }
}
