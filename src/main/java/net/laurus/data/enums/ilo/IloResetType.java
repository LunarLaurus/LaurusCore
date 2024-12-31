package net.laurus.data.enums.ilo;

import javax.management.InvalidApplicationException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloResetType {
	
    ON("On"),
    FORCE_OFF("ForceOff"),
    FORCE_RESTART("ForceRestart"),
    NMI("Nmi"),
    PUSH_POWER_BUTTON("PushPowerButton");

    private final String value;

    public static IloResetType get(String x) throws InvalidApplicationException {
        for (IloResetType resetType : values()) {
            if (resetType.value.equalsIgnoreCase(x)) {
                return resetType;
            }
        }
        throw new InvalidApplicationException(x);
    }
}

