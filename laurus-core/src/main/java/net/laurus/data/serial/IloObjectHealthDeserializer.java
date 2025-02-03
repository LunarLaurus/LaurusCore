package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.IloObjectHealth;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class IloObjectHealthDeserializer extends EnumDeserializer<IloObjectHealth> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return IloObjectHealth.class;
    }

}