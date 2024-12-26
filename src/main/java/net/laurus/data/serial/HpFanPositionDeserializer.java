package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.HpSensorPhysicalContext;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class HpFanPositionDeserializer extends EnumDeserializer<HpSensorPhysicalContext> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return HpSensorPhysicalContext.class;
    }

}