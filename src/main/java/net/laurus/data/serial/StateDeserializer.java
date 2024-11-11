package net.laurus.data.serial;

import lombok.NoArgsConstructor;
import net.laurus.data.enums.ilo.State;
import net.laurus.util.EnumDeserializer;

@NoArgsConstructor
public class StateDeserializer extends EnumDeserializer<State> {

    @Override
    protected Class<? extends Enum<?>> getEnumType() {
        return State.class;
    }

}