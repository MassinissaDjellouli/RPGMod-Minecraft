package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.thirst.PlayerThirstProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameType;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class DrankC2SPacket {
    public DrankC2SPacket(){

    }
    public DrankC2SPacket(FriendlyByteBuf buf){

    }
    public void toBytes(FriendlyByteBuf buf){}

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(
                () ->{
                    ServerPlayer player = context.getSender();
                    GameType gameModeForPlayer = player.gameMode.getGameModeForPlayer();
                    if(gameModeForPlayer.isCreative()){
                        return;
                    }
                    player.getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(
                            playerThirst -> {
                                playerThirst.addThirst(3);
                            }
                    );
                }
        );
    return true;
    }
}
