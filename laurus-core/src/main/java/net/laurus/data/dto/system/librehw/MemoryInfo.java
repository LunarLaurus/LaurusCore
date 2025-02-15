package net.laurus.data.dto.system.librehw;

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
public class MemoryInfo implements UpdateInformation<MemoryInfoDto> {

	private static final long serialVersionUID = NetworkData.getCurrentVersionHash();

    private double used;

    private double available;

    private double load;


    @Override
    public void update(MemoryInfoDto updateData) {
    	
    }

    public static MemoryInfo from(double used, double available, double load) {        
        return new MemoryInfo(used, available, load);
    }

    public static MemoryInfo from(MemoryInfoDto updateData) { 
        return new MemoryInfo(updateData.getUsed(), updateData.getAvailable(), updateData.getLoad());
    }

}
