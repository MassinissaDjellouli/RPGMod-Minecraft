package com.massinissadjellouli.RPGmod.damageIndicator;

import net.minecraft.world.entity.decoration.ArmorStand;


public class DamageIndicatorData {
    private ArmorStand damageIndicator;
    private int ticksSinceSpawn;

    public void addTick(int tick) {
        ticksSinceSpawn += tick;
    }

    public int getTicksSinceSpawn() {
        return ticksSinceSpawn;
    }

    public void kill() {
        damageIndicator.kill();
    }

    public DamageIndicatorData(ArmorStand damageIndicator) {
        this.damageIndicator = damageIndicator;
        this.ticksSinceSpawn = 0;
    }
}
