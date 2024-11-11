package net.laurus.util;

import java.util.HashMap;
import java.util.Map;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CpuTempMath {

    // Utility method to count CPU cores based on the prefix "CPU Core #"
    public static int countCores(Map<String, Map<String, Float>> cpuTemperatureData) {
        // Identify the key for a single CPU
        return cpuTemperatureData.values().stream().findFirst() // Take the first entry (assuming all CPUs have identical core counts)
                .map(coreMap -> (int) coreMap.keySet().stream()
                        .filter(key -> key.startsWith("CPU Core #"))
                        .count())
                .orElse(0); // Default to 0 if no data is found
    }

    // Utility method to count CPU cores for a single CPU
    public static int countCoresSingleCpu(Map<String, Float> cpuTemperatureData) {
        // Identify the key for a single CPU
        return (int) cpuTemperatureData.keySet().stream()
                .filter(key -> key.startsWith("CPU Core #"))
                .count(); // Count cores based on the matching keys
    }
    
    private static float getTemperatureMetric(String metricName, Map<String, Float> cpuTemperatureData) {
        Float temperature = cpuTemperatureData.get(metricName);
        return temperature != null ? temperature : 0.0f;
    }


    public static float getCpuPackageTemperature(Map<String, Float> cpuTemperatureData) {
        return getTemperatureMetric("CPU Package", cpuTemperatureData);
    }

    public static float getCoreMaxTemperature(Map<String, Float> cpuTemperatureData) {
        return getTemperatureMetric("Core Max", cpuTemperatureData);
    }

    public static float getCoreAverageTemperature(Map<String, Float> cpuTemperatureData) {
        return getTemperatureMetric("Core Average", cpuTemperatureData);
    }

    public static Map<Integer, Float> getCoreTemperatures(int cpuId,
            Map<String, Float> cpuTemperatureData) {
        return getCoreTemperatures(cpuId, countCoresSingleCpu(cpuTemperatureData), cpuTemperatureData);
    }

    public static Map<Integer, Float> getCoreTemperatures(int cpuId, int coreCount,
            Map<String, Float> cpuTemperatureData) {
        
        Map<Integer, Float> coreTemperatures = new HashMap<>();
        Map<String, Float> data = cpuTemperatureData;
        for (int i = 1; i <= coreCount/2; i++) {
            String coreKey = "CPU Core #" + i;
            coreTemperatures.put(i, data.getOrDefault(coreKey, 0.0f));
        }

        return coreTemperatures;
    }
}
