package net.laurus.data.dto.system.librehw;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonDeserialize(as = CpuInfoDto.class)
public class CpuInfoDto {

	@JsonAlias("CpuDataBySocket")
	private Map<Integer, CpuDataDto> cpuDataBySocket;

	@JsonAlias("CpuCount")
	private int cpuCount;

	@JsonAlias("CpuCoreCount")
	private int cpuCoreCount;

	public CpuDataDto getCpuDataBySocket(int socket) {
		return cpuDataBySocket.get(socket);
	}
}
