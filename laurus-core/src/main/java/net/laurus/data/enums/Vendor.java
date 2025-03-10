package net.laurus.data.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private static final Map<String, Vendor> aliasMap = initializeAliasMap();

    private static Map<String, Vendor> initializeAliasMap() {
        return java.util.Arrays.stream(values())
                .flatMap(vendor -> vendor.alias.stream().map(alias -> Map.entry(alias.toLowerCase(), vendor)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1)); // Keep first encountered
    }

    /**
     * Finds a vendor based on a given model name.
     *
     * @param modelName The model name.
     * @return The corresponding Vendor or UNKNOWN if not found.
     */
    public static Vendor lookup(String modelName) {
        if (modelName == null || modelName.trim().isEmpty()) {
            log.warning("Model name is null or empty.");
            return UNKNOWN;
        }

        String cleanedModelName = modelName.trim().toLowerCase();

        for (Vendor v : values()) {
            if (cleanedModelName.contains(v.name().toLowerCase())) {
                return v;
            }
        }

        Vendor aliasMatch = aliasMap.get(cleanedModelName);
        if (aliasMatch != null) {
            return aliasMatch;
        }

        for(String alias : aliasMap.keySet()){
            if(cleanedModelName.contains(alias)){
                return aliasMap.get(alias);
            }
        }

        log.info("Vendor not recognized: " + modelName);
        return UNKNOWN;
    }
}