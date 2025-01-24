package net.laurus.data.enums.ilo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloObjectState implements Serializable {
    ENABLED("Enabled"), DISABLED("Disabled"), OFFLINE("Offline"), IN_TEST("InTest"), STARTING("Starting"),
    ABSENT("Absent"), UNKNOWN("Unknown");

    String name;
}
