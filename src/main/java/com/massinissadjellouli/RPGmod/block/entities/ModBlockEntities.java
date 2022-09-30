package com.massinissadjellouli.RPGmod.block.entities;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RPGMod.MODID);

    public static final RegistryObject<BlockEntityType<ItemCompressorBlockEntity>> ITEM_COMPRESSOR =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("item_compressor",
                    ()->
                BlockEntityType.Builder.of(ItemCompressorBlockEntity::new,
                        ModBlocks.ITEM_COMPRESSOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<RarityTableBlockEntity>> RARITY_TABLE =
            BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register("rarity_table",
                    ()->
                BlockEntityType.Builder.of(RarityTableBlockEntity::new,
                        ModBlocks.RARITY_TABLE.get()).build(null));
    public static void registerBlockEntities(IEventBus eventBus){
        BLOCK_ENTITY_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
