package net.laurus.data.dto.system;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.interfaces.UpdateInformation;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SystemInfo implements UpdateInformation<SystemInfoDto> {

	CpuInfo cpu;

	private SystemInfo(CpuInfo cpu) {
		this.cpu = cpu;
	}

	public static SystemInfo from(SystemInfoDto system) {
		CpuInfoDto cpu = system.getCpuInfoDto();
		CpuInfo cpuInfo = CpuInfo.from(cpu);
		return new SystemInfo(cpuInfo);
	}

	@Override
	public void update(SystemInfoDto systemInfoDTO) {
		// Update CPU data
		cpu.update(systemInfoDTO.getCpuInfoDto());
	}

}
