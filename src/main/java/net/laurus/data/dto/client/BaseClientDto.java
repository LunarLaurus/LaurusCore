package net.laurus.data.dto.client;

import java.util.Optional;

import lombok.Value;
import lombok.extern.java.Log;
import net.laurus.data.dto.system.SystemInfo;
import net.laurus.data.dto.system.SystemInfoDto;
import net.laurus.data.dto.system.UpdateInformation;
import net.laurus.data.enums.Vendor;
import net.laurus.data.impi.IpmiInfo;
import net.laurus.data.impi.ilo.IntegratedLightsOutInfo;
import net.laurus.network.IPv4Address;

@Value
@Log
public class BaseClientDto implements UpdateInformation<SystemInfoDto> {

    private String name;
    private Vendor vendor;
    private boolean hasIpmi;
    private SystemInfo system;
    private Optional<IpmiInfo> ipmiSystem;

    public BaseClientDto(SystemInfoDto systemInfoDTO) {
        this.name = systemInfoDTO.getClientName();
        Vendor vendor =  Vendor.lookup(systemInfoDTO.getModelName());
        log.info("Client Vendor: "+systemInfoDTO.getModelName());
        this.hasIpmi = !isZeroIPAddress(systemInfoDTO.getIloAddress());
        this.system = SystemInfo.from(systemInfoDTO);
        if (hasIpmi) {
            ipmiSystem = Optional.of(new IntegratedLightsOutInfo(this.name, new IPv4Address(systemInfoDTO.getIloAddress()), 5));
            if (vendor.equals(Vendor.UNKNOWN)) {
            	vendor = Vendor.HPE;
            }
        } else {
            ipmiSystem = Optional.empty();
        }
        this.vendor = vendor;
        log.info("Created Client: " + name);
    }

    @Override
    public void update(SystemInfoDto systemInfoDTO) {
        system.update(systemInfoDTO);
        ipmiSystem.ifPresent(t -> t.update(null));
    }

    private static boolean isZeroIPAddress(String ipAddress) {
        if (ipAddress == null) {
            return true;
        }
        return ipAddress.trim().equals("0.0.0.0");
    }

}
