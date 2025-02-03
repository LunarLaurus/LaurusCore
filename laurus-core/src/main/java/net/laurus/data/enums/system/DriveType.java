package net.laurus.data.enums.system;

public enum DriveType {
	
    UNKNOWN(0),
    NO_ROOT_DIRECTORY(1),
    REMOVABLE(2),
    FIXED(3),
    NETWORK(4),
    CD_ROM(5),
    RAM(6);

    private final int value;

    DriveType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Method to get a string representation of the enum
    public static String toString(DriveType driveType) {
        return driveType.name();
    }

    // Method to get an enum value from a string (case-insensitive)
    public static DriveType fromString(String name) {
        try {
            return DriveType.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // Invalid name, return null
        }
    }
}
