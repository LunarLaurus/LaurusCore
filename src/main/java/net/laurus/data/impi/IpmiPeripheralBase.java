package net.laurus.data.impi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.dto.system.UpdateInformation;
import net.laurus.data.impi.ilo.IntegratedLightsOutInfo;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class IpmiPeripheralBase implements UpdateInformation<IntegratedLightsOutInfo> {

}
