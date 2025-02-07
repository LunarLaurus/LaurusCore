package net.laurus.data.enums.ilo;

import java.util.HashMap;

/**
 * Enum representing various HP memory statuses.
 */
public enum HpMemoryStatus {

    UNKNOWN, OTHER, NOT_PRESENT, PRESENT_UNUSED, GOOD_IN_USE, ADDED_BUT_UNUSED, UPGRADED_BUT_UNUSED,
    EXPECTED_BUT_MISSING, DOES_NOT_MATCH, NOT_SUPPORTED, CONFIGURATION_ERROR, DEGRADED, PRESENT_SPARE,
    GOOD_PARTIALLY_IN_USE;

    /**
     * Maps each enum value to a formatted name string.
     */
    private static final HashMap<HpMemoryStatus, String> nameMap = new HashMap<>();

    /**
     * Maps string representations of memory statuses to enum values.
     */
    private static final HashMap<String, HpMemoryStatus> statusMap = new HashMap<>();

    /**
     * Retrieves the enum value corresponding to a given string.
     *
     * @param status The string representation of the memory status.
     * @return The corresponding {@link HpMemoryStatus} enum value, or {@code UNKNOWN} if not found.
     */
    public static HpMemoryStatus get(String status) {
        return statusMap.computeIfAbsent(status, o -> {
            for (HpMemoryStatus s : HpMemoryStatus.values()) {
                String name = nameMap.computeIfAbsent(s, p -> s.name().replaceAll("_", "").toUpperCase());
                if (name.equals(status)) {
                    return s;
                }
            }
            return HpMemoryStatus.UNKNOWN;
        });
    }
}
