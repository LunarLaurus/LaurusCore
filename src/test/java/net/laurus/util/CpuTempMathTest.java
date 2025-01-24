package net.laurus.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Map;

class CpuTempMathTest {

    @Test
    void testCountCoresSingleCpu() {
        Map<String, Float> data = Map.of(
                "CPU Core #1", 30.5f,
                "CPU Core #2", 31.0f
        );
        assertEquals(2, CpuTempMath.countCoresForSingleCpu(data));
    }

    @Test
    void testGetCoreTemperatures() {
        Map<String, Float> data = Map.of(
                "CPU Core #1", 30.5f,
                "CPU Core #2", 31.0f
        );
        Map<Integer, Float> result = CpuTempMath.getCoreTemperatures(data);

        assertEquals(30.5f, result.get(1));
        assertEquals(31.0f, result.get(2));
    }
}
