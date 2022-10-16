package com.massinissadjellouli.RPGmod.effects;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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
            "burning",() -> RPGModEffects.BURNING.effect
    );
    public static final RegistryObject<MobEffect> MOB_POISON = MOB_EFFECTS.register(
            "poison",() -> RPGModEffects.MOB_POISON.effect
    );
    public static final RegistryObject<MobEffect> LIGHTNING = MOB_EFFECTS.register(
            "lightning",() -> RPGModEffects.LIGHTNING.effect
    );

    public enum RPGModEffects{
        LIGHTNING(new LightningEffect(MobEffectCategory.HARMFUL,16187136)),
        MOB_POISON(new MobPoisonEffect(MobEffectCategory.HARMFUL,14088995 )),
        FREEZE(new FreezeEffect(MobEffectCategory.HARMFUL,3124687 )),
        BURNING(new BurningEffect(MobEffectCategory.HARMFUL,16728064));

        public final MobEffect effect;
        RPGModEffects(MobEffect effect){
            this.effect = effect;
        }
    }
    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
