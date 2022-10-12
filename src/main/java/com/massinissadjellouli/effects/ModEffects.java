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
            "freeze",() -> RPGModEffects.FREEZE.effect
    );
    public static final RegistryObject<MobEffect> BURNING = MOB_EFFECTS.register(
            "burning",() -> RPGModEffects.MOB_POISON.effect
    );
    public static final RegistryObject<MobEffect> MOB_POISON = MOB_EFFECTS.register(
            "poison",() -> RPGModEffects.BURNING.effect
    );

    public enum RPGModEffects{
        MOB_POISON(new MobPoisonEffect(MobEffectCategory.HARMFUL,65301 )),
        FREEZE(new FreezeEffect(MobEffectCategory.HARMFUL,3124687 )),
        BURNING(new BurningEffect(MobEffectCategory.HARMFUL,16728064 ));

        public MobEffect effect;
        RPGModEffects(MobEffect effect){
            this.effect = effect;
        }
    }
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
