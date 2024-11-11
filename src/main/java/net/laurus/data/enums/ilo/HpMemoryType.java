package net.laurus.data.enums.ilo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HpMemoryType implements Serializable {

    HPSMARTMEMORY("HP Smart Memory"), HPSTANDARD("HP Standard Memory"), UNKNOWN("Unknown");
    
    final String displayName;

}
