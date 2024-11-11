package net.laurus.data.enums.ilo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Health implements Serializable {
    OK, WARNING, CRITICAL, UNKNOWN;
}
