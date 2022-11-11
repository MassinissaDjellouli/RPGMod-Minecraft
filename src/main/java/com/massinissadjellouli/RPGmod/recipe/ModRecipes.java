package com.massinissadjellouli.RPGmod.recipe;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModRecipes {
    public static DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RPGMod.MODID);

    public static final RegistryObject<RecipeSerializer<ItemCompressorRecipe>> ITEM_COMPRESSOR_SERIALIZER =
            SERIALIZER.register("item_compressing", () -> ItemCompressorRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<RarityTableRecipe>> RARITY_TABLE_SERIALIZER =
            SERIALIZER.register("item_rarifying", () -> RarityTableRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
    }
}
