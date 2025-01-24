package net.laurus.data.enums;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.management.InvalidApplicationException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import net.laurus.data.enums.ilo.*;
import net.laurus.data.enums.system.*;

public class EnumTest {

    @Nested
    class IpmiImplementationTest {

        @Test
        void testCurrentlySupported() {
            assertTrue(IpmiImplementation.ILO.isCurrentlySupported(), "ILO should be currently supported");
            assertFalse(IpmiImplementation.DRAC.isCurrentlySupported(), "DRAC should not be currently supported");
            assertFalse(IpmiImplementation.UNKNOWN.isCurrentlySupported(), "UNKNOWN should not be currently supported");
        }

        @Test
        void testEnumValues() {
            IpmiImplementation[] values = IpmiImplementation.values();
            assertEquals(3, values.length, "There should be 3 enum values");
            assertEquals(IpmiImplementation.ILO, values[0]);
            assertEquals(IpmiImplementation.DRAC, values[1]);
            assertEquals(IpmiImplementation.UNKNOWN, values[2]);
        }

        @Test
        void testValueOf() {
            assertEquals(IpmiImplementation.ILO, IpmiImplementation.valueOf("ILO"));
            assertEquals(IpmiImplementation.DRAC, IpmiImplementation.valueOf("DRAC"));
            assertEquals(IpmiImplementation.UNKNOWN, IpmiImplementation.valueOf("UNKNOWN"));
        }
    }

    @Nested
    class VendorTest {

        @ParameterizedTest
        @MethodSource("provideValidVendorLookups")
        void testValidLookups(String input, Vendor expectedVendor) {
            assertEquals(expectedVendor, Vendor.lookup(input), "Vendor lookup should match for: " + input);
        }

        @ParameterizedTest
        @MethodSource("provideInvalidVendorLookups")
        void testInvalidLookups(String input) {
            assertEquals(Vendor.UNKNOWN, Vendor.lookup(input), "Vendor lookup should return UNKNOWN for: " + input);
        }

        static List<Arguments> provideValidVendorLookups() {
            return List.of(
                Arguments.of("HP ProLiant", Vendor.HPE),
                Arguments.of("Dell PowerEdge", Vendor.DELL),
                Arguments.of("Supermicro", Vendor.SUPERMICRO)
            );
        }

        static List<Arguments> provideInvalidVendorLookups() {
            return List.of(
                Arguments.of("Unknown Vendor"),
                Arguments.of("Random Model"),
                Arguments.of("")
            );
        }
    }

    @Nested
    class IloObjectStateTest {

        @Test
        void testEnumValues() {
            assertEquals("Enabled", IloObjectState.ENABLED.getName());
            assertEquals("Unknown", IloObjectState.UNKNOWN.getName());
        }
    }

    @Nested
    class IloObjectHealthTest {

        @Test
        void testEnumValues() {
            assertEquals(IloObjectHealth.OK, IloObjectHealth.valueOf("OK"));
            assertEquals(IloObjectHealth.CRITICAL, IloObjectHealth.valueOf("CRITICAL"));
        }
    }

    @Nested
    class DriveTypeTest {

        @Test
        void testFromString() {
            assertEquals(DriveType.REMOVABLE, DriveType.fromString("removable"));
            assertNull(DriveType.fromString("invalid"));
        }
    }

    @Nested
    class IloResetTypeTest {

        @Test
        void testGetValid() throws InvalidApplicationException {
            assertEquals(IloResetType.ON, IloResetType.get("On"));
        }

        @Test
        void testGetInvalid() {
            assertThrows(InvalidApplicationException.class, () -> IloResetType.get("Invalid"));
        }
    }

    @Nested
    class IloSensorLocationTest {

        @Test
        void testFromString() {
            assertEquals(IloSensorLocation.CPU, IloSensorLocation.fromString("CPU"));
            assertEquals(IloSensorLocation.UNKNOWN, IloSensorLocation.fromString("Invalid"));
        }
    }

    @Nested
    class IloChassisTypeTest {

        @Test
        void testGet() {
            assertEquals(IloChassisType.RACK, IloChassisType.get("Rack"));
            assertEquals(IloChassisType.UNKNOWN, IloChassisType.get("Invalid"));
        }
    }

    @Nested
    class IloBootSourceOverrideTargetTest {

        @Test
        void testGet() {
            assertEquals(IloBootSourceOverrideTarget.PXE, IloBootSourceOverrideTarget.get("Pxe"));
            assertEquals(IloBootSourceOverrideTarget.UNKNOWN, IloBootSourceOverrideTarget.get("Invalid"));
        }
    }

    @Nested
    class HpMemoryErrorCorrectionTest {

        @Test
        void testGet() {
            assertEquals(HpMemoryErrorCorrection.SINGLE_BIT_ECC, HpMemoryErrorCorrection.get("SINGLEBITECC"));
            assertEquals(HpMemoryErrorCorrection.NONE, HpMemoryErrorCorrection.get("Invalid"));
        }
    }

    @Nested
    class IloTemperatureUnitTest {

        @Test
        void testEnumValues() {
            assertEquals("C", IloTemperatureUnit.CELSIUS.getSymbol());
            assertEquals("?", IloTemperatureUnit.UNKNOWN.getSymbol());
        }
    }

    @Nested
    class IloFanUnitTest {

        @Test
        void testEnumValues() {
            assertEquals("RPM", IloFanUnit.RPM.getName());
            assertEquals("%", IloFanUnit.PERCENT.getName());
        }
    }

    @Nested
    class IloPowerAutoOnTest {

        @Test
        void testGet() {
            assertEquals(IloPowerAutoOn.POWER_ON, IloPowerAutoOn.get("PowerOn"));
            assertEquals(IloPowerAutoOn.UNKNOWN, IloPowerAutoOn.get("Invalid"));
        }
    }

    @Nested
    class IloSystemTypeTest {

        @Test
        void testGet() {
            assertEquals(IloSystemType.PHYSICAL, IloSystemType.get("Physical"));
            assertEquals(IloSystemType.UNKNOWN, IloSystemType.get("Invalid"));
        }
    }

    @Nested
    class IloTpmModuleTypeTest {

        @Test
        void testGet() {
            assertEquals(IloTpmModuleType.TPM2_0, IloTpmModuleType.get("TPM2.0"));
            assertEquals(IloTpmModuleType.UNKNOWN, IloTpmModuleType.get("Invalid"));
        }
    }

    @Nested
    class IloPostStateTest {

        @Test
        void testGet() {
            assertEquals(IloPostState.IN_POST, IloPostState.get("InPost"));
            assertEquals(IloPostState.UNKNOWN, IloPostState.get("Invalid"));
        }
    }
}
