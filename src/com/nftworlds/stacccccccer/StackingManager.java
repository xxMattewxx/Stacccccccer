package com.nftworlds.stacccccccer;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import com.nftworlds.stacccccccer.utils.StackingConfig;
import com.nftworlds.stacccccccer.models.StackableEntity;
import org.bukkit.scoreboard.Objective;

import java.util.*;

public class StackingManager {
    private final StackingConfig stackingConfig;
    private final HashMap<Integer, StackableEntity> idToStackableEntity = new HashMap<>();

    public StackingManager(StackingConfig config) {
        this.stackingConfig = config;
    }

    //Used by the spawners only
    public StackableEntity getEntityToStackOn(Entity entity) {
        List<Entity> nearbyEntities = entity.getNearbyEntities(stackingConfig.getSpawnerXDistance(), stackingConfig.getSpawnerYDistance(), stackingConfig.getSpawnerZDistance());
        for(Entity nearbyEntity : nearbyEntities) {
            if(nearbyEntity.isDead()) continue;
            if(entity.getType() != nearbyEntity.getType()) continue;

            StackableEntity stackableEntity = getStackableEntityByID(nearbyEntity.getEntityId());
            if(stackableEntity == null) continue;
            if(stackableEntity.getCount() + 1 > stackingConfig.getMaxStackCount()) continue;

            return stackableEntity;
        }

        return null;
    }

    public StackableEntity getStackableEntityByID(int id) {
        return idToStackableEntity.get(id);
    }

    public List<StackableEntity> getAll() {
        return idToStackableEntity.values().stream().toList();
    }

    public void addEntity(StackableEntity stackableEntity) {
        idToStackableEntity.put(stackableEntity.getEntity().getEntityId(), stackableEntity);
    }

    public void removeEntity(StackableEntity stackableEntity) {
        idToStackableEntity.remove(stackableEntity.getEntity().getEntityId());
    }
}