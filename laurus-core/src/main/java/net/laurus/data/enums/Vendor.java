package net.laurus.data.enums;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import net.laurus.interfaces.NetworkData;

import static java.util.List.of;

/**
 * Enum representing different hardware vendors.
 */
@AllArgsConstructor
@Log
public enum Vendor implements NetworkData {

    HPE(of("HP", "HPE")),
    DELL(of("DELL")),
    SUPERMICRO(of()),
    UNKNOWN(of());

    private static final long serialVersionUID = 0;
    private final List<String> alias;

    /**
     * Finds a vendor based on a given model name.
     *
     * @param modelName The model name.
     * @return The corresponding Vendor or UNKNOWN if not found.
     */
    public static Vendor lookup(String modelName) {
        modelName = modelName.toLowerCase();
        for (Vendor v : values()) {
            if (modelName.contains(v.name().toLowerCase()) || v.alias.stream().anyMatch(modelName::contains)) {
                return v;
            }
        }
        log.info("Vendor not recognized: " + modelName);
        return UNKNOWN;
    }
}
