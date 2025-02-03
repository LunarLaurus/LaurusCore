package net.laurus.data.enums;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import net.laurus.interfaces.NetworkData;

import static java.util.List.of;

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

    List<String> alias;

    public static OperatingSystem lookup(String modelName) {
        for (OperatingSystem v : OperatingSystem.values()) {
            if (modelName.toLowerCase().contains(v.name().toLowerCase())) {
                return v;
            } else {
                for (String a : v.alias) {
                    if (modelName.toLowerCase().contains(a.toLowerCase())) {
                        return v;
                    }
                }
            }
        }
        log.info("Tried to find Vendor but was Unknown: " + modelName);
        return UNKNOWN;
    }
}
