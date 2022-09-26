package com.massinissadjellouli.RPGmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import static com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity.AMOUNT_OF_SLOTS_TO_COMPRESS;

public class ItemCompressorRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private  int count;
    private  int duration;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItem;

    public ItemCompressorRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItem,int count,int duration) {
        this.id = id;
        this.count = count;
        this.duration = duration;
        this.output = output;
        this.recipeItem = recipeItem;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public int getDuration(){
        return duration;
    }
    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()){
            return false;
        }

        boolean isValid = true;
        for (int i = 0; i < AMOUNT_OF_SLOTS_TO_COMPRESS; i++) {
                    isValid = isValid && recipeItem.get(i).test(pContainer.getItem(i)) &&
                            recipeItem.get(i).getItems()[0].getCount() <= pContainer.getItem(i).getCount();
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

    public int getCount() {
        return count;
    }

    public static class Type implements RecipeType<ItemCompressorRecipe> {
        private Type(){}
        public static final Type INSTANCE = new Type();
        public static final String ID = "item_compressing";
    }

    public static class Serializer implements RecipeSerializer<ItemCompressorRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final  ResourceLocation ID = new ResourceLocation(RPGMod.MODID,"item_compressing");

        @Override
        public ItemCompressorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe,"output"));

            int count = GsonHelper.getAsInt(pSerializedRecipe,"count");
            int duration = GsonHelper.getAsInt(pSerializedRecipe,"duration");

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe,"ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(AMOUNT_OF_SLOTS_TO_COMPRESS,Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i));
                ingredient.getItems()[0].setCount(count);
                inputs.set(i,ingredient);
            }
            return new ItemCompressorRecipe(pRecipeId,output,inputs,count,duration);
        }

        @Override
        public @Nullable ItemCompressorRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(),Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i,Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            int count = pBuffer.readInt();
            int duration = pBuffer.readInt();
            return new ItemCompressorRecipe(pRecipeId,output,inputs,count,duration);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ItemCompressorRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ing: pRecipe.getIngredients()) {
                ing.toNetwork(pBuffer);
            }
            pBuffer.writeItemStack(pRecipe.getResultItem(),false);
            pBuffer.writeInt(pRecipe.count);
            pBuffer.writeInt(pRecipe.duration);
        }
    }
}
