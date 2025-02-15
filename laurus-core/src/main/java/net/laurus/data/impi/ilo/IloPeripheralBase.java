package net.laurus.data.impi.ilo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.impi.IpmiInfo;
import net.laurus.data.impi.IpmiPeripheralBase;
import net.laurus.interfaces.NetworkData;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class IloPeripheralBase extends IpmiPeripheralBase {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
	@Override
	public final void update(IpmiInfo updateData) {
		update((IntegratedLightsOutInfo) updateData);
	}
	
	public abstract void update(IntegratedLightsOutInfo updateData);

}
