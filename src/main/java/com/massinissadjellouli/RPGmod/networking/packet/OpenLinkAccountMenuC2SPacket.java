package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.screen.ClassChangeMenu;
import com.massinissadjellouli.RPGmod.screen.LinkAccountMenu;
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

public class OpenLinkAccountMenuC2SPacket {
    public OpenLinkAccountMenuC2SPacket() {

    }

    public OpenLinkAccountMenuC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();


        context.enqueueWork(() -> {
            NetworkHooks.openScreen(player, new MenuProvider() {

                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    return new LinkAccountMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(player.blockPosition()));
                }
            }, player.blockPosition());
        });
        return true;
    }


}
