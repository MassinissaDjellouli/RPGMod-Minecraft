package com.massinissadjellouli.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

public interface DamagingMobEffect{
    void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity);
    void setIsVulnerable();
}
