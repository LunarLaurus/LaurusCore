package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloPostMode {

    NORMAL("Normal"),
    POST_TO_SHUTDOWN("PostToShutdown"),
    POST_TO_REBOOT("PostToReboot"),
    UNKNOWN("Unknown");

    private final String value;

    public static IloPostMode get(String x) {
        for (IloPostMode mode : values()) {
            if (mode.value.equalsIgnoreCase(x)) {
                return mode;
            }
        }
        return UNKNOWN;
    }
}
