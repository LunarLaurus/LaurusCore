package net.laurus.data.enums.ilo;

import java.io.Serializable;
import java.util.HashMap;

public enum HpMemoryErrorCorrection implements Serializable {

    NONE, PARITY, SINGLE_BIT_ECC, MULTI_BIT_ECC, CRC;
    
    private static final HashMap<HpMemoryErrorCorrection, String> nameMap = new HashMap<>();
    private static final HashMap<String, HpMemoryErrorCorrection> statusMap = new HashMap<>();

    public static HpMemoryErrorCorrection get(String ecc) {
        return statusMap.computeIfAbsent(ecc, o -> {
            for (HpMemoryErrorCorrection s : HpMemoryErrorCorrection.values()) {
                String name = nameMap.computeIfAbsent(s, p -> s.name().replaceAll("_", "").toUpperCase());
                if (name.equals(ecc.toUpperCase())) {
                    return s;
                }
            }
            return HpMemoryErrorCorrection.NONE;
        });

    }

}
