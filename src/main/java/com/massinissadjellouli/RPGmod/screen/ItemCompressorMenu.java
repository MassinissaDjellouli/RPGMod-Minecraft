package com.massinissadjellouli.RPGmod.screen;

import com.massinissadjellouli.RPGmod.block.ModBlocks;
import com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import static com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity.*;

public class ItemCompressorMenu extends AbstractContainerMenu {
    public final ItemCompressorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public ItemCompressorMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory, inventory.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public ItemCompressorMenu(int id, Inventory inventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.ITEM_COMPRESSOR_MENU.get(), id);
        checkContainerSize(inventory, AMOUNT_OF_SLOTS);
        blockEntity = (ItemCompressorBlockEntity) entity;
        this.level = inventory.player.level;
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            for (int i = 0; i < AMOUNT_OF_SLOTS_TO_COMPRESS_ROWS; i++) {
                for (int y = 0; y < AMOUNT_OF_SLOTS_TO_COMPRESS_COLS; y++) {
                    this.addSlot(new SlotItemHandler(handler,i * AMOUNT_OF_SLOTS_TO_COMPRESS_ROWS + y,
                            25 + i * 18,18 + y * 18));
                }
            }
            this.addSlot(new SlotItemHandler(handler,POSITION_OF_RESULT_SLOT,115,36){

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        });

        addDataSlots(data);
    }
    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = AMOUNT_OF_SLOTS;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + index);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.ITEM_COMPRESSOR.get());
    }

    private void addPlayerInventory(Inventory inventory) {
        for (int i = 0; i < 3; i++) {
            for (int y = 0; y < 9; y++) {
                this.addSlot(new Slot(inventory, y + i * 9 + 9, 8 + y * 18,84 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory inventory) {
            for (int i = 0; i < 9; i++) {
                this.addSlot(new Slot(inventory,i , 8 + i * 18, 142));
            }
    }

    public boolean isCrafting(){
        return data.get(0) > 0;
    }

    public int getScaledProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressBarSize = 53;

        return maxProgress != 0 && progress != 0? progress * progressBarSize / maxProgress : 0;

    }
}
