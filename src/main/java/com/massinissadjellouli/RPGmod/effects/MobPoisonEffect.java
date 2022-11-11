package com.massinissadjellouli.RPGmod.effects;
//Source : https://www.youtube.com/watch?v=fm7urzE4pmo&t=206s&ab_channel=ModdingbyKaupenjoe

import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MobPoisonEffect extends MobEffect implements DamagingMobEffect {


    private boolean isVulnerable = false;

    protected MobPoisonEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity) {
        applyEffectTick(pLivingEntity, 1);
        if (pLivingEntity.getHealth() == 0 && !pLivingEntity.isAlive()) {
            RPGModEventFactory.onKilledBySwordEffect(pLivingEntity);
            pLivingEntity.removeEffect(this);
        }
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int hurt = isVulnerable ? 2 : 1;
        pLivingEntity.hurt(DamageSource.MAGIC, hurt);
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
