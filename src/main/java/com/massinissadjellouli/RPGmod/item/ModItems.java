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

    //Crystals
    public static final RegistryObject<Item> FIRE_CRYSTAL = ITEM_DEFERRED_REGISTER.register("fire_crystal",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> ICE_CRYSTAL = ITEM_DEFERRED_REGISTER.register("ice_crystal",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> POISON_CRYSTAL = ITEM_DEFERRED_REGISTER.register("poison_crystal",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //Titanium
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEM_DEFERRED_REGISTER.register("titanium_ingot",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> REINFORCED_TITANIUM_INGOT = ITEM_DEFERRED_REGISTER.register("reinforced_titanium_ingot",
            ()-> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    //Steel tools
    public static final RegistryObject<PickaxeItem> STEEL_PICKAXE = ITEM_DEFERRED_REGISTER.register("steel_pickaxe",
            ()-> new PickaxeItem(
                    new ToolTiers().STEEL,1,-2.8f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<AxeItem> STEEL_AXE = ITEM_DEFERRED_REGISTER.register("steel_axe",
            ()-> new AxeItem(
                    new ToolTiers().STEEL,5,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<HoeItem> STEEL_HOE = ITEM_DEFERRED_REGISTER.register("steel_hoe",
            ()-> new HoeItem(
                    new ToolTiers().STEEL,-2,0,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<ShovelItem> STEEL_SHOVEL = ITEM_DEFERRED_REGISTER.register("steel_shovel",
            ()-> new ShovelItem(
                    new ToolTiers().STEEL,2,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<SwordItem> STEEL_SWORD = ITEM_DEFERRED_REGISTER.register("steel_sword",
            ()-> new SwordItem(
                    new ToolTiers().STEEL,4,-2.4f,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    //Titanium tools
    public static final RegistryObject<PickaxeItem> TITANIUM_PICKAXE = ITEM_DEFERRED_REGISTER.register("titanium_pickaxe",
            ()-> new PickaxeItem(
                    new ToolTiers().TITANIUM,1,-2.8f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<AxeItem> TITANIUM_AXE = ITEM_DEFERRED_REGISTER.register("titanium_axe",
            ()-> new AxeItem(
                    new ToolTiers().TITANIUM,5,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<HoeItem> TITANIUM_HOE = ITEM_DEFERRED_REGISTER.register("titanium_hoe",
            ()-> new HoeItem(
                    new ToolTiers().TITANIUM,-2,0,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<ShovelItem> TITANIUM_SHOVEL = ITEM_DEFERRED_REGISTER.register("titanium_shovel",
            ()-> new ShovelItem(
                    new ToolTiers().TITANIUM,2,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<SwordItem> TITANIUM_SWORD = ITEM_DEFERRED_REGISTER.register("titanium_sword",
            ()-> new SwordItem(
                    new ToolTiers().TITANIUM,4,-2.4f,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    // Reinforced titanium tools

    public static final RegistryObject<PickaxeItem> REINFORCED_TITANIUM_PICKAXE =
            ITEM_DEFERRED_REGISTER.register("reinforced_titanium_pickaxe",
            ()-> new PickaxeItem(
                    new ToolTiers().REINFORCED_TITANIUM,1,-2.8f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));

    public static final RegistryObject<AxeItem> REINFORCED_TITANIUM_AXE =
            ITEM_DEFERRED_REGISTER.register("reinforced_titanium_axe",
            ()-> new AxeItem(
                    new ToolTiers().REINFORCED_TITANIUM,5,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<HoeItem> REINFORCED_TITANIUM_HOE =
            ITEM_DEFERRED_REGISTER.register("reinforced_titanium_hoe",
            ()-> new HoeItem(
                    new ToolTiers().REINFORCED_TITANIUM,-2,0,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<ShovelItem> REINFORCED_TITANIUM_SHOVEL =
            ITEM_DEFERRED_REGISTER.register("reinforced_titanium_shovel",
            ()-> new ShovelItem(
                    new ToolTiers().REINFORCED_TITANIUM,2,-3f,
                    new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<SwordItem> REINFORCED_TITANIUM_SWORD =
            ITEM_DEFERRED_REGISTER.register("reinforced_titanium_sword",
            ()-> new SwordItem(
                    new ToolTiers().REINFORCED_TITANIUM,4,-2.4f,
                    new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));



    public static void registerItems(IEventBus eventBus){
        ITEM_DEFERRED_REGISTER.register(eventBus);
    }
}
