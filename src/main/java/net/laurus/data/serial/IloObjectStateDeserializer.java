package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.IloObjectState;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class IloObjectStateDeserializer extends EnumDeserializer<IloObjectState> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return IloObjectState.class;
    }

}