package net.laurus.data.impi;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.enums.IpmiImplementation;
import net.laurus.interfaces.UpdateInformation;
import net.laurus.network.IPv4Address;


@Getter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public abstract class IpmiInfo implements UpdateInformation<Object>, Serializable {

    private final String parentHostName;
    private final IPv4Address address;
    private final IpmiImplementation type;
    private final int updateIpmiEveryXUpdates;
    protected int updateCounter = 0;
    private final List<IpmiPeripheralBase> ipmiHardware = new CopyOnWriteArrayList<>();

}
