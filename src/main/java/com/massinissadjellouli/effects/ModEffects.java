package com.massinissadjellouli.effects;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RPGMod.MODID);

    public static final RegistryObject<MobEffect> FREEZE = MOB_EFFECTS.register(
            "freeze",() -> new FreezeEffect(MobEffectCategory.HARMFUL,3124687)
    );
    public static final RegistryObject<MobEffect> BURNING = MOB_EFFECTS.register(
            "burning",() -> new BurningEffect(MobEffectCategory.HARMFUL,16728064 )
    );
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
