package net.laurus.data.dto.ipmi.ilo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import net.laurus.data.impi.IpmiPeripheralBase;

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class IloPeripheralBase extends IpmiPeripheralBase {

}
