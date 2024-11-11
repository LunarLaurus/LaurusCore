package net.laurus.data.enums;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.util.List;

import static java.util.List.of;

@AllArgsConstructor
@Log
public enum Vendor {

    HPE(of("HP")),
    DELL(of()),
    SUPERMICRO(of()),
    UNKNOWN(of());
    
    List<String> alias;

    public static Vendor lookup(String modelName) {
        for (Vendor v : Vendor.values()) {
            if (modelName.toLowerCase().contains(v.name().toLowerCase())) {
                return v;
            }
            else {
                for (String a : v.alias) {
                    if (modelName.toLowerCase().contains(a.toLowerCase())) {
                        return v;
                    }
                }
            }
        }       
        log.info("Tried to find Vendor but was Unknown: "+modelName); 
        return UNKNOWN;
    }
    
}
