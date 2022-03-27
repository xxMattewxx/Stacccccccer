package com.nftworlds.stacccccccer.utils;

import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class Helper {
    private static HashMap<String, String> friendlyNamesMapping = new HashMap<>();
    public static String getFriendlyNameForType(EntityType type) {
        if(friendlyNamesMapping.containsKey(type.name()))
            return friendlyNamesMapping.get(type.name());

        return type.name();
    }

    static {
        //TODO: INCLUDE ALL MOBS
        friendlyNamesMapping.put("BLAZE", "Blaze");
        friendlyNamesMapping.put("CREEPER", "Creeper");
        friendlyNamesMapping.put("IRON_GOLEM", "Iron Golem");
    }
}
