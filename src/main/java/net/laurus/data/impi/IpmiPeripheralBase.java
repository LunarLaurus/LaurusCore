package net.laurus.data.impi;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.UpdateInformation;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class IpmiPeripheralBase implements UpdateInformation<IpmiInfo> {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

}
