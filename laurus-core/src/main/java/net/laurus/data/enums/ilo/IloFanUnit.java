package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloFanUnit {
    RPM("RPM"), PERCENT("%"), UNKNOWN("unknown");

    String name;
}
