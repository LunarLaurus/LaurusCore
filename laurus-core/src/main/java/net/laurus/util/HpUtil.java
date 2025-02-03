package net.laurus.util;

import java.util.HashMap;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HpUtil {
    
    private static final HashMap<Integer, Boolean> HP_CACHE = new HashMap<>();

    public static boolean isHp(String manufacturer) {
        if (manufacturer != null && HP_CACHE.containsKey(manufacturer.hashCode())) {
            return HP_CACHE.get(manufacturer.hashCode());
        }
        boolean hp = manufacturer != null
                && (manufacturer.toLowerCase().equals("hp") || manufacturer.toLowerCase().equals("hpe"));
        HP_CACHE.put(manufacturer != null ? manufacturer.hashCode() : 0, hp);
        return hp;
    }

}
