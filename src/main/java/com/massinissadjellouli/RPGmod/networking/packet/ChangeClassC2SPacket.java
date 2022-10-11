package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.classSystem.PlayerClassProvider;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassType;
import com.massinissadjellouli.RPGmod.screen.ClassChangeMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

import static com.massinissadjellouli.RPGmod.classSystem.PlayerClassType.MINEUR;

public class ChangeClassC2SPacket {
    private final PlayerClassType newClass;
    public ChangeClassC2SPacket(PlayerClassType newClass) {
        this.newClass = newClass;
    }

    public ChangeClassC2SPacket(FriendlyByteBuf buf) {
        this.newClass = PlayerClassType.valueOf(buf.readUtf());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(newClass.toString());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();
        context.enqueueWork(() -> {
            player.getCapability(PlayerClassProvider.PLAYER_CLASS).ifPresent(playerClass ->
                    playerClass.change(newClass));
    });
        return true;
    }



}
