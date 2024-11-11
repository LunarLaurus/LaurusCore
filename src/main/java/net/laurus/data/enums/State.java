package net.laurus.data.enums;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum State implements Serializable {
    ENABLED("Enabled"), DISABLED("Disabled"), OFFLINE("Offline"), IN_TEST("InTest"), STARTING("Starting"),
    ABSENT("Absent"), UNKNOWN("unknown");

    String name;
}
