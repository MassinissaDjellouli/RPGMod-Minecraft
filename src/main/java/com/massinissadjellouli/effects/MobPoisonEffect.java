package com.massinissadjellouli.effects;
//Source : https://www.youtube.com/watch?v=fm7urzE4pmo&t=206s&ab_channel=ModdingbyKaupenjoe
import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MobPoisonEffect extends MobEffect implements DamagingMobEffect{


    protected MobPoisonEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
    @Override
    public void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity, int pAmplifier){
        applyEffectTick(pLivingEntity,pAmplifier);
        if(pLivingEntity.getHealth() == 0 && !pLivingEntity.isAlive()){
            RPGModEventFactory.onKilledBySwordEffect(pLivingEntity);
            pLivingEntity.removeEffect(this);
        }
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.hurt(DamageSource.MAGIC, 1.0F);
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
