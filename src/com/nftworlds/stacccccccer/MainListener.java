package com.nftworlds.stacccccccer;

import com.nftworlds.stacccccccer.models.StackableEntity;
import com.nftworlds.stacccccccer.loot.LootCalculator;
import com.nftworlds.stacccccccer.utils.StackingConfig;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MainListener implements Listener {
    private final LootCalculator lootCalculator;
    private final StackingConfig stackingConfig;
    private final StackingManager stackingManager;

    public MainListener(LootCalculator lootCalculator, StackingConfig stackingConfig, StackingManager stackingManager) {
        this.lootCalculator = lootCalculator;
        this.stackingConfig = stackingConfig;
        this.stackingManager = stackingManager;
    }

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        //only stack spawner mobs
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER) return;

        //only stack whitelisted mobs
        if(!stackingConfig.canStackMob(event.getEntityType())) return;

        StackableEntity existingStackableEntity = stackingManager.getEntityToStackOn(event.getEntity());
        if(existingStackableEntity != null) { //use an existing stackable entity
            existingStackableEntity.increaseCount(1);
            event.setCancelled(true);
        }
        else { //create a new entity to stack on
            StackableEntity stackableEntity = new StackableEntity(event.getEntity());
            stackingManager.addEntity(stackableEntity);
        }
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if(!stackingConfig.canStackMob(event.getEntityType())) return;

        StackableEntity stackableEntity = stackingManager.getStackableEntityByID(event.getEntity().getEntityId());
        if(stackableEntity == null) return; //not a stacked entity, use vanilla behavior

        //do not drop vanilla items
        event.getDrops().clear();

        LivingEntity killer = event.getEntity().getKiller();
        int lootingLevel = -1;
        if(killer != null && killer instanceof Player)
            lootingLevel = event.getEntity().getKiller().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);

        event.getDrops().addAll(
            lootCalculator.getDropsWeighted(stackableEntity.getEntity().getType(), stackableEntity.getCount(), lootingLevel)
        );

        //stop tracking entity
        stackingManager.removeEntity(stackableEntity);
    }
}
