package net.laurus.data.enums.ilo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HpMemoryType {

    HPSMARTMEMORY("HP Smart Memory"), HPSTANDARD("HP Standard Memory"), UNKNOWN("Unknown");
    
    final String displayName;

}
