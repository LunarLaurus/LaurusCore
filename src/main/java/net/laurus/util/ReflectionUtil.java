package net.laurus.util;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/** Utility class for reflection-related methods */
@SuppressWarnings(value = "all")
public class ReflectionUtil {

    private static final Logger logger = Logger.getLogger(ReflectionUtil.class.getName());

    /** Finds all fully qualified class names (FQCN) in a specified package */
    public static String[] findAllClassesInPackage(String packageName) {
        try {
            Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
            Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
            Set<Class<? extends Enum>> enums = reflections.getSubTypesOf(Enum.class);
            logger.log(Level.INFO, "Found {0} classes, {1} Enums in package: {2}", new Object[]{classes.size(), enums.size(), packageName});
            classes.addAll(enums);
            return classes.stream()
                    .map(Class::getName)
                    .distinct()
                    .sorted()
                    .toArray(String[]::new);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding classes in package: " + packageName, e);
            return new String[0];
        }
    }
}

