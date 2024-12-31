package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloPostState {
	
    UNKNOWN("Unknown"),
    RESET("Reset"),
    POWER_OFF("PowerOff"),
    IN_POST("InPost"),
    IN_POST_DISCOVERY_COMPLETE("InPostDiscoveryComplete"),
    FINISHED_POST("FinishedPost");

    private final String value;

    public static IloPostState get(String x) {
        for (IloPostState state : values()) {
            if (state.value.equalsIgnoreCase(x)) {
                return state;
            }
        }
        return UNKNOWN;
    }
}