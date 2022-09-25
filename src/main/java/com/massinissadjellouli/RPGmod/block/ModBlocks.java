package com.massinissadjellouli.RPGmod.block;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.block.custom.ItemCompressorBlock;
import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import com.massinissadjellouli.RPGmod.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER
            = DeferredRegister.create(ForgeRegistries.BLOCKS, RPGMod.MODID);

    //Crystals
    public static final RegistryObject<Block> FIRE_CRYSTAL_ORE = registerBlock("fire_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops(),
                    UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_FIRE_CRYSTAL_ORE = registerBlock("deepslate_fire_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
        UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> ICE_CRYSTAL_ORE = registerBlock("ice_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops(),
                    UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_ICE_CRYSTAL_ORE = registerBlock("deepslate_ice_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
        UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> POISON_CRYSTAL_ORE = registerBlock("poison_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops(),
                    UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_POISON_CRYSTAL_ORE = registerBlock("deepslate_poison_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
        UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_MULTI_CRYSTAL_ORE = registerBlock("deepslate_multi_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE),
        UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> MULTI_CRYSTAL_ORE = registerBlock("multi_crystal_ore",
            ()-> new DropExperienceBlock(
            BlockBehaviour.Properties.of(Material.STONE).strength(14f,10f).requiresCorrectToolForDrops(),
        UniformInt.of(7,13)),CreativeModeTab.TAB_BUILDING_BLOCKS);

    //Titanium
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            ()-> new Block(
                    BlockBehaviour.Properties.of(Material.STONE).strength(4f,4f).requiresCorrectToolForDrops())
            ,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlock("deepslate_titanium_ore",
            ()-> new Block(
                    BlockBehaviour.Properties.of(Material.STONE).strength(4f,4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE))
            ,CreativeModeTab.TAB_BUILDING_BLOCKS);


    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            ()-> new Block(
                    BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                            .strength(4f,4f).requiresCorrectToolForDrops().sound(SoundType.METAL))
            ,CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> ITEM_COMPRESSOR = registerBlock("item_compressor",
            () -> new ItemCompressorBlock(
                    BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(4f,4f)
                            .requiresCorrectToolForDrops().sound(SoundType.METAL)),CreativeModeTab.TAB_DECORATIONS);


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        ModItems.ITEM_DEFERRED_REGISTER.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends  Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCK_DEFERRED_REGISTER.register(name,block);
        registerBlockItem(name,toReturn ,tab);
        return toReturn;
    }
    public static void registerBlocks(IEventBus eventBus){
        BLOCK_DEFERRED_REGISTER.register(eventBus);
    }
}
