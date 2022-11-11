package com.massinissadjellouli.RPGmod.screen;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, RPGMod.MODID);

    public static final RegistryObject<MenuType<ItemCompressorMenu>> ITEM_COMPRESSOR_MENU =
            registerMenuType(ItemCompressorMenu::new, "item_compressor_menu");
    public static final RegistryObject<MenuType<RarityTableMenu>> RARITY_TABLE_MENU =
            registerMenuType(RarityTableMenu::new, "rarity_table_menu");
    public static final RegistryObject<MenuType<ClassChangeMenu>> CHANGE_CLASS_MENU =
            registerMenuType(ClassChangeMenu::new, "change_class_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(
            IContainerFactory<T> factory, String name) {
        return MENU_TYPE_DEFERRED_REGISTER.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void registerMenus(IEventBus eventBus) {
        MENU_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
