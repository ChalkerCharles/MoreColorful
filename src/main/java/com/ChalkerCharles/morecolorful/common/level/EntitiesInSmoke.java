package com.ChalkerCharles.morecolorful.common.level;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.world.entity.Entity;

public class EntitiesInSmoke {
    private final IntSet entitiesInSmoke = new IntOpenHashSet();
    private final IntSet entitiesToAdd = new IntOpenHashSet();

    public void addEntity(Entity entity) {
        this.entitiesToAdd.add(entity.getId());
    }

    public boolean isInSmoke(Entity entity) {
        return this.entitiesInSmoke.contains(entity.getId());
    }

    public void tick() {
        this.entitiesInSmoke.clear();
        this.entitiesInSmoke.addAll(this.entitiesToAdd);
        this.entitiesToAdd.clear();
    }
}
