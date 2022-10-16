package com.massinissadjellouli.RPGmod.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ElementalMobEffectInstance extends MobEffectInstance {
    private final boolean fromPlayerSword;
    private final boolean isVulnerable;
    private final boolean isResistant;

    public ElementalMobEffectInstance(MobEffect pEffect, int pDuration, boolean fromPlayerSword, boolean isVulnerable, boolean isResistant) {
        super(pEffect, pDuration);
        this.fromPlayerSword = fromPlayerSword;
        this.isVulnerable = isVulnerable;
        this.isResistant = isResistant;
    }

    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (isResistant) {
            return;
        }
        if (this.getEffect() instanceof DamagingMobEffect effect) {
            if (isVulnerable) {
                effect.setIsVulnerable();
            }
            if (fromPlayerSword){
                effect.applyElementalEffectTickFromPlayer(pEntity);
                return;
            }
            super.applyEffect(pEntity);
        }
    }
}
