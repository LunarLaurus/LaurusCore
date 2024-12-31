package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.laurus.interfaces.NetworkData;

@AllArgsConstructor
@Getter
public enum IloObjectHealth implements NetworkData {
    OK, WARNING, CRITICAL, UNKNOWN;
}
