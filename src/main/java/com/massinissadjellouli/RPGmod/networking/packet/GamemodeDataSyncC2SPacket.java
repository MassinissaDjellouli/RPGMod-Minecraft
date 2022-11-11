package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GamemodeDataSyncC2SPacket {

    public GamemodeDataSyncC2SPacket() {

    }

    public GamemodeDataSyncC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {

            ClientGamemodeData.setIsSurvival(context.getSender().gameMode.isSurvival());
        });
        return true;
    }
}
