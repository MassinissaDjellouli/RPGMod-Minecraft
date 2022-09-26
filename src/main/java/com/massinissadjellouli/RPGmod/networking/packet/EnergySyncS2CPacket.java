package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.block.entities.ItemCompressorBlockEntity;
import com.massinissadjellouli.RPGmod.client.ClientThirstData;
import com.massinissadjellouli.RPGmod.screen.ItemCompressorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {

        private final int energy;
        private final BlockPos pos;
        public EnergySyncS2CPacket(int energy,BlockPos pos){
            this.energy = energy;
            this.pos = pos;

        }
        public EnergySyncS2CPacket(FriendlyByteBuf buf){
            this.energy = buf.readInt();
            this.pos = buf.readBlockPos();

        }

        public void toBytes(FriendlyByteBuf buf){
            buf.writeInt(energy);
            buf.writeBlockPos(pos);
        }

        public boolean handle(Supplier<NetworkEvent.Context> supplier){
            NetworkEvent.Context context = supplier.get();

            context.enqueueWork(()->{

                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ItemCompressorBlockEntity blockEntity){
                    blockEntity.setEnergyLvl(energy);
                    if(Minecraft.getInstance().player.containerMenu
                            instanceof ItemCompressorMenu menu && menu.blockEntity.getBlockPos().equals(pos)){
                        blockEntity.setEnergyLvl(energy);
                    }
                }
            });
            return true;
        }




}