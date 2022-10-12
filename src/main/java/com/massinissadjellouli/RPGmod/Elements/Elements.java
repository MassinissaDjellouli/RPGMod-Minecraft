package com.massinissadjellouli.RPGmod.Elements;

import net.minecraft.world.effect.MobEffect;

import static com.massinissadjellouli.effects.ModEffects.RPGModEffects.*;


public enum Elements {
    ICE(FREEZE.effect),
    POISON(MOB_POISON.effect),
    FIRE(BURNING.effect);

    public final MobEffect effect;
    Elements(MobEffect effect){
        this.effect = effect;
    }
}
