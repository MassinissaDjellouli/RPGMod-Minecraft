package com.massinissadjellouli.RPGmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.tags.RarityTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.massinissadjellouli.RPGmod.block.entities.RarityTableBlockEntity.*;
import static com.massinissadjellouli.RPGmod.tags.RarityTags.*;

public class RarityTableRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final RarityTags rarity;
    private final Map<Integer, Ingredient> recipePattern;

    public RarityTableRecipe(ResourceLocation id, RarityTags rarity, Map<Integer, Ingredient> recipePattern) {
        this.id = id;
        this.rarity = rarity;
        this.recipePattern = recipePattern;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide() || recipePattern.size() != AMOUNT_OF_SLOTS_TO_INSERT) {
            return false;
        }
        if (itemHasNoRarity(pContainer.getItem(POSITION_OF_ITEM_SLOT))) {
            return false;
        }
        List<Boolean> validSlots = new ArrayList<>();
        recipePattern.forEach((i, ingredient) -> {
            validSlots.add(ingredient.test(pContainer.getItem(i)));
        });
        validSlots.add(getTag(
                getItemRarity(
                        pContainer.getItem(POSITION_OF_ITEM_SLOT))) == rarity);
        return validSlots.stream().allMatch(aBoolean -> aBoolean);
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<RarityTableRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();
        public static final String ID = "item_rarifying";
    }

    public static class Serializer implements RecipeSerializer<RarityTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(RPGMod.MODID, "item_rarifying");

        @Override
        public RarityTableRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            int level = GsonHelper.getAsInt(pSerializedRecipe, "inputRarityLevel");

            RarityTags rarity = getTagByLevel(level);
            JsonObject keyIngredientMap = GsonHelper.getAsJsonObject(pSerializedRecipe, "keys");
            JsonArray pattern = GsonHelper.getAsJsonArray(pSerializedRecipe, "pattern");

            Map<String, ItemStack> map = new HashMap<>();
            keyIngredientMap.entrySet().forEach(
                    val -> map.put(val.getKey(), ShapedRecipe.itemStackFromJson(GsonHelper.parse(
                                            "{" +
                                                    "\"item\" : \"" + val.getValue().getAsString()
                                                    + "\"}"
                                    )
                            )
                    )
            );
            int currentCol = 0;
            int currentPos = -3;
            int currentRow = 0;
            Map<Integer, Ingredient> ingredientMap = new HashMap<>();
            for (int i = 0; i < pattern.size(); i++) {
                if (currentCol == AMOUNT_OF_SLOTS_TO_INSERT_COLS) {
                    currentCol = 1;
                    currentRow++;
                    currentPos = 0;
                } else {
                    currentCol++;
                    currentPos += 3;
                }
                JsonElement jsonElement = pattern.get(i);
                ItemStack itemStack = map.get(jsonElement.getAsString());
                ingredientMap.put(currentRow + currentPos, Ingredient.of(itemStack));
            }
            return new RarityTableRecipe(pRecipeId, rarity, ingredientMap);
        }

        @Override
        public @Nullable RarityTableRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Map<Integer, Ingredient> inputs = new HashMap<>();

            for (int i = 0; i < pBuffer.readInt(); i++) {
                inputs.put(i, Ingredient.fromNetwork(pBuffer));
            }

            int level = pBuffer.readInt();
            return new RarityTableRecipe(pRecipeId, getTagByLevel(level), inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, RarityTableRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            pRecipe.recipePattern.forEach((i, ingredient) -> {
                ingredient.toNetwork(pBuffer);
            });

            pBuffer.writeInt(pRecipe.rarity.level);
        }
    }
}
