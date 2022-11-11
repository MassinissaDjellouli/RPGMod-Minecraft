package com.massinissadjellouli.RPGmod.item;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

import static com.massinissadjellouli.RPGmod.tags.ModTags.Blocks.*;

public class ToolTiers {
    public static final Tier COPPER =
            new ForgeTier(2, 875, 9, 2f, 13, NEEDS_COPPER_TOOLS,
                    () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier STEEL =
            new ForgeTier(2, 2100, 8, 2.5f, 19, NEEDS_STEEL_TOOLS,
                    () -> Ingredient.of(ModItems.STEEL_INGOT.get()));
    public static final Tier TITANIUM =
            new ForgeTier(5, 1900, 12, 5f, 22, NEEDS_TITANIUM_TOOLS,
                    () -> Ingredient.of(ModItems.TITANIUM_INGOT.get()));
    public static final Tier REINFORCED_TITANIUM =
            new ForgeTier(5, 5095, 14, 6f, 25, NEEDS_REINFORCED_TITANIUM_TOOLS,
                    () -> Ingredient.of(ModItems.REINFORCED_TITANIUM_INGOT.get()));
    public static final Tier HELL_ALLOY =
            new ForgeTier(6, 16000, 16, 25f, 25, null, Ingredient::of);
    public static final Tier DIVINE_ALLOY =
            new ForgeTier(7, 26000, 16, 55f, 25, null, Ingredient::of);

    public static void registerTiers() {
        TierSortingRegistry.registerTier(COPPER, new ResourceLocation(RPGMod.MODID, "copper"),
                List.of(Tiers.IRON),
                List.of(Tiers.DIAMOND));
        TierSortingRegistry.registerTier(TITANIUM, new ResourceLocation(RPGMod.MODID, "titanium"),
                List.of(Tiers.NETHERITE),
                List.of()
        );
        TierSortingRegistry.registerTier(REINFORCED_TITANIUM, new ResourceLocation(RPGMod.MODID, "reinforced_titanium"),
                List.of(TITANIUM),
                List.of()
        );
        TierSortingRegistry.registerTier(HELL_ALLOY, new ResourceLocation(RPGMod.MODID, "hell_alloy"),
                List.of(REINFORCED_TITANIUM),
                List.of()
        );
        TierSortingRegistry.registerTier(DIVINE_ALLOY, new ResourceLocation(RPGMod.MODID, "divine_alloy"),
                List.of(HELL_ALLOY),
                List.of()
        );
    }
}
