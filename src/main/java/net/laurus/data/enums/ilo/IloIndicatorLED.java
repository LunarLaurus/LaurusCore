package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloIndicatorLED {

    UNKNOWN("Unknown"),
    LIT("Lit"),
    BLINKING("Blinking"),
    OFF("Off");

    private final String value;

    public static IloIndicatorLED get(String x) {
        for (IloIndicatorLED state : values()) {
            if (state.value.equalsIgnoreCase(x)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}