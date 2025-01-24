package net.laurus.data.dto.system.esxi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EsxiSystemDataDtoTest {

	private static final String TEST_JSON = """
			{"system":{"hostname":"falador.guth.ix","vmwareVersion":"VMware ESXi 7.0.3 build-23794027","kernelVersion":"7.0.3","biosVersion":"P89 (3.40)","hostModel":"ProLiant DL380 Gen9","managementIp":"192.168.0.11","uptimeSeconds":2949793484323,"totalMemoryBytes":68583206912},"cpu":{ "info": {"sockets":2,"coresPerSocket":10,"threadsPerCore":2,"logicalProcessors":40,"cpuUsagePercentage":0,"tjmax":90}, "data": [{"coreId":0,"physicalCoreId":0,"socketId":0,"temperature":36,"digitalReadout":54,"virtualThread":false},{"coreId":1,"physicalCoreId":0,"socketId":0,"temperature":36,"digitalReadout":54,"virtualThread":true},{"coreId":2,"physicalCoreId":1,"socketId":0,"temperature":35,"digitalReadout":55,"virtualThread":false},{"coreId":3,"physicalCoreId":1,"socketId":0,"temperature":36,"digitalReadout":54,"virtualThread":true},{"coreId":4,"physicalCoreId":2,"socketId":0,"temperature":35,"digitalReadout":55,"virtualThread":false},{"coreId":5,"physicalCoreId":2,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":6,"physicalCoreId":3,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":7,"physicalCoreId":3,"socketId":0,"temperature":35,"digitalReadout":55,"virtualThread":true},{"coreId":8,"physicalCoreId":4,"socketId":0,"temperature":33,"digitalReadout":57,"virtualThread":false},{"coreId":9,"physicalCoreId":4,"socketId":0,"temperature":36,"digitalReadout":54,"virtualThread":true},{"coreId":10,"physicalCoreId":5,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":11,"physicalCoreId":5,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":12,"physicalCoreId":6,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":13,"physicalCoreId":6,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":14,"physicalCoreId":7,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":15,"physicalCoreId":7,"socketId":0,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":16,"physicalCoreId":8,"socketId":0,"temperature":33,"digitalReadout":57,"virtualThread":false},{"coreId":17,"physicalCoreId":8,"socketId":0,"temperature":33,"digitalReadout":57,"virtualThread":true},{"coreId":18,"physicalCoreId":9,"socketId":0,"temperature":33,"digitalReadout":57,"virtualThread":false},{"coreId":19,"physicalCoreId":9,"socketId":0,"temperature":33,"digitalReadout":57,"virtualThread":true},{"coreId":20,"physicalCoreId":10,"socketId":1,"temperature":33,"digitalReadout":57,"virtualThread":false},{"coreId":21,"physicalCoreId":10,"socketId":1,"temperature":33,"digitalReadout":57,"virtualThread":true},{"coreId":22,"physicalCoreId":11,"socketId":1,"temperature":33,"digitalReadout":57,"virtualThread":false},{"coreId":23,"physicalCoreId":11,"socketId":1,"temperature":33,"digitalReadout":57,"virtualThread":true},{"coreId":24,"physicalCoreId":12,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":25,"physicalCoreId":12,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":26,"physicalCoreId":13,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":27,"physicalCoreId":13,"socketId":1,"temperature":33,"digitalReadout":57,"virtualThread":true},{"coreId":28,"physicalCoreId":14,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":29,"physicalCoreId":14,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":30,"physicalCoreId":15,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":31,"physicalCoreId":15,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":32,"physicalCoreId":16,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":33,"physicalCoreId":16,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":true},{"coreId":34,"physicalCoreId":17,"socketId":1,"temperature":34,"digitalReadout":56,"virtualThread":false},{"coreId":35,"physicalCoreId":17,"socketId":1,"temperature":35,"digitalReadout":55,"virtualThread":true},{"coreId":36,"physicalCoreId":18,"socketId":1,"temperature":32,"digitalReadout":58,"virtualThread":false},{"coreId":37,"physicalCoreId":18,"socketId":1,"temperature":32,"digitalReadout":58,"virtualThread":true},{"coreId":38,"physicalCoreId":19,"socketId":1,"temperature":32,"digitalReadout":58,"virtualThread":false},{"coreId":39,"physicalCoreId":19,"socketId":1,"temperature":32,"digitalReadout":58,"virtualThread":true}] }}
			""";

	@Test
	void testDeserializationAgainstRealSystem() throws IOException {
		// Deserialize JSON to DTO
		EsxiSystemDataDto systemData = EsxiSystemDataDto.from(TEST_JSON);

		// Assertions for "system" section
		assertNotNull(systemData.getSystem(), "System data should not be null");
		assertEquals("falador.guth.ix", systemData.getSystem().getHostname());
		assertEquals(68583206912L, systemData.getSystem().getTotalMemoryBytes());
		assertEquals(2949793484323L, systemData.getSystem().getUptimeSeconds());

		// Assertions for "cpu.info" section
		assertNotNull(systemData.getCpu(), "CPU data should not be null");
		assertNotNull(systemData.getCpu().getInfo(), "CPU info should not be null");
		assertEquals(2, systemData.getCpu().getInfo().getSockets());
		assertEquals(10, systemData.getCpu().getInfo().getCoresPerSocket());
		assertEquals(40, systemData.getCpu().getInfo().getLogicalProcessors());

		// Assertions for individual CPU cores
		assertNotNull(systemData.getCpu().getData(), "CPU core data should not be null");
		assertEquals(40, systemData.getCpu().getData().size(), "CPU core data size should match the real system");
		assertEquals(0, systemData.getCpu().getData().get(0).getCoreId());
		assertEquals(36, systemData.getCpu().getData().get(0).getTemperature());
	}

	@Test
	void testStaticTestSystem() {
		// Create a static test system
		EsxiSystemDto system = EsxiSystemDto.builder().hostname("test-system.local")
				.vmwareVersion("VMware ESXi 8.0.0 build-12345678").kernelVersion("8.0.0").biosVersion("P100 (4.00)")
				.hostModel("Test System Gen10").managementIp("192.168.1.100").uptimeSeconds(123456789L)
				.totalMemoryBytes(128849018880L).build();

		EsxiCpuInfoDto cpuInfo = EsxiCpuInfoDto.builder().sockets(1).coresPerSocket(16).threadsPerCore(2)
				.logicalProcessors(32).cpuUsagePercentage(5).tjmax(95).build();

		EsxiCpuCoreDto core = EsxiCpuCoreDto.builder().coreId(0).physicalCoreId(0).socketId(0).temperature(40)
				.digitalReadout(60).virtualThread(false).build();

		EsxiCpuDataDto cpuData = EsxiCpuDataDto.builder().info(cpuInfo).data(List.of(core)).build();

		EsxiSystemDataDto systemData = EsxiSystemDataDto.builder().system(system).cpu(cpuData).build();

		// Assertions
		assertEquals("test-system.local", systemData.getSystem().getHostname());
		assertEquals(128849018880L, systemData.getSystem().getTotalMemoryBytes());
		assertEquals(32, systemData.getCpu().getInfo().getLogicalProcessors());
	}

	@Test
	void testCustomBuilderInstances() {
		// Create a custom EsxiSystemDataDto instance with different configurations
		EsxiSystemDto system = EsxiSystemDto.builder().hostname("custom-system.local")
				.vmwareVersion("VMware ESXi 7.1.0 build-98765432").kernelVersion("7.1.0")
				.biosVersion("Custom BIOS (1.00)").hostModel("Custom Model X").managementIp("10.0.0.50")
				.uptimeSeconds(987654321L).totalMemoryBytes(34359738368L).build();

		EsxiCpuInfoDto cpuInfo = EsxiCpuInfoDto.builder().sockets(2).coresPerSocket(12).threadsPerCore(2)
				.logicalProcessors(48).cpuUsagePercentage(15).tjmax(100).build();

		EsxiCpuCoreDto core0 = EsxiCpuCoreDto.builder().coreId(0).physicalCoreId(0).socketId(0).temperature(42)
				.digitalReadout(62).virtualThread(false).build();

		EsxiCpuDataDto cpuData = EsxiCpuDataDto.builder().info(cpuInfo).data(List.of(core0)).build();

		EsxiSystemDataDto systemData = EsxiSystemDataDto.builder().system(system).cpu(cpuData).build();

		// Assertions
		assertEquals("custom-system.local", systemData.getSystem().getHostname());
		assertEquals(48, systemData.getCpu().getInfo().getLogicalProcessors());
		assertEquals(42, systemData.getCpu().getData().get(0).getTemperature());
	}
}
