package com.massinissadjellouli.RPGmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.massinissadjellouli.RPGmod.RPGMod;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import static com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity.AMOUNT_OF_SLOTS_TO_COMPRESS;

public class RarityTableRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItem;

    public RarityTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItem) {
        this.id = id;
        this.output = output;
        this.recipeItem = recipeItem;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }

        boolean isValid = true;
        switch (recipeItem.size()){
            case 1 -> {
                isValid = false;
                for (int i = 0; i < AMOUNT_OF_SLOTS_TO_COMPRESS; i++) {
                    isValid = isValid || recipeItem.get(0).test(pContainer.getItem(i)) &&
                            recipeItem.get(0).getItems()[0].getCount() <= pContainer.getItem(i).getCount();
                }
            }
            case AMOUNT_OF_SLOTS_TO_COMPRESS -> {
                for (int i = 0; i < AMOUNT_OF_SLOTS_TO_COMPRESS; i++) {
                    isValid = isValid && recipeItem.get(i).test(pContainer.getItem(i)) &&
                            recipeItem.get(i).getItems()[0].getCount() <= pContainer.getItem(i).getCount();
                }
            }
        }

        return isValid;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
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
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "item_rarifying";
    }

    public static class Serializer implements RecipeSerializer<RarityTableRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final  ResourceLocation ID = new ResourceLocation(RPGMod.MODID,"item_rarifying");

        @Override
        public RarityTableRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe,"output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe,"ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(),Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i));
                ingredient.getItems()[0].setCount(1);
                inputs.set(i,ingredient);
            }
            return new RarityTableRecipe(pRecipeId,output,inputs);
        }

        @Override
        public @Nullable RarityTableRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(),Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i,Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new RarityTableRecipe(pRecipeId,output,inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, RarityTableRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing: pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(),false);
        }
    }
}
