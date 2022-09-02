package com.massinissadjellouli.RPGmod.networking;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.networking.packet.DrankC2SPacket;
import com.massinissadjellouli.RPGmod.networking.packet.JumpReduceThirstC2SPacket;
import com.massinissadjellouli.RPGmod.networking.packet.ReduceThirstByTickC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }

    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(RPGMod.MODID,"packets"))
                .networkProtocolVersion(()->"1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;

        net.messageBuilder(DrankC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DrankC2SPacket::new)
                .encoder(DrankC2SPacket::toBytes)
                .consumerMainThread(DrankC2SPacket::handle)
                .add();
        net.messageBuilder(JumpReduceThirstC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(JumpReduceThirstC2SPacket::new)
                .encoder(JumpReduceThirstC2SPacket::toBytes)
                .consumerMainThread(JumpReduceThirstC2SPacket::handle)
                .add();
        net.messageBuilder(ReduceThirstByTickC2SPacket.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReduceThirstByTickC2SPacket::new)
                .encoder(ReduceThirstByTickC2SPacket::toBytes)
                .consumerMainThread(ReduceThirstByTickC2SPacket::handle)
                .add();
    }

    public static <T> void sendToServer(T message){
        INSTANCE.sendToServer(message);
    }
    public static <T> void sendToPlayer(T message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),message);
    }
}
