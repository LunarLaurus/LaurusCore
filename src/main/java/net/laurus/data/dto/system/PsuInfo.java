package net.laurus.data.dto.system;

import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.interfaces.NetworkData;
import net.laurus.interfaces.UpdateInformation;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PsuInfo implements UpdateInformation<PsuInfoDto> {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

	private String name;
	
	@Builder.Default
	private Map<String, Float> power = new HashMap<>();
	
	@Builder.Default
	private Map<String, Float> voltage = new HashMap<>();

	@Override
	public void update(PsuInfoDto updateData) {
		power = updateData.getPower();
		voltage = updateData.getVoltage();
	}

	public static PsuInfo from(String name, Map<String, Float> pwr, Map<String, Float> vltg) {
		return new PsuInfo(name, pwr, vltg);
	}

	public static PsuInfo from(PsuInfoDto updateData) {
		return new PsuInfo(updateData.getName(), updateData.getPower(), updateData.getVoltage());
	}

}
