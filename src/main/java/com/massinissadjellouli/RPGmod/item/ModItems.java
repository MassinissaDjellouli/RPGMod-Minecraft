package com.massinissadjellouli.RPGmod.item;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, RPGMod.MODID);

    public static void registerItems(IEventBus eventBus){
        ITEM_DEFERRED_REGISTER.register(eventBus);
    }
}
