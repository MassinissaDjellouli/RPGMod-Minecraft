package com.massinissadjellouli.RPGmod.world.features;

import com.google.common.base.Suppliers;
import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.block.ModBlocks;
import com.massinissadjellouli.RPGmod.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?,?>> CONFIGURED_FEATURE_DEFERRED_REGISTER =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, RPGMod.MODID);

    private static final Supplier<List<OreConfiguration.TargetBlockState>> TITANIUM_OVERWORLD_REPLACEMENT = Suppliers.memoize(
            ()-> List.of(
                    OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, ModBlocks.TITANIUM_ORE.get().defaultBlockState()),
                    OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_TITANIUM_ORE.get().defaultBlockState())
            )
    );

    //Fields:oreReplacement,count per veins
    public static final RegistryObject<ConfiguredFeature<?,?>> TITANIUM_OVERWORLD_ORE =
            CONFIGURED_FEATURE_DEFERRED_REGISTER.register("titanium_overworld_ore",
                    ()->  new ConfiguredFeature<>(
                            Feature.ORE, new OreConfiguration(TITANIUM_OVERWORLD_REPLACEMENT.get(),15)
                    ));
    public static void registerConfiguredFeatures(IEventBus eventBus){
        CONFIGURED_FEATURE_DEFERRED_REGISTER.register(eventBus);
    }
}
