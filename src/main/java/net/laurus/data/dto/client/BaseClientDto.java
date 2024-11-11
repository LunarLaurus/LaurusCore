package net.laurus.data.dto.client;

import java.util.Optional;

import lombok.Value;
import lombok.extern.java.Log;
import net.laurus.data.dto.system.SystemInfo;
import net.laurus.data.dto.system.SystemInfoDto;
import net.laurus.data.dto.system.UpdateInformation;
import net.laurus.data.enums.Vendor;
import net.laurus.data.enums.system.SystemArchitecture;
import net.laurus.data.impi.IpmiInfo;
import net.laurus.data.impi.ilo.IntegratedLightsOutInfo;
import net.laurus.network.IPv4Address;

@Value
@Log
public class BaseClientDto implements UpdateInformation<SystemInfoDto> {

    private String clientHostName;
    private SystemArchitecture arch;
    private Vendor vendor;
    private boolean hasIpmi;
    private SystemInfo system;
    private Optional<IpmiInfo> ipmiSystem;

    public BaseClientDto(SystemInfoDto systemInfoDTO) {
        this.clientHostName = systemInfoDTO.getSystemHostName();
        Vendor vendor =  Vendor.lookup(systemInfoDTO.getSystemModelName());
        this.arch = SystemArchitecture.fromValue(systemInfoDTO.getSystemArchitecture());
        log.info("Client Vendor: "+systemInfoDTO.getSystemModelName());
        this.hasIpmi = !isZeroIPAddress(systemInfoDTO.getIpmiAddress());
        this.system = SystemInfo.from(systemInfoDTO);
        if (hasIpmi) {
            ipmiSystem = Optional.of(new IntegratedLightsOutInfo(this.clientHostName, new IPv4Address(systemInfoDTO.getIpmiAddress()), 5));
            if (vendor.equals(Vendor.UNKNOWN)) {
            	vendor = Vendor.HPE;
            }
        } else {
            ipmiSystem = Optional.empty();
        }
        this.vendor = vendor;
        log.info("Created Client: " + clientHostName);
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
