package com.nftworlds.stacccccccer.loot;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LootCalculator {
    private static Random rnd = new Random();
    private HashMap<EntityType, List<LootEntry>> lootPerType = new HashMap<>();

    public LootCalculator() {
        //TODO: LOAD LOOT ENTRIES FROM CONFIG
        lootPerType.put(EntityType.BLAZE, new ArrayList<>() {
            {
                add(new LootEntry(0, 1, Material.BLAZE_ROD));
            }
        });

        lootPerType.put(EntityType.CREEPER, new ArrayList<>() {
            {
                add(new LootEntry(0, 2, Material.GUNPOWDER));
            }
        });

        lootPerType.put(EntityType.IRON_GOLEM, new ArrayList<>() {
            {
                add(new LootEntry(3, 5, Material.IRON_INGOT));
                add(new LootEntry(0, 2, Material.POPPY));
            }
        });
    }

    public List<ItemStack> getDropsWeighted(EntityType entityType, int entityCount, int lootingLevel) {
        List<LootEntry> entries = lootPerType.get(entityType);
        if(entries == null) return null;

        HashMap<Material, Integer> itemCounts = new HashMap<>();
        for(int i = 0; i < entityCount; i++) {
            for(LootEntry entry : entries) {
                //+1 is here so the bound is inclusive
                int cnt = Math.max(entry.getMinCount(), rnd.nextInt(entry.getMaxCount() + lootingLevel + 1));
                if(cnt == 0) continue;

                if(!itemCounts.containsKey(entry.getItem()))
                    itemCounts.put(entry.getItem(), 0);

                itemCounts.put(entry.getItem(), itemCounts.get(entry.getItem()) + cnt);
            }
        }

        List<ItemStack> ret = new ArrayList<>();
        for(HashMap.Entry<Material, Integer> entry : itemCounts.entrySet()) {
            int count = entry.getValue();
            while(count >= 64) {
                ret.add(new ItemStack(entry.getKey(), 64));
                count -= 64;
            }

            if(count > 0) ret.add(new ItemStack(entry.getKey(), count));
        }
        return ret;
    }
}
