package com.massinissadjellouli.RPGmod.world.features;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE_DEFERRED_REGISTER =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, RPGMod.MODID);

    public static final RegistryObject<PlacedFeature> TITANIUM_OVERWORLD_ORE = PLACED_FEATURE_DEFERRED_REGISTER.register(
            "titanium_overworld_ore",
            ()-> new PlacedFeature(ModConfiguredFeatures.TITANIUM_OVERWORLD_ORE.getHolder().get(),
                    commonOrePlacement(4,HeightRangePlacement.triangle(
                            VerticalAnchor.bottom(),VerticalAnchor.absolute(10)
                    )))
    );

    private static List<PlacementModifier> commonOrePlacement(int countPerChunks,PlacementModifier height){
        return orePlacement(CountPlacement.of(countPerChunks),height);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier count, PlacementModifier height){
        return List.of(count, InSquarePlacement.spread(),height, BiomeFilter.biome());
    }

    public static void registerPlacedFeatures(IEventBus eventBus){
        PLACED_FEATURE_DEFERRED_REGISTER.register(eventBus);
    }
}
