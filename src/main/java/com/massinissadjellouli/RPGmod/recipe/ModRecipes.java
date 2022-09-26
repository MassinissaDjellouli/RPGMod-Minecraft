package com.massinissadjellouli.RPGmod.recipe;

import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.massinissadjellouli.RPGmod.recipe.ItemCompressorRecipe.Serializer.INSTANCE;

public class ModRecipes {
    public static DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,RPGMod.MODID);

    public static final RegistryObject<RecipeSerializer<ItemCompressorRecipe>> ITEM_COMPRESSOR_SERIALIZER =
            SERIALIZER.register("item_compressing", () -> INSTANCE);
    public static void register(IEventBus eventBus){
        SERIALIZER.register(eventBus);
    }
}
