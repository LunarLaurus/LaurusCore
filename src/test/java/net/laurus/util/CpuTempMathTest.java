package net.laurus.util;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Map;

class CpuTempMathTest {

    @Test
    void testCountTotalCores() {
        Map<String, Map<String, Float>> data = Map.of(
                "CPU 0", Map.of("CPU Core #1", 30.5f, "CPU Core #2", 31.0f),
                "CPU 1", Map.of("CPU Core #1", 32.0f, "CPU Core #2", 33.0f)
        );

        int totalCores = CpuTempMath.countTotalCores(data);
        assertEquals(4, totalCores, "Should count all cores across CPUs.");
    }

    @Test
    void testCountCoresForSingleCpu() {
        Map<String, Float> data = Map.of(
                "CPU Core #1", 30.5f,
                "CPU Core #2", 31.0f
        );

        int coreCount = CpuTempMath.countCoresForSingleCpu(data);
        assertEquals(2, coreCount, "Should count all cores for a single CPU.");
    }

    @Test
    void testGetCpuPackageTemperature() {
        Map<String, Float> data = Map.of(
                "CPU Package", 70.5f,
                "Core Max", 75.0f
        );

        float packageTemp = CpuTempMath.getCpuPackageTemperature(data);
        assertEquals(70.5f, packageTemp, "Should return the correct CPU package temperature.");
    }

    @Test
    void testGetCoreTemperatures() {
        Map<String, Float> data = Map.of(
                "CPU Core #1", 30.5f,
                "CPU Core #2", 31.0f
        );

        Map<Integer, Float> coreTemps = CpuTempMath.getCoreTemperatures(data);

        assertEquals(30.5f, coreTemps.get(1), "Should map core #1 to its temperature.");
        assertEquals(31.0f, coreTemps.get(2), "Should map core #2 to its temperature.");
    }

    @Test
    void testGetCoreTemperatures_InvalidCoreKeys() {
        Map<String, Float> data = Map.of(
                "CPU Core A", 30.5f,
                "CPU Core #1", 31.0f
        );

        Map<Integer, Float> coreTemps = CpuTempMath.getCoreTemperatures(data);

        assertEquals(1, coreTemps.size(), "Should ignore invalid core keys.");
        assertEquals(31.0f, coreTemps.get(1), "Should correctly map valid core keys.");
    }

    @Test
    void testGetTemperatureMetric() {
        Map<String, Float> data = Map.of("CPU Package", 75.0f);
        assertEquals(75.0f, CpuTempMath.getCpuPackageTemperature(data));
        assertEquals(0.0f, CpuTempMath.getCpuPackageTemperature(Map.of()));
    }
}
