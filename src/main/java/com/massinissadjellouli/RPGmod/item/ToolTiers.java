package com.massinissadjellouli.RPGmod.item;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.registries.DeferredRegister;

import java.util.List;

public class ToolTiers {
    public static final Tier COPPER =
            new ForgeTier(2,875,9,2f,13, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier STEEL =
            new ForgeTier(2,2100,8,2.5f,19, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModItems.STEEL_INGOT.get()));
    public static final Tier TITANIUM =
            new ForgeTier(5,1900,12,5f,22, ModTags.Blocks.NEEDS_TITANIUM_TOOLS,
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()));
    public static final Tier REINFORCED_TITANIUM =
            new ForgeTier(5,5095,14,6f,25, ModTags.Blocks.NEEDS_TITANIUM_TOOLS,
            () -> Ingredient.of(ModItems.REINFORCED_TITANIUM_INGOT.get()));

    public static void registerTiers() {
        TierSortingRegistry.registerTier(COPPER,new ResourceLocation(RPGMod.MODID,"copper"),
                List.of(Tiers.IRON),
                List.of(Tiers.DIAMOND));
        TierSortingRegistry.registerTier(TITANIUM,new ResourceLocation(RPGMod.MODID,"titanium"),
                List.of(Tiers.NETHERITE),
                List.of()
        );
        TierSortingRegistry.registerTier(REINFORCED_TITANIUM,new ResourceLocation(RPGMod.MODID,"reinforced_titanium"),
                List.of(TITANIUM),
                List.of()
        );
    }
}
