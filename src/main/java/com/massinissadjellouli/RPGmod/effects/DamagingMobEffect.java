package com.massinissadjellouli.RPGmod.effects;

import net.minecraft.world.entity.LivingEntity;

public interface DamagingMobEffect {
    void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity);

    void setIsVulnerable();
}
