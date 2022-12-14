package com.massinissadjellouli.RPGmod.networking;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.networking.packet.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RPGMod.MODID, "packets"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(DrankC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrankC2SPacket::new)
                .encoder(DrankC2SPacket::toBytes)
                .consumerMainThread(DrankC2SPacket::handle)
                .add();
        net.messageBuilder(KilledBySwordEffectC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(KilledBySwordEffectC2SPacket::new)
                .encoder(KilledBySwordEffectC2SPacket::toBytes)
                .consumerMainThread(KilledBySwordEffectC2SPacket::handle)
                .add();

        net.messageBuilder(JumpReduceThirstC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(JumpReduceThirstC2SPacket::new)
                .encoder(JumpReduceThirstC2SPacket::toBytes)
                .consumerMainThread(JumpReduceThirstC2SPacket::handle)
                .add();

        net.messageBuilder(ReduceThirstByTickC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReduceThirstByTickC2SPacket::new)
                .encoder(ReduceThirstByTickC2SPacket::toBytes)
                .consumerMainThread(ReduceThirstByTickC2SPacket::handle)
                .add();

        net.messageBuilder(ThirstDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ThirstDataSyncS2CPacket::new)
                .encoder(ThirstDataSyncS2CPacket::toBytes)
                .consumerMainThread(ThirstDataSyncS2CPacket::handle)
                .add();
        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();

        net.messageBuilder(GamemodeDataSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GamemodeDataSyncC2SPacket::new)
                .encoder(GamemodeDataSyncC2SPacket::toBytes)
                .consumerMainThread(GamemodeDataSyncC2SPacket::handle)
                .add();

        net.messageBuilder(ThirstEffectC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ThirstEffectC2SPacket::new)
                .encoder(ThirstEffectC2SPacket::toBytes)
                .consumerMainThread(ThirstEffectC2SPacket::handle)
                .add();
        net.messageBuilder(OpenClassMenuC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenClassMenuC2SPacket::new)
                .encoder(OpenClassMenuC2SPacket::toBytes)
                .consumerMainThread(OpenClassMenuC2SPacket::handle)
                .add();
        net.messageBuilder(OpenLinkAccountMenuC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OpenLinkAccountMenuC2SPacket::new)
                .encoder(OpenLinkAccountMenuC2SPacket::toBytes)
                .consumerMainThread(OpenLinkAccountMenuC2SPacket::handle)
                .add();
        net.messageBuilder(ChangeClassC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChangeClassC2SPacket::new)
                .encoder(ChangeClassC2SPacket::toBytes)
                .consumerMainThread(ChangeClassC2SPacket::handle)
                .add();
        net.messageBuilder(SendUserStatsToBackendC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SendUserStatsToBackendC2SPacket::new)
                .encoder(SendUserStatsToBackendC2SPacket::toBytes)
                .consumerMainThread(SendUserStatsToBackendC2SPacket::handle)
                .add();
        net.messageBuilder(UpdatePlayerUIDBackendC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UpdatePlayerUIDBackendC2SPacket::new)
                .encoder(UpdatePlayerUIDBackendC2SPacket::toBytes)
                .consumerMainThread(UpdatePlayerUIDBackendC2SPacket::handle)
                .add();
    }

    public static <T> void sendToServer(T message) {
        if (Minecraft.getInstance().getConnection() == null) {
            return;
        }
        INSTANCE.sendToServer(message);
    }

    public static <T> void sendToPlayer(T message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <T> void sendToClients(T message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
