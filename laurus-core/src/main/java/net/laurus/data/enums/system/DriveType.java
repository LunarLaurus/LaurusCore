package net.laurus.data.enums.system;

/**
 * Enum representing different types of drives.
 */
public enum DriveType {

    /** Unknown drive type */
    UNKNOWN(0),

    /** No root directory detected */
    NO_ROOT_DIRECTORY(1),

    /** Removable drive (e.g., USB drive) */
    REMOVABLE(2),

    /** Fixed drive (e.g., HDD or SSD) */
    FIXED(3),

    /** Network drive */
    NETWORK(4),

    /** CD/DVD drive */
    CD_ROM(5),

    /** RAM disk */
    RAM(6);

    private final int value;

    DriveType(int value) {
        this.value = value;
    }

    /**
     * Gets the integer value associated with the drive type.
     *
     * @return the numeric value of the drive type.
     */
    public int getValue() {
        return value;
    }

    /**
     * Gets a string representation of the enum.
     *
     * @param driveType The drive type.
     * @return The name of the drive type.
     */
    public static String toString(DriveType driveType) {
        return driveType.name();
    }

    /**
     * Converts a string into a corresponding DriveType enum (case-insensitive).
     *
     * @param name The string representation of the drive type.
     * @return The corresponding DriveType or UNKNOWN if not found.
     */
    public static DriveType fromString(String name) {
        try {
            return DriveType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
