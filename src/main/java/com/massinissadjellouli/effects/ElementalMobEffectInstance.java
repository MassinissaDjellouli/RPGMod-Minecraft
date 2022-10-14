package com.massinissadjellouli.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class ElementalMobEffectInstance extends MobEffectInstance {
    private boolean fromPlayerSword;


    public ElementalMobEffectInstance(MobEffect pEffect, int pDuration, boolean fromPlayerSword) {
        super(pEffect, pDuration);
        this.fromPlayerSword = fromPlayerSword;
    }

    @Override
    public void applyEffect(LivingEntity pEntity) {
        if(this.getEffect() instanceof DamagingMobEffect effect && fromPlayerSword){
            effect.applyElementalEffectTickFromPlayer(pEntity,1);
            return;
        }
        super.applyEffect(pEntity);
    }
}
