package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IloObjectState {
    ENABLED("Enabled"), DISABLED("Disabled"), OFFLINE("Offline"), IN_TEST("InTest"), STARTING("Starting"),
    ABSENT("Absent"), UNKNOWN("Unknown");

    String name;
}
