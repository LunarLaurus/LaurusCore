package net.laurus.data.enums.ilo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloFanUnit implements Serializable {
    RPM("RPM"), PERCENT("%"), UNKNOWN("unknown");

    String name;
}
