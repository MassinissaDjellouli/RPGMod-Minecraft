package com.massinissadjellouli.RPGmod.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class KilledBySwordEffectEvent extends LivingEvent {
    public KilledBySwordEffectEvent(LivingEntity entity) {
        super(entity);
    }
}
