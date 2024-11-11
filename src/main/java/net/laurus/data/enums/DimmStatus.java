package net.laurus.data.enums;

import java.io.Serializable;
import java.util.HashMap;

public enum DimmStatus implements Serializable {

    UNKNOWN, OTHER, NOT_PRESENT, PRESENT_UNUSED, GOOD_IN_USE, ADDED_BUT_UNUSED, UPGRADED_BUT_UNUSED,
    EXPECTED_BUT_MISSING, DOES_NOT_MATCH, NOT_SUPPORTED, CONFIGURATION_ERROR, DEGRADED, PRESENT_SPARE,
    GOOD_PARTIALLY_IN_USE;

    private static final HashMap<DimmStatus, String> nameMap = new HashMap<>();
    private static final HashMap<String, DimmStatus> statusMap = new HashMap<>();

    public static DimmStatus get(String status) {

        return statusMap.computeIfAbsent(status, o -> {
            for (DimmStatus s : DimmStatus.values()) {
                String name = nameMap.computeIfAbsent(s, p -> s.name().replaceAll("_", "").toUpperCase());
                if (name.equals(status)) {
                    return s;
                }
            }
            return DimmStatus.UNKNOWN;
        });

    }

}
