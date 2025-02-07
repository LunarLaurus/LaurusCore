package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing types of HP memory.
 */
@Getter
@AllArgsConstructor
public enum HpMemoryType {

    /** HP Smart Memory */
    HPSMARTMEMORY("HP Smart Memory"),

    /** HP Standard Memory */
    HPSTANDARD("HP Standard Memory"),

    /** Unknown memory type */
    UNKNOWN("Unknown");

    /**
     * Display name for the memory type.
     */
    private final String displayName;
}
