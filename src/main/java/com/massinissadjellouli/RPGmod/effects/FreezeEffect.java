package com.massinissadjellouli.RPGmod.effects;
//Source : https://www.youtube.com/watch?v=fm7urzE4pmo&t=206s&ab_channel=ModdingbyKaupenjoe

import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FreezeEffect extends MobEffect implements DamagingMobEffect {

    boolean isVulnerable = false;

    protected FreezeEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide) {
            double x = pLivingEntity.getX();
            double y = pLivingEntity.getY();
            double z = pLivingEntity.getZ();

            pLivingEntity.teleportTo(x, y, z);
            pLivingEntity.setDeltaMovement(0, 0, 0);
            if (isVulnerable) {
                pLivingEntity.hurt(DamageSource.MAGIC, 1);
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
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
    public void setIsVulnerable() {
        isVulnerable = true;
    }
}
