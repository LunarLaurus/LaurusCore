package net.laurus.data.impi.ilo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import net.laurus.data.enums.IpmiImplementation;
import net.laurus.data.impi.IpmiInfo;
import net.laurus.network.IPv4Address;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class IntegratedLightsOutInfo extends IpmiInfo {

    private final List<IloPeripheralBase> iloHardware = new CopyOnWriteArrayList<>();

    public IntegratedLightsOutInfo(String iloHost, IPv4Address iloAddress, int updateEveryX) {
        super(iloHost, iloAddress,IpmiImplementation.ILO, updateEveryX);
        log.info("Initialised ILO for client.");
    }
    
    @Override
    public void update(Object updateData) {
        if (iloHardware.isEmpty()) {
            
        }
        else {
            if (updateCounter != 0 && updateCounter % getUpdateIpmiEveryXUpdates() == 0) {            
                updateCounter = 0;
                for (var peripheral : iloHardware) {
                    peripheral.update(this);
                }
            }
        }
        
        updateCounter++;
    }    
    
}
