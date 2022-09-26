package com.massinissadjellouli.RPGmod.block.custom;

import com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity;
import com.massinissadjellouli.RPGmod.block.entities.ModBlockEntities;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import com.massinissadjellouli.RPGmod.tags.ModTags.Items.CompressorFuels;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

public class ItemCompressorBlock extends BaseEntityBlock {


    public ItemCompressorBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ItemCompressorBlockEntity(pos,state);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ItemCompressorBlockEntity) {
                ((ItemCompressorBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState,pLevel,pPos,pNewState,pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()){
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof ItemCompressorBlockEntity blockEntity){
                if(pPlayer.hasItemInSlot(EquipmentSlot.MAINHAND)){
                    ItemStack itemInHand = pPlayer.getItemBySlot(EquipmentSlot.MAINHAND);
                    List<CompressorFuels> compressorFuelsList = Arrays.stream(CompressorFuels.values())
                            .filter(compressorFuels -> itemInHand.is(compressorFuels.item)).toList();
                    if(compressorFuelsList.isEmpty()){
                        NetworkHooks.openScreen((ServerPlayer) pPlayer, blockEntity, pPos);
                    }else{
                        int energy = compressorFuelsList.get(0).energy;
                        int count = 1;
                        if(Screen.hasControlDown()){
                            count = itemInHand.getCount();
                            energy *= count;
                        }
                        if(itemInHand.is(CompressorFuels.LAVA.item)){
                            pPlayer.setItemInHand(MAIN_HAND, new ItemStack(Items.BUCKET));
                        }else if(itemInHand.getCount() > 1){
                            itemInHand.setCount(itemInHand.getCount() - count);
                            pPlayer.setItemInHand(MAIN_HAND, itemInHand);
                        }else{
                            pPlayer.setItemInHand(MAIN_HAND,ItemStack.EMPTY);
                        }
                        blockEntity.addEnergy(((ItemCompressorBlockEntity) entity),energy );

                    }
                }else{
                    NetworkHooks.openScreen((ServerPlayer) pPlayer, (ItemCompressorBlockEntity) entity, pPos);
                }
            }else {
                throw new IllegalStateException("Container provider manquant");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ITEM_COMPRESSOR.get(),ItemCompressorBlockEntity::tick);
    }
}