package net.laurus.data.dto.system.lmsensors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.laurus.Constant;

public class LMSensorsDtoTest {

    @Test
    void testComponentInfoDto() {
        // Create and validate ComponentInfoDto
        ComponentInfoDto component = new ComponentInfoDto("CPU Temperature", 45.5f, 70.0f, 90.0f);
        assertNotNull(component, "ComponentInfoDto should not be null");
        assertEquals("CPU Temperature", component.getLabel());
        assertEquals(45.5f, component.getTemperature());
    }

    @Test
    void testDiskInfoDto() {
        // Create and validate DiskInfoDto
        DiskInfoDto disk = new DiskInfoDto("Disk 1", 500000000000L, 300000000000L, 1024000000L, 2048000000L);
        assertNotNull(disk, "DiskInfoDto should not be null");
        assertEquals("Disk 1", disk.getName());
        assertEquals(500000000000L, disk.getTotalSpace());
    }

    @Test
    void testMemoryInfoDto() {
        // Create and validate MemoryInfoDto
        MemoryInfoDto memory = new MemoryInfoDto(16000000000L, 8000000000L, 8000000000L, 4000000000L);
        assertNotNull(memory, "MemoryInfoDto should not be null");
        assertEquals(16000000000L, memory.getTotal());
        assertEquals(8000000000L, memory.getUsed());
    }

    @Test
    void testNetworkInfoDto() {
        // Create and validate NetworkInfoDto
        NetworkInfoDto network = new NetworkInfoDto("eth0", 1000000000L, 500000000L, 1500L);
        assertNotNull(network, "NetworkInfoDto should not be null");
        assertEquals("eth0", network.getInterfaceName());
        assertEquals(1500L, network.getMtu());
    }

    @Test
    void testProcessInfoDto() {
        // Create and validate ProcessInfo
        ProcessInfo process = new ProcessInfo("java", 1234, 2048000L);
        assertNotNull(process, "ProcessInfo should not be null");
        assertEquals("java", process.getName());
        assertEquals(1234, process.getPid());
    }

    @Test
    void testSystemUptimeDto() {
        // Create and validate SystemUptimeDto
        SystemUptimeDto uptime = new SystemUptimeDto(2, 5, 30, 45, 183645L);
        assertNotNull(uptime, "SystemUptimeDto should not be null");
        assertEquals(183645L, uptime.getTotalSeconds());
    }

    @Test
    void testRustClientDataDtoDeserialization() throws IOException {
        // Test JSON for RustClientData
        String testJson = """
            {
              "uptime": {
                "days": 2,
                "hours": 5,
                "minutes": 30,
                "seconds": 45,
                "total_seconds": 183645
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

        // Deserialize RustClientData from JSON
        RustClientData rustClientData = RustClientData.from(testJson);

        // Assertions for deserialized data
        assertNotNull(rustClientData, "RustClientData should not be null");
        assertNotNull(rustClientData.getUptime(), "Uptime should not be null");
        assertEquals(183645L, rustClientData.getUptime().getTotalSeconds());

        assertNotNull(rustClientData.getCpuInfo(), "CPU info should not be null");
        assertEquals(3, rustClientData.getCpuInfo().getCoreCount());
        assertEquals("x86_64", rustClientData.getCpuInfo().getCpuArch());

        assertNotNull(rustClientData.getMemoryInfo(), "Memory info should not be null");
        assertEquals(16000000000L, rustClientData.getMemoryInfo().getTotal());
    }

    @Test
    void testRustClientDataDtoSerialization() throws IOException {
        // Create and serialize RustClientData
        RustClientData.CpuInfoDto cpuInfo = new RustClientData.CpuInfoDto(List.of(10.5f, 20.0f, 30.2f), 3, "x86_64");
        SystemUptimeDto uptime = new SystemUptimeDto(2, 5, 30, 45, 183645L);

        RustClientData.CpuPackageDataDto.CpuCoreDataDto core = new RustClientData.CpuPackageDataDto.CpuCoreDataDto(
            "Core 0", 60.0f, 80.0f, 95.0f);

        RustClientData.CpuPackageDataDto packageData = new RustClientData.CpuPackageDataDto(
            "0", "Adapter 1", 65.5f, 80.0f, 95.0f, List.of(core));

        RustClientData rustClientData = new RustClientData(
            uptime, cpuInfo, List.of(packageData), null, null, null, null);

        String json = Constant.JSON_MAPPER.writeValueAsString(rustClientData);

        assertNotNull(json, "Serialized JSON should not be null");
        System.out.println("Serialized JSON: " + json);
    }
}
