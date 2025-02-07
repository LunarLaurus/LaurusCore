package net.laurus.data.enums;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import net.laurus.interfaces.NetworkData;

import static java.util.List.of;

/**
 * Enum representing different operating systems.
 */
@AllArgsConstructor
@Log
public enum OperatingSystem implements NetworkData {

    WINDOWS(of("Windows")),
    WINDOWS_SERVER(of("Windows server", "Win server")),
    DEBIAN(of("Debian")),
    UBUNTU(of("Ubuntu")),
    LINUX_OTHER(of("Linux", "linux os", "unix")),
    ESXI(of("Esxi", "Vmware Esxi", "Vmware")),
    PROXMOX(of("Proxmox", "Proxmox ve")),
    UNKNOWN(of());

    private static final long serialVersionUID = 0;
    private final List<String> alias;

    /**
     * Finds an OS type based on a given model name.
     *
     * @param modelName The model name.
     * @return The corresponding OperatingSystem or UNKNOWN if not found.
     */
    public static OperatingSystem lookup(String modelName) {
        modelName = modelName.toLowerCase();
        for (OperatingSystem os : values()) {
            if (modelName.contains(os.name().toLowerCase()) || os.alias.stream().anyMatch(modelName::contains)) {
                return os;
            }
        }
        log.info("OS not recognized: " + modelName);
        return UNKNOWN;
    }
}
