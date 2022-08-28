package com.massinissadjellouli.RPGmod.item;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.ITEMS, RPGMod.MODID);

    //COPPER TOOLS
    //Properties: Tier,AttackDamageModifer,AttackSpeed,Tab
    public static final RegistryObject<PickaxeItem> COPPER_PICKAXE = ITEM_DEFERRED_REGISTER.register("copper_pickaxe",
            ()-> new PickaxeItem(
                    new ToolTiers().COPPER,1,-2.8f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<AxeItem> COPPER_AXE = ITEM_DEFERRED_REGISTER.register("copper_axe",
            ()-> new AxeItem(
                    new ToolTiers().COPPER,5,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<HoeItem> COPPER_HOE = ITEM_DEFERRED_REGISTER.register("copper_hoe",
            ()-> new HoeItem(
                    new ToolTiers().COPPER,-2,0,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<ShovelItem> COPPER_SHOVEL = ITEM_DEFERRED_REGISTER.register("copper_shovel",
            ()-> new ShovelItem(
                    new ToolTiers().COPPER,2,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<SwordItem> COPPER_SWORD = ITEM_DEFERRED_REGISTER.register("copper_sword",
            ()-> new SwordItem(
                    new ToolTiers().COPPER,4,-2.4f,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    //Steel
    public static final RegistryObject<Item> STEEL_INGOT = ITEM_DEFERRED_REGISTER.register("steel_ingot",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static void registerItems(IEventBus eventBus){
        ITEM_DEFERRED_REGISTER.register(eventBus);
    }
}
