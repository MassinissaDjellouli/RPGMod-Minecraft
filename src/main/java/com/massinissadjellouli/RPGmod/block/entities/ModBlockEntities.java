package com.massinissadjellouli.RPGmod.block.entities;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RPGMod.MODID);

    public static void registerBlockEntities(IEventBus eventBus){
        BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
