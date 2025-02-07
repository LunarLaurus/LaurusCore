package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing UEFI target boot sources in HP iLO.
 */
@AllArgsConstructor
@Getter
public enum IloUefiTargetBootSourceOverride {

    /** No boot source override */
    NONE("None"),

    /** Boot from PXE (Preboot Execution Environment) */
    PXE("Pxe"),

    /** Boot from a floppy disk */
    FLOPPY("Floppy"),

    /** Boot from a CD/DVD */
    CD("Cd"),

    /** Boot from a USB device */
    USB("Usb"),

    /** Boot from a hard disk drive */
    HDD("Hdd"),

    /** Enter BIOS setup */
    BIOS_SETUP("BiosSetup"),

    /** Boot into utilities */
    UTILITIES("Utilities"),

    /** Boot into diagnostics mode */
    DIAGS("Diags"),

    /** Boot into UEFI shell */
    UEFI_SHELL("UefiShell"),

    /** Boot into a UEFI target */
    UEFI_TARGET("UefiTarget"),

    /** The boot source override state is unknown */
    UNKNOWN("Unknown");

    private final String value;

    /**
     * Retrieves the corresponding enum value for a given string.
     *
     * @param x The string representation of the UEFI boot source.
     * @return The matching {@link IloUefiTargetBootSourceOverride} value, or {@code UNKNOWN} if not found.
     */
    public static IloUefiTargetBootSourceOverride get(String x) {
        for (IloUefiTargetBootSourceOverride target : values()) {
            if (target.value.equalsIgnoreCase(x)) {
                return target;
            }
        }
        return UNKNOWN;
    }
}
