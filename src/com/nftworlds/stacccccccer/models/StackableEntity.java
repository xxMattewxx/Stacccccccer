package com.nftworlds.stacccccccer.models;

import org.bukkit.entity.Entity;
import com.nftworlds.stacccccccer.utils.Helper;

public class StackableEntity {
    private int count;
    private Entity entity;

    public StackableEntity(Entity entity) {
        this.count = 1;
        this.entity = entity;

        entity.setCustomNameVisible(true);
        updateName();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        updateName();
    }

    public void increaseCount(int amount) {
        count += amount;
        updateName();
    }

    private void updateName() {
        getEntity().setCustomName(String.format("§l§6%s §f(%d)", Helper.getFriendlyNameForType(getEntity().getType()), count));
    }

    public Entity getEntity() {
        return entity;
    }
}
