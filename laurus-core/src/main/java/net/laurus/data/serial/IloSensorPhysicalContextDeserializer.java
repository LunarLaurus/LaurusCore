package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.IloSensorPhysicalContext;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class IloSensorPhysicalContextDeserializer extends EnumDeserializer<IloSensorPhysicalContext> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return IloSensorPhysicalContext.class;
    }

}