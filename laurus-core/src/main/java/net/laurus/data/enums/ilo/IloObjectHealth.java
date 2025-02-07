package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

/**
 * Enum representing the health status of an HP iLO object.
 */
@AllArgsConstructor
@Getter
public enum IloObjectHealth implements NetworkData {

    /** Object is in good condition */
    OK,

    /** Object is in a warning state */
    WARNING,

    /** Object is in a critical failure state */
    CRITICAL,

    /** Object health is unknown */
    UNKNOWN;
}
