package com.massinissadjellouli.RPGmod.block.entities;

import com.massinissadjellouli.RPGmod.energy.ModEnergyStorage;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.EnergySyncS2CPacket;
import com.massinissadjellouli.RPGmod.recipe.ItemCompressorRecipe;
import com.massinissadjellouli.RPGmod.screen.ItemCompressorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemCompressorBlockEntity extends BlockEntity implements MenuProvider {

    public static final int AMOUNT_OF_SLOTS_TO_COMPRESS_ROWS = 3;
    public static final int AMOUNT_OF_SLOTS_TO_COMPRESS_COLS = 3;
    public static final int AMOUNT_OF_SLOTS_TO_COMPRESS = AMOUNT_OF_SLOTS_TO_COMPRESS_ROWS * AMOUNT_OF_SLOTS_TO_COMPRESS_COLS;
    public static final int POSITION_OF_RESULT_SLOT = 9;
    public static final int AMOUNT_OF_SLOTS = 10;
    private int progress;
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(10000,300) {
        @Override
        public void onEnergyChanged() {

            setChanged();
            ModPackets.sendToClients(new EnergySyncS2CPacket(energy,getBlockPos()));
        }
    };
    public static final int ENERGY_REQUIREMENT = 10;
    protected final ContainerData data;
    private int maxProgress = 200;
    public ItemCompressorBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.ITEM_COMPRESSOR.get(), p_155229_, p_155230_);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> ItemCompressorBlockEntity.this.progress;
                    case 1 -> ItemCompressorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0 -> ItemCompressorBlockEntity.this.progress = value;
                    case 1 -> ItemCompressorBlockEntity.this.maxProgress = value;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(AMOUNT_OF_SLOTS){
        @Override
        protected void onContentsChanged(int slot){
            setChanged();
        }
    };


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    @Override
    public Component getDisplayName() {
        return Component.literal("Compresseur a item");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ItemCompressorMenu(id,inventory,this,this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @NotNull Direction side) {
        if(cap == ForgeCapabilities.ENERGY){
            return lazyEnergyHandler.cast();

        }
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap,side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(()->ENERGY_STORAGE);
        lazyItemHandler = LazyOptional.of(()->itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory",itemStackHandler.serializeNBT());
        nbt.putInt("energy",ENERGY_STORAGE.getEnergyStored());
        nbt.putInt("item_compressor_progress",this.progress);
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));
        this.progress = nbt.getInt("item_compressor_progress");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i,itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public static void tick(Level level, BlockPos blockPos,
                                                    BlockState state, ItemCompressorBlockEntity itemCompressor) {

        if(level.isClientSide()) return;
        if(hasRecipe(itemCompressor) && hasEnergy(itemCompressor)){
            Optional<ItemCompressorRecipe> recipe = getRecipe(itemCompressor);
            itemCompressor.maxProgress = recipe.get().getDuration() * 20;
            itemCompressor.progress++;
            extractEnergy(itemCompressor);
            setChanged(level,blockPos,state);
            if(itemCompressor.progress >= itemCompressor.maxProgress){
                craftItem(itemCompressor);
            }
        }else{
            itemCompressor.resetProgress();
        }
    }

    private static void extractEnergy(ItemCompressorBlockEntity itemCompressor) {
        itemCompressor.ENERGY_STORAGE.extractEnergy(ENERGY_REQUIREMENT,false);
    }

    private static boolean hasEnergy(ItemCompressorBlockEntity itemCompressor) {
        return itemCompressor.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQUIREMENT * itemCompressor.maxProgress;
    }

    private static Optional<ItemCompressorRecipe> getRecipe(ItemCompressorBlockEntity itemCompressor) {
        Level level = itemCompressor.level;
        SimpleContainer inventory = new SimpleContainer(itemCompressor.itemStackHandler.getSlots());
        for (int i = 0; i < itemCompressor.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i,itemCompressor.itemStackHandler.getStackInSlot(i));
        }
        return level.getRecipeManager().getRecipeFor(ItemCompressorRecipe.Type.INSTANCE,inventory,level);
    }

    private static boolean hasRecipe(ItemCompressorBlockEntity itemCompressor) {
        SimpleContainer inventory = new SimpleContainer(itemCompressor.itemStackHandler.getSlots());
        for (int i = 0; i < itemCompressor.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i,itemCompressor.itemStackHandler.getStackInSlot(i));
        }

        Level level = itemCompressor.level;

        Optional<ItemCompressorRecipe> recipe = level.getRecipeManager().getRecipeFor(ItemCompressorRecipe.Type.INSTANCE,inventory,level);

        return recipe.isPresent()  && canInsertAmountIntoResultSlot(inventory)
                && canInsertItemIntoResultSlot(inventory, recipe.get().getResultItem());
    }

    private static boolean canInsertAmountIntoResultSlot(SimpleContainer inventory) {
        return inventory.getItem(POSITION_OF_RESULT_SLOT).getMaxStackSize() > inventory.getItem(POSITION_OF_RESULT_SLOT).getCount();
    }

    private static boolean canInsertItemIntoResultSlot(SimpleContainer inventory, ItemStack itemStack) {
        return inventory.getItem(POSITION_OF_RESULT_SLOT).getItem() == itemStack.getItem() || inventory.getItem(POSITION_OF_RESULT_SLOT).isEmpty();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(ItemCompressorBlockEntity itemCompressor) {
        Level level = itemCompressor.level;

        Optional<ItemCompressorRecipe> recipe = getRecipe(itemCompressor);
        if(hasRecipe(itemCompressor)  && hasEnergy(itemCompressor)){
            //TODO: craft right item
            for (int i = 0; i < AMOUNT_OF_SLOTS_TO_COMPRESS; i++) {
                itemCompressor.itemStackHandler.extractItem(i,recipe.get().getCount(),false);
            }
            itemCompressor.itemStackHandler.setStackInSlot(POSITION_OF_RESULT_SLOT,new ItemStack(recipe.get().getResultItem().getItem(),
                    itemCompressor.itemStackHandler.getStackInSlot(POSITION_OF_RESULT_SLOT).getCount()
                            + recipe.get().getResultItem().getCount()));
            itemCompressor.resetProgress();
        }
    }

    public void addEnergy(ItemCompressorBlockEntity entity, int energy) {
        entity.ENERGY_STORAGE.receiveEnergy(energy,false);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLvl(int energy) {
        ENERGY_STORAGE.setEnergy(energy);
    }
}
