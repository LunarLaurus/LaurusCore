package net.laurus.data.enums;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Unit implements Serializable {
    RPM("RPM"), PERCENT("%"), UNKNOWN("unknown");

    String name;
}
