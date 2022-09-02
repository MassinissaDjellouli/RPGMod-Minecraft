package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.thirst.PlayerThirstProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReduceThirstByTickC2SPacket {
        public ReduceThirstByTickC2SPacket(){

        }
        public ReduceThirstByTickC2SPacket(FriendlyByteBuf buf){

        }
        public void toBytes(FriendlyByteBuf buf){}

        public boolean handle(Supplier<NetworkEvent.Context> supplier){
            NetworkEvent.Context context = supplier.get();

            context.enqueueWork(
                    () ->{
                        ServerPlayer player = context.getSender();
                        if (player != null && player.gameMode.getGameModeForPlayer().isSurvival()) {
                            player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(
                                    playerThirst ->{
                                    if(playerThirst.getThirstLevel() > 0){
                                    playerThirst.reduceTicks();
                                    }
                                }
                            );
                        }
                    }
            );
            return true;
        }
    }


