package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing the different states of an HP iLO object.
 */
@AllArgsConstructor
@Getter
public enum IloObjectState {

    /** Object is enabled */
    ENABLED("Enabled"),

    /** Object is disabled */
    DISABLED("Disabled"),

    /** Object is offline */
    OFFLINE("Offline"),

    /** Object is in a test state */
    IN_TEST("InTest"),

    /** Object is starting up */
    STARTING("Starting"),

    /** Object is absent */
    ABSENT("Absent"),

    /** Object state is unknown */
    UNKNOWN("Unknown");

    private final String name;
}
