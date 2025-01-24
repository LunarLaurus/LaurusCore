package net.laurus.data.dto.system.librehw;

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
@FieldDefaults(level=AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CpuInfo implements UpdateInformation<CpuInfoDto> {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();
	
    private int cpuCount;
    private int cpuCoreCount;
    private Map<Integer, CpuDataDto> cpuDataBySocket;

    @Override
    public void update(CpuInfoDto updateData) {
        this.cpuDataBySocket.clear(); 
        int index = 0;
        for (var f : cpuDataBySocket.values()) {
        	cpuDataBySocket.put(index++, f);
        }
    }

    public static CpuInfo from(int cpuId, int coreCount) {        
        return new CpuInfo(cpuId, coreCount, new HashMap<>());
    }

    public static CpuInfo from(CpuInfoDto dto) { 
        return new CpuInfo(dto.getCpuCount(), dto.getCpuCoreCount(), dto.getCpuDataBySocket());
    }

}
