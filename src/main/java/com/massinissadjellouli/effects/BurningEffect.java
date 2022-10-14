package com.massinissadjellouli.effects;
import com.massinissadjellouli.RPGmod.events.RPGModEventFactory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BurningEffect extends MobEffect implements DamagingMobEffect{

    protected BurningEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyElementalEffectTickFromPlayer(LivingEntity pLivingEntity, int pAmplifier){
        applyEffectTick(pLivingEntity,pAmplifier);
        if(!pLivingEntity.isAlive()){
            RPGModEventFactory.onKilledBySwordEffect(pLivingEntity);
            pLivingEntity.removeEffect(this);
        }
    }
    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if(!pLivingEntity.level.isClientSide){
            pLivingEntity.setSecondsOnFire(1);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
