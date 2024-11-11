package net.laurus.rabbit;

import net.laurus.util.GeneralUtil;
import net.laurus.util.ReflectionUtil;

public class RabbitConstants {

	public static final String[] ALLOW_SERIALIZING_CLASSES;

	static {
		String[] iloDtoClasses = ReflectionUtil.findAllClassesInPackage("net.laurus.data.dto");
		String[] iloEnumClasses = ReflectionUtil.findAllClassesInPackage("net.laurus.data.enums");
		String[] networkClasses = ReflectionUtil.findAllClassesInPackage("net.laurus.network");
		String[] iloClientClasses = ReflectionUtil.findAllClassesInPackage("net.laurus.ilo");
		String[] otherClasses = {
			"java.lang.Enum",
			"java.util.ArrayList",
            "java.util.LinkedList",
            "java.util.Vector",
            "java.util.Stack",
            "java.util.HashSet",
            "java.util.LinkedHashSet",
            "java.util.TreeSet",
            "java.util.HashMap",
            "java.util.LinkedHashMap",
            "java.util.TreeMap",
            "java.util.EnumSet",
            "java.util.EnumMap",
            "java.util.PriorityQueue",
            "java.util.Collections",
            "java.util.AbstractCollection",
            "java.util.AbstractList",
            "java.util.AbstractSet",
            "java.util.AbstractMap",
            "java.util.List",
            "java.util.Set",
            "java.util.Map",
            "java.util.Queue",
            "java.util.Deque",
            "java.util.Iterator",
            "java.util.ListIterator",
            "java.util.SortedSet",
            "java.util.SortedMap",
            "java.util.Collection",
            "java.util.Map.Entry"
        };
		ALLOW_SERIALIZING_CLASSES = GeneralUtil.combineAndSort(iloDtoClasses, iloEnumClasses, iloClientClasses,
				networkClasses, otherClasses);
	}

}
