package com.massinissadjellouli.RPGmod.effects;

import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BurningEffect extends MobEffect implements DamagingMobEffect {

    boolean isVulnerable = false;

    protected BurningEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity) {
        applyEffectTick(pLivingEntity, 1);
        if (!pLivingEntity.isAlive()) {
            RPGModEventFactory.onKilledBySwordEffect(pLivingEntity);
            pLivingEntity.removeEffect(this);
        }
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide) {
            pLivingEntity.setSecondsOnFire(1);
        }
        if (isVulnerable) {
            pLivingEntity.hurt(DamageSource.MAGIC, 1);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void setIsVulnerable() {
        isVulnerable = true;
    }
}
