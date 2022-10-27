package com.massinissadjellouli.RPGmod.entities;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RPGMod.MODID);
    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);

    }

    public static final RegistryObject<EntityType<Goblin>> GOBLIN =
            ENTITIES.register("goblin",() -> EntityType.Builder.of(Goblin::new, MobCategory.MONSTER).build("Goblin"));
}
