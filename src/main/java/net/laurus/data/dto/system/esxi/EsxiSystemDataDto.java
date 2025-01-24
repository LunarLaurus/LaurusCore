package net.laurus.data.dto.system.esxi;

import static net.laurus.Constant.JSON_MAPPER;

import java.io.IOException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EsxiSystemDataDto {

	private EsxiSystemDto system;
	private EsxiCpuDataDto cpu;

	public static EsxiSystemDataDto from(String jsonData) throws IOException {
		return JSON_MAPPER.readValue(jsonData, EsxiSystemDataDto.class);
	}

}
