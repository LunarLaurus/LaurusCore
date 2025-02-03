package net.laurus.util;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneralUtil {

    public static boolean timeDifference(long currentTimeMillis, long earlierTimeMillis, int minimumSeconds) {
        long timeDifferenceSeconds = (currentTimeMillis - earlierTimeMillis) / 1000;
        return timeDifferenceSeconds >= minimumSeconds;
    }
    
    // Function to combine varargs String[] and return one sorted array with no duplicates
    public static String[] combineAndSort(String[]... arrays) {
        return Arrays.stream(arrays)   // Stream all arrays
                .flatMap(Arrays::stream)  // Flatten them into one stream
                .collect(Collectors.toCollection(TreeSet::new))  // Collect into a TreeSet (sorted and no duplicates)
                .toArray(String[]::new);  // Convert the TreeSet back to an array
    }

}
