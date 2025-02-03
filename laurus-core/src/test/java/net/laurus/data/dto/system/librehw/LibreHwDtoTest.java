package net.laurus.data.dto.system.librehw;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import net.laurus.Constant;

public class LibreHwDtoTest {

    @Test
    void testCpuDataDto() throws IOException {
        // Test JSON for CpuDataDto
        String testJson = """
            {
                "Temperatures": {
                    "Core 0": 60.5,
                    "Core 1": 61.0
                },
                "Load": {
                    "Core 0": 15.5,
                    "Core 1": 20.0
                },
                "ClockSpeeds": {
                    "Core 0": 3.5,
                    "Core 1": 3.6
                }
            }
            """;

        // Deserialize JSON into CpuDataDto
        CpuDataDto cpuData = Constant.JSON_MAPPER.readValue(testJson, CpuDataDto.class);

        // Assertions
        assertNotNull(cpuData, "CpuDataDto should not be null");
        assertEquals(60.5f, cpuData.getTemperatures().get("Core 0"));
        assertEquals(3.6f, cpuData.getClockSpeeds().get("Core 1"));
    }

    @Test
    void testCpuInfoDto() throws IOException {
        // Test JSON for CpuInfoDto
        String testJson = """
            {
                "CpuDataBySocket": {
                    "0": {
                        "Temperatures": {
                            "Core 0": 60.5
                        },
                        "Load": {
                            "Core 0": 15.5
                        },
                        "ClockSpeeds": {
                            "Core 0": 3.5
                        }
                    }
                },
                "CpuCount": 1,
                "CpuCoreCount": 2
            }
            """;

        // Deserialize JSON into CpuInfoDto
        CpuInfoDto cpuInfo = Constant.JSON_MAPPER.readValue(testJson, CpuInfoDto.class);

        // Assertions
        assertNotNull(cpuInfo, "CpuInfoDto should not be null");
        assertEquals(1, cpuInfo.getCpuCount());
        assertEquals(2, cpuInfo.getCpuCoreCount());
        assertNotNull(cpuInfo.getCpuDataBySocket(), "CpuDataBySocket should not be null");
    }

    @Test
    void testGpuInfoDto() throws IOException {
        // Test JSON for GpuInfoDto
        String testJson = """
            {
                "Name": "GPU 0",
                "Temperatures": {
                    "Core": 65.0
                },
                "Load": {
                    "Core": 75.5
                },
                "ClockSpeeds": {
                    "Core": 1200
                },
                "MemoryUsage": {
                    "Used": 4000
                }
            }
            """;

        // Deserialize JSON into GpuInfoDto
        GpuInfoDto gpuInfo = Constant.JSON_MAPPER.readValue(testJson, GpuInfoDto.class);

        // Assertions
        assertNotNull(gpuInfo, "GpuInfoDto should not be null");
        assertEquals("GPU 0", gpuInfo.getName());
        assertEquals(65.0f, gpuInfo.getTemperatures().get("Core"));
    }

    @Test
    void testMemoryInfoDto() throws IOException {
        // Test JSON for MemoryInfoDto
        String testJson = """
            {
                "Used": 8000,
                "Available": 16000,
                "Load": 50.0
            }
            """;

        // Deserialize JSON into MemoryInfoDto
        MemoryInfoDto memoryInfo = Constant.JSON_MAPPER.readValue(testJson, MemoryInfoDto.class);

        // Assertions
        assertNotNull(memoryInfo, "MemoryInfoDto should not be null");
        assertEquals(8000.0, memoryInfo.getUsed());
        assertEquals(50.0, memoryInfo.getLoad());
    }

    @Test
    void testPsuInfoDto() throws IOException {
        // Test JSON for PsuInfoDto
        String testJson = """
            {
                "Name": "PSU 0",
                "Power": {
                    "12V": 120.0
                },
                "Voltage": {
                    "12V": 12.0
                }
            }
            """;

        // Deserialize JSON into PsuInfoDto
        PsuInfoDto psuInfo = Constant.JSON_MAPPER.readValue(testJson, PsuInfoDto.class);

        // Assertions
        assertNotNull(psuInfo, "PsuInfoDto should not be null");
        assertEquals("PSU 0", psuInfo.getName());
        assertEquals(120.0f, psuInfo.getPower().get("12V"));
    }

    @Test
    void testStorageDriveInfoDto() throws IOException {
        // Test JSON for StorageDriveInfoDto
        String testJson = """
            {
                "Name": "Disk 1",
                "DriveType": "FIXED",
                "DriveFormat": "NTFS",
                "TotalSize": 1000000000000,
                "AvailableFreeSpace": 500000000000,
                "VolumeLabel": "My Drive"
            }
            """;

        // Deserialize JSON into StorageDriveInfoDto
        StorageDriveInfoDto driveInfo = Constant.JSON_MAPPER.readValue(testJson, StorageDriveInfoDto.class);

        // Assertions
        assertNotNull(driveInfo, "StorageDriveInfoDto should not be null");
        assertEquals("Disk 1", driveInfo.getName());
        assertEquals("NTFS", driveInfo.getDriveFormat());
    }

    @Test
    void testSystemInfoDto() throws IOException {
        // Test JSON for SystemInfoDto
        String testJson = """
            {
                "SystemArchitecture": 64,
                "SystemPlatform": "Windows",
                "SystemHostName": "Test-PC",
                "SystemModelName": "Test Model",
                "System64Bit": true,
                "DotNetProcess64Bit": true,
                "SystemUptime": 123456789,
                "SystemStorageInfo": [
                    {
                        "Name": "Disk 1",
                        "DriveType": "FIXED",
                        "DriveFormat": "NTFS",
                        "TotalSize": 1000000000000,
                        "AvailableFreeSpace": 500000000000,
                        "VolumeLabel": "My Drive"
                    }
                ],
                "CpuInfo": {
                    "CpuDataBySocket": {
                        "0": {
                            "Temperatures": {
                                "Core 0": 60.5
                            },
                            "Load": {
                                "Core 0": 15.5
                            },
                            "ClockSpeeds": {
                                "Core 0": 3.5
                            }
                        }
                    },
                    "CpuCount": 1,
                    "CpuCoreCount": 2
                },
                "Gpus": [
                    {
                        "Name": "GPU 0",
                        "Temperatures": {
                            "Core": 65.0
                        },
                        "Load": {
                            "Core": 75.5
                        },
                        "ClockSpeeds": {
                            "Core": 1200
                        }
                    }
                ],
                "Memory": {
                    "Used": 8000,
                    "Available": 16000,
                    "Load": 50.0
                },
                "PowerSupplies": [
                    {
                        "Name": "PSU 0",
                        "Power": {
                            "12V": 120.0
                        },
                        "Voltage": {
                            "12V": 12.0
                        }
                    }
                ]
            }
            """;

        // Deserialize JSON into SystemInfoDto
        SystemInfoDto systemInfo = Constant.JSON_MAPPER.readValue(testJson, SystemInfoDto.class);

        // Assertions
        assertNotNull(systemInfo, "SystemInfoDto should not be null");
        assertEquals("Test-PC", systemInfo.getSystemHostName());
        assertEquals(64, systemInfo.getSystemArchitecture());
    }
}
