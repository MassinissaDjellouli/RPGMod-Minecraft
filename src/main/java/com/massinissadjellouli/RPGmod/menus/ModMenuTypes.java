package com.massinissadjellouli.RPGmod.menus;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RPGMod.MODID);

    public static void registerMenus(IEventBus eventBus){
        MENU_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
