package net.laurus.data.enums.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing different types of drives.
 */
@RequiredArgsConstructor
@Getter
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

    /**
     * The integer value associated with the drive type.
     */
    private final int value;

    /**
     * Converts a string into a corresponding DriveType enum (case-insensitive).
     *
     * @param name The string representation of the drive type.
     * @return The corresponding DriveType or UNKNOWN if not found.
     */
    public static DriveType fromString(String name) {
        if (name == null || name.trim().isEmpty()) {
            return UNKNOWN;
        }
        try {
            return valueMap.getOrDefault(name.trim().toUpperCase(), UNKNOWN);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    private static final Map<String, DriveType> valueMap = initializeValueMap();

    private static Map<String, DriveType> initializeValueMap(){
        Map<String, DriveType> map = new HashMap<>();
        for(DriveType driveType : DriveType.values()){
            map.put(driveType.name().trim().toUpperCase(), driveType);
        }
        return map;
    }
}