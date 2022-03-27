package com.nftworlds.stacccccccer.loot;

import org.bukkit.Material;

public class LootEntry {
    private int minCount;
    private int maxCount;
    private Material item;

    public LootEntry (int minCount, int maxCount, Material item) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.item = item;
    }

    public int getMinCount() { return minCount; }

    public int getMaxCount() { return maxCount; }

    public Material getItem() { return item; }
}
