package com.massinissadjellouli.RPGmod.block;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER
            = DeferredRegister.create(ForgeRegistries.BLOCKS, RPGMod.MODID);

    public static void registerBlocks(IEventBus eventBus){
        BLOCK_DEFERRED_REGISTER.register(eventBus);
    }
}
