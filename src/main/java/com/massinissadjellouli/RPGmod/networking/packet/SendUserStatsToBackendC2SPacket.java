package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.classSystem.PlayerClassType;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerUIDCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendUserStatsToBackendC2SPacket {

    public SendUserStatsToBackendC2SPacket() {}

    public SendUserStatsToBackendC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
//            player.getCapability(PlayerUIDCapabilityProvider.PLAYER_UID).ifPresent(System.out::println);
        });
        return true;
    }

//8531a659-a219-47ce-b978-02826042a198
}
