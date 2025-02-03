package net.laurus.data.dto.system.lmsensors;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import net.laurus.Constant;

public class LMSensorsDtoTest {

	@Test
	void testComponentInfoDto() {
		ComponentInfoDto component = new ComponentInfoDto("CPU Temperature", 45.5f, 70.0f, 90.0f);

		assertNotNull(component, "ComponentInfoDto should not be null");
		assertEquals("CPU Temperature", component.getLabel());
		assertEquals(45.5f, component.getTemperature());
		assertEquals(70.0f, component.getMaxTemperature());
		assertEquals(90.0f, component.getCriticalTemperature());
	}

	@Test
	void testDiskInfoDto() {
		DiskInfoDto disk = new DiskInfoDto("Disk 1", 500000000000L, 300000000000L, 1024000000L, 2048000000L);

		assertNotNull(disk, "DiskInfoDto should not be null");
		assertEquals("Disk 1", disk.getName());
		assertEquals(500000000000L, disk.getTotalSpace());
		assertEquals(300000000000L, disk.getAvailableSpace());
		assertEquals(1024000000L, disk.getReadBytes());
		assertEquals(2048000000L, disk.getWrittenBytes());
	}

	@Test
	void testMemoryInfoDto() {
		MemoryInfoDto memory = new MemoryInfoDto(16000000000L, 8000000000L, 8000000000L, 4000000000L);

		assertNotNull(memory, "MemoryInfoDto should not be null");
		assertEquals(16000000000L, memory.getTotal());
		assertEquals(8000000000L, memory.getUsed());
		assertEquals(8000000000L, memory.getTotalSwap());
		assertEquals(4000000000L, memory.getUsedSwap());
	}

	@Test
	void testNetworkInfoDto() {
		NetworkInfoDto network = new NetworkInfoDto("eth0", 1000000000L, 500000000L, 1500L);

		assertNotNull(network, "NetworkInfoDto should not be null");
		assertEquals("eth0", network.getInterfaceName());
		assertEquals(1000000000L, network.getReceived());
		assertEquals(500000000L, network.getTransmitted());
		assertEquals(1500L, network.getMtu());
	}

	@Test
	void testProcessInfoDto() {
		ProcessInfo process = new ProcessInfo("java", 1234, 2048000L);

		assertNotNull(process, "ProcessInfo should not be null");
		assertEquals("java", process.getName());
		assertEquals(1234, process.getPid());
		assertEquals(2048000L, process.getMemory());
	}

	@Test
	void testSystemUptimeDto() {
		SystemUptimeDto uptime = new SystemUptimeDto(2, 5, 30, 45, 183645L);

		assertNotNull(uptime, "SystemUptimeDto should not be null");
		assertEquals(2, uptime.getDays());
		assertEquals(5, uptime.getHours());
		assertEquals(30, uptime.getMinutes());
		assertEquals(45, uptime.getSeconds());
		assertEquals(183645L, uptime.getTotalSeconds());
	}

	@Test
	void testRustClientDataDtoDeserialization() throws IOException {
		String testJson = """
				{
				  "system_info": {
				    "hostname": "test-system",
				    "uptime": {
				      "days": 2,
				      "hours": 5,
				      "minutes": 30,
				      "seconds": 45,
				      "total_seconds": 183645
				    },
				    "management_ip": "192.168.0.50"
				  },
				  "cpu_info": {
				    "usage_per_core": [10.5, 20.0, 30.2],
				    "core_count": 3,
				    "cpu_arch": "x86_64"
				  },
				  "cpu_packages": [
				    {
				      "package_id": "0",
				      "adapter_name": "Adapter 1",
				      "package_temperature": 65.5,
				      "high_threshold": 80.0,
				      "critical_threshold": 95.0,
				      "cores": [
				        {
				          "core_name": "Core 0",
				          "temperature": 60.0,
				          "high_threshold": 80.0,
				          "critical_threshold": 95.0
				        }
				      ]
				    }
				  ],
				  "memory_info": {
				    "total": 16000000000,
				    "used": 8000000000,
				    "total_swap": 8000000000,
				    "used_swap": 4000000000
				  },
				  "disks": [
				    {
				      "name": "Disk 1",
				      "total_space": 500000000000,
				      "available_space": 300000000000,
				      "read_bytes": 1024000000,
				      "written_bytes": 2048000000
				    }
				  ],
				  "network_interfaces": [
				    {
				      "interface_name": "eth0",
				      "received": 1000000000,
				      "transmitted": 500000000,
				      "mtu": 1500
				    }
				  ],
				  "components": [
				    {
				      "label": "CPU Temperature",
				      "temperature": 45.5,
				      "max_temperature": 70.0,
				      "critical_temperature": 90.0
				    }
				  ]
				}
				""";

		// Deserialize the updated JSON
		RustClientData rustClientData = RustClientData.from(testJson);

		// Assertions for deserialized data
		assertNotNull(rustClientData, "RustClientData should not be null");

		// Validate system info
		SystemInfoDto systemInfo = rustClientData.getSystemInfo();
		assertNotNull(systemInfo, "SystemInfo should not be null");
		assertEquals("test-system", systemInfo.getHostname());
		assertEquals("192.168.0.50", systemInfo.getManagementIp());

		// Validate uptime
		SystemUptimeDto uptime = systemInfo.getUptime();
		assertNotNull(uptime, "Uptime should not be null");
		assertEquals(2, uptime.getDays());
		assertEquals(5, uptime.getHours());
		assertEquals(30, uptime.getMinutes());
		assertEquals(45, uptime.getSeconds());
		assertEquals(183645L, uptime.getTotalSeconds());

		// Validate CPU info
		RustClientData.CpuInfoDto cpuInfo = rustClientData.getCpuInfo();
		assertNotNull(cpuInfo, "CPU info should not be null");
		assertEquals(3, cpuInfo.getCoreCount());
		assertEquals("x86_64", cpuInfo.getCpuArch());
		assertEquals(List.of(10.5f, 20.0f, 30.2f), cpuInfo.getUsagePerCore());

		// Validate memory info
		MemoryInfoDto memoryInfo = rustClientData.getMemoryInfo();
		assertNotNull(memoryInfo, "Memory info should not be null");
		assertEquals(16000000000L, memoryInfo.getTotal());
		assertEquals(8000000000L, memoryInfo.getUsed());
		assertEquals(8000000000L, memoryInfo.getTotalSwap());
		assertEquals(4000000000L, memoryInfo.getUsedSwap());

		// Validate disks
		assertNotNull(rustClientData.getDisks(), "Disks should not be null");
		assertEquals(1, rustClientData.getDisks().size());
		DiskInfoDto disk = rustClientData.getDisks().get(0);
		assertEquals("Disk 1", disk.getName());
		assertEquals(500000000000L, disk.getTotalSpace());
		assertEquals(300000000000L, disk.getAvailableSpace());
		assertEquals(1024000000L, disk.getReadBytes());
		assertEquals(2048000000L, disk.getWrittenBytes());

		// Validate network interfaces
		assertNotNull(rustClientData.getNetworkInterfaces(), "Network interfaces should not be null");
		assertEquals(1, rustClientData.getNetworkInterfaces().size());
		NetworkInfoDto network = rustClientData.getNetworkInterfaces().get(0);
		assertEquals("eth0", network.getInterfaceName());
		assertEquals(1000000000L, network.getReceived());
		assertEquals(500000000L, network.getTransmitted());
		assertEquals(1500L, network.getMtu());

		// Validate components
		assertNotNull(rustClientData.getComponents(), "Components should not be null");
		assertEquals(1, rustClientData.getComponents().size());
		ComponentInfoDto component = rustClientData.getComponents().get(0);
		assertEquals("CPU Temperature", component.getLabel());
		assertEquals(45.5f, component.getTemperature());
		assertEquals(70.0f, component.getMaxTemperature());
		assertEquals(90.0f, component.getCriticalTemperature());
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "{}", "invalid-json" })
	void testRustClientDataDtoInvalidDeserialization(String invalidJson) {
		if (invalidJson.equals("{}")) {
			// Handle the valid but empty JSON scenario
			RustClientData data = assertDoesNotThrow(() -> RustClientData.from(invalidJson));
			assertNotNull(data, "Deserialized RustClientData should not be null");

			// Check all fields are null
			assertNull(data.getSystemInfo(), "SystemInfo should be null for empty JSON");
			assertNull(data.getCpuInfo(), "CPU info should be null for empty JSON");
			assertNull(data.getCpuData(), "CPU data should be null for empty JSON");
			assertNull(data.getMemoryInfo(), "Memory info should be null for empty JSON");
			assertNull(data.getDisks(), "Disks should be null for empty JSON");
			assertNull(data.getNetworkInterfaces(), "Network interfaces should be null for empty JSON");
			assertNull(data.getComponents(), "Components should be null for empty JSON");
		} else {
			// Handle invalid JSON scenarios
			assertThrows(IOException.class, () -> RustClientData.from(invalidJson),
					"Expected an IOException for invalid JSON");
		}
	}

	@Test
	void testRustClientDataDtoSerialization() throws IOException {
		RustClientData.CpuInfoDto cpuInfo = new RustClientData.CpuInfoDto(List.of(10.5f, 20.0f, 30.2f), 3, "x86_64");
		SystemUptimeDto uptime = new SystemUptimeDto(2, 5, 30, 45, 183645L);
		SystemInfoDto systemInfo = new SystemInfoDto("test-system", uptime, "192.168.0.50");

		RustClientData.CpuPackageDataDto.CpuCoreDataDto core = new RustClientData.CpuPackageDataDto.CpuCoreDataDto(
				"Core 0", 60.0f, 80.0f, 95.0f);
		RustClientData.CpuPackageDataDto packageData = new RustClientData.CpuPackageDataDto("0", "Adapter 1", 65.5f,
				80.0f, 95.0f, List.of(core));
		RustClientData rustClientData = new RustClientData(systemInfo, cpuInfo, List.of(packageData), null, null, null,
				null);

		String json = Constant.JSON_MAPPER.writeValueAsString(rustClientData);

		assertNotNull(json, "Serialized JSON should not be null");
		System.out.println("Serialized JSON: " + json);
	}
}
