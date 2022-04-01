package com.nftworlds.stacccccccer.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.HashSet;

public class StackingConfig {
    private HashSet<EntityType> stackableTypes = new HashSet<>();
    private boolean voidAboveStackLimitMobs; //if new entities should be voided if they'd stack above stack limit
    private int maxStackCount; //max number of mobs in a single entity
    private int schedulerTiming; //ticks between stack clearing
    private double stackDistance; //max pythagorean distance for stacking of in world mobs (in blocks)
    private double spawnerXDistance; //max delta X for spawner direct stacking
    private double spawnerYDistance; //max delta Y for spawner direct stacking
    private double spawnerZDistance; //max delta Z for spawner direct stacking

    public StackingConfig(FileConfiguration config) {
        //TODO: LOAD STACKABLE MOB TYPES FROM CONFIG
        stackableTypes.add(EntityType.BLAZE);
        stackableTypes.add(EntityType.CREEPER);
        stackableTypes.add(EntityType.IRON_GOLEM);

        voidAboveStackLimitMobs = config.getBoolean("voidAboveStackLimitMobs");
        maxStackCount = config.getInt("maxStackCount");
        schedulerTiming = config.getInt("schedulerTiming");
        stackDistance = config.getDouble("stackDistance");

        spawnerXDistance = config.getInt("spawnerXDistance");
        spawnerYDistance = config.getInt("spawnerYDistance");
        spawnerZDistance = config.getInt("spawnerZDistance");
    }

    public boolean canStackMob(EntityType type) {
        return stackableTypes.contains(type);
    }

    public int getMaxStackCount() {
        return maxStackCount;
    }

    public double getStackDistance() {
        return stackDistance;
    }

    public int getSchedulerTiming() { return schedulerTiming; }

    public double getSpawnerXDistance() { return spawnerXDistance; }

    public double getSpawnerYDistance() { return spawnerYDistance; }

    public double getSpawnerZDistance() { return spawnerZDistance; }

    public boolean isVoidAboveStackLimitMobs() { return voidAboveStackLimitMobs; }
}
