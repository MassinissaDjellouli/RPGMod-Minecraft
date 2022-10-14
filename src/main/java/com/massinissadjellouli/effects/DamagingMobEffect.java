package com.massinissadjellouli.effects;

import net.minecraft.world.entity.LivingEntity;

public interface DamagingMobEffect{
    void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity);
    void setIsVulnerable();
}
