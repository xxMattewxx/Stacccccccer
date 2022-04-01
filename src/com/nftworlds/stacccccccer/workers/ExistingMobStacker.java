package com.nftworlds.stacccccccer.workers;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import com.nftworlds.stacccccccer.StackingManager;
import com.nftworlds.stacccccccer.utils.StackingConfig;
import com.nftworlds.stacccccccer.models.StackableEntity;

import java.util.*;

public class ExistingMobStacker extends BukkitRunnable {
    private StackingConfig stackingConfig;
    private StackingManager stackingManager;

    public ExistingMobStacker(StackingConfig stackingConfig, StackingManager stackingManager) {
        this.stackingConfig = stackingConfig;
        this.stackingManager = stackingManager;
    }

    @Override
    public void run() {
        List<StackableEntity> stackableEntitiesList = stackingManager.getAll();
        HashSet<StackableEntity> processedEntities = new HashSet<>();
        List<StackableEntity> entitiesToFree = new ArrayList<>();

        for(StackableEntity stackableEntity : stackableEntitiesList) {
            if(stackableEntity.getCount() >= stackingConfig.getMaxStackCount()) continue;

            List<Entity> nearbyEntities = stackableEntity.getEntity().getNearbyEntities(stackingConfig.getStackDistance(), stackingConfig.getStackDistance(), stackingConfig.getStackDistance());
            for(Entity nearbyEntity : nearbyEntities) {
                StackableEntity candidateStackableEntity = stackingManager.getStackableEntityByID(nearbyEntity.getEntityId());
                if(candidateStackableEntity == null) continue;

                if(stackableEntity.getEntity().getType() != candidateStackableEntity.getEntity().getType()) continue; //only stack same types
                if(candidateStackableEntity.getCount() < stackableEntity.getCount()) continue; //higher count entity gets stacked on
                if(processedEntities.contains(candidateStackableEntity)) continue;

                if(candidateStackableEntity.getCount() + stackableEntity.getCount() >= stackingConfig.getMaxStackCount()) {
                    if(stackingConfig.isVoidAboveStackLimitMobs()) {
                        candidateStackableEntity.setCount(stackingConfig.getMaxStackCount());
                        entitiesToFree.add(stackableEntity);
                        processedEntities.add(stackableEntity);
                        break;
                    }
                    else
                        continue;
                };

                entitiesToFree.add(stackableEntity);
                processedEntities.add(stackableEntity);
                candidateStackableEntity.increaseCount(stackableEntity.getCount());
                break;
            }
        }

        for(StackableEntity entity : entitiesToFree) {
            entity.getEntity().remove();
            stackingManager.removeEntity(entity);
        }
    }
}