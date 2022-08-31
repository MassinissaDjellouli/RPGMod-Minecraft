package com.massinissadjellouli.RPGmod.item;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ToolTiers {
    public static final Tier COPPER =
            new ForgeTier(2,875,9,2f,13, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier STEEL =
            new ForgeTier(2,2100,8,2.5f,19, BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModItems.STEEL_INGOT.get()));
    public static final Tier TITANIUM =
            new ForgeTier(4,1900,12,5f,22, BlockTags.NEEDS_DIAMOND_TOOL,//Ajouter nouveau tag
            () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()));
    public static final Tier REINFORCED_TITANIUM =
            new ForgeTier(5,5095,14,6f,25, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.REINFORCED_TITANIUM_INGOT.get()));
}
