package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloUefiTargetBootSourceOverride {

    NONE("None"),
    PXE("Pxe"),
    FLOPPY("Floppy"),
    CD("Cd"),
    USB("Usb"),
    HDD("Hdd"),
    BIOS_SETUP("BiosSetup"),
    UTILITIES("Utilities"),
    DIAGS("Diags"),
    UEFI_SHELL("UefiShell"),
    UEFI_TARGET("UefiTarget"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloUefiTargetBootSourceOverride get(String x) {
        for (IloUefiTargetBootSourceOverride target : values()) {
            if (target.value.equalsIgnoreCase(x)) {
                return target;
            }
        }
        return UNKNOWN;
    }
}
