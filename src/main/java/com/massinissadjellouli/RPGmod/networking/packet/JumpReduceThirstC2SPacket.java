package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JumpReduceThirstC2SPacket {
    public JumpReduceThirstC2SPacket() {

    }

    public JumpReduceThirstC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(
                () -> {
                    ServerPlayer player = context.getSender();
                    if (player != null && player.gameMode.getGameModeForPlayer().isSurvival()) {
                        PlayerThirst.setReduceByTick(PlayerThirst.getReduceByTick() + 2.4f);
                    }
                }
        );
        return true;
    }
}
