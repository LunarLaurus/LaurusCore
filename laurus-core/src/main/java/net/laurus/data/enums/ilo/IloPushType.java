package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloPushType {

    PRESS("Press"),
    PRESS_AND_HOLD("PressAndHold");

    private final String value;

    public static IloPushType get(String x) {
        for (IloPushType type : values()) {
            if (type.value.equalsIgnoreCase(x)) {
                return type;
            }
        }
        return PRESS;
    }
}

