package net.laurus.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CpuTempMath {

    /**
     * Counts the total number of CPU cores across all CPUs in the dataset.
     *
     * @param cpuTemperatureData Map where the key is the CPU ID, and the value is a map of temperature metrics.
     * @return The total number of cores across all CPUs.
     */
    public static int countTotalCores(Map<String, Map<String, Float>> cpuTemperatureData) {
        return cpuTemperatureData.values().stream()
                .flatMap(cpu -> cpu.keySet().stream())
                .filter(key -> key.startsWith("CPU Core #"))
                .mapToInt(key -> 1)
                .sum();
    }

    /**
     * Counts the number of cores for a single CPU.
     *
     * @param cpuTemperatureData Map of temperature metrics for a single CPU.
     * @return The number of cores in this CPU.
     */
    public static int countCoresForSingleCpu(Map<String, Float> cpuTemperatureData) {
        return (int) cpuTemperatureData.keySet().stream()
                .filter(key -> key.startsWith("CPU Core #"))
                .count();
    }

    /**
     * Retrieves a specific temperature metric from the dataset.
     *
     * @param metricName         The name of the metric (e.g., "CPU Package").
     * @param cpuTemperatureData Map of temperature metrics for a single CPU.
     * @return The value of the metric, or 0.0 if the metric is not found.
     */
    private static float getTemperatureMetric(String metricName, Map<String, Float> cpuTemperatureData) {
        return cpuTemperatureData.getOrDefault(metricName, 0.0f);
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

    /**
     * Retrieves core temperatures for a single CPU.
     *
     * @param cpuTemperatureData Map of temperature metrics for a single CPU.
     * @return A map of core numbers (1-based) to their respective temperatures.
     */
    public static Map<Integer, Float> getCoreTemperatures(Map<String, Float> cpuTemperatureData) {
        Map<Integer, Float> coreTemperatures = new HashMap<>();

        cpuTemperatureData.forEach((key, value) -> {
            if (key.startsWith("CPU Core #")) {
                try {
                    int coreNumber = Integer.parseInt(key.replace("CPU Core #", "").trim());
                    coreTemperatures.put(coreNumber, value);
                } catch (NumberFormatException e) {
                    // Log or handle invalid core identifiers
                }
            }
        });

        return coreTemperatures;
    }
}
