package net.laurus.data.enums;

import java.io.Serializable;
import java.util.HashMap;

public enum ErrorCorrection implements Serializable {

    NONE, PARITY, SINGLE_BIT_ECC, MULTI_BIT_ECC, CRC;
    
    private static final HashMap<ErrorCorrection, String> nameMap = new HashMap<>();
    private static final HashMap<String, ErrorCorrection> statusMap = new HashMap<>();

    public static ErrorCorrection get(String ecc) {
        return statusMap.computeIfAbsent(ecc, o -> {
            for (ErrorCorrection s : ErrorCorrection.values()) {
                String name = nameMap.computeIfAbsent(s, p -> s.name().replaceAll("_", "").toUpperCase());
                if (name.equals(ecc.toUpperCase())) {
                    return s;
                }
            }
            return ErrorCorrection.NONE;
        });

    }

}
