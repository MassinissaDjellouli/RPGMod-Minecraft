package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.client.ClientThirstData;
import com.massinissadjellouli.RPGmod.events.ClientEvents;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KilledBySwordEffectC2SPacket {
    final EntityType<?> entity;
    public KilledBySwordEffectC2SPacket(EntityType entity) {
        this.entity = entity;
    }

    public KilledBySwordEffectC2SPacket(FriendlyByteBuf buf) {
        entity = EntityType.byString(buf.readUtf()).get();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(EntityType.getKey(entity).toString());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();



        context.enqueueWork(() -> {
            ClientEvents.givePlayerKillXp(entity,player);
        });
        return true;
    }

}
