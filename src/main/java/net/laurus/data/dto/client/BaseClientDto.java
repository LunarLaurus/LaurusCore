package net.laurus.data.dto.client;

import java.io.Serializable;

import lombok.Value;
import lombok.extern.java.Log;
import net.laurus.data.dto.system.SystemInfo;
import net.laurus.data.dto.system.SystemInfoDto;
import net.laurus.data.dto.system.SystemIpmiInfo;
import net.laurus.data.enums.Vendor;
import net.laurus.data.enums.system.SystemArchitecture;
import net.laurus.data.impi.IpmiInfo;
import net.laurus.data.impi.ilo.IntegratedLightsOutInfo;
import net.laurus.interfaces.UpdateInformation;
import net.laurus.network.IPv4Address;
import net.laurus.util.NetworkUtil;

@Value
@Log
public class BaseClientDto implements UpdateInformation<SystemInfoDto>, Serializable {

    private String clientHostName;
    private SystemArchitecture arch;
    private Vendor vendor;
    private SystemInfo system;
    private SystemIpmiInfo ipmi;

    public BaseClientDto(SystemInfoDto systemInfoDTO) {
        this.clientHostName = systemInfoDTO.getSystemHostName();
        Vendor vendor =  Vendor.lookup(systemInfoDTO.getSystemModelName());
        this.arch = SystemArchitecture.fromValue(systemInfoDTO.getSystemArchitecture());
        log.info("Client Vendor: "+systemInfoDTO.getSystemModelName());
        boolean hasIpmi = !NetworkUtil.isZeroIPAddress(systemInfoDTO.getIpmiAddress());
        IpmiInfo possibleIpmi = null;
        this.system = SystemInfo.from(systemInfoDTO);
        if (hasIpmi) {        	
            possibleIpmi = new IntegratedLightsOutInfo(this.clientHostName, new IPv4Address(systemInfoDTO.getIpmiAddress()), 5);
            if (vendor.equals(Vendor.UNKNOWN)) {
            	vendor = Vendor.HPE;
            }
        }
        this.ipmi = new SystemIpmiInfo(hasIpmi, possibleIpmi);
        this.vendor = vendor;
        log.info("Created Client: " + clientHostName);
    }

    @Override
    public void update(SystemInfoDto systemInfoDTO) {
        system.update(systemInfoDTO);
        if (ipmi.isHasIpmi()) {
        	ipmi.getIpmiSystem().update(systemInfoDTO);
        }
    }

}
