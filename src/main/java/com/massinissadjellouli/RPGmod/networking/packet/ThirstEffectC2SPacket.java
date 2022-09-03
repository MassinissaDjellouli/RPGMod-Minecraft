package com.massinissadjellouli.RPGmod.networking.packet;

import com.massinissadjellouli.RPGmod.client.ClientThirstData;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ThirstEffectC2SPacket {
    public ThirstEffectC2SPacket() {

    }

    public ThirstEffectC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        ServerPlayer player = context.getSender();



        context.enqueueWork(() -> {
            float attributeMultiplier;
            if (ClientThirstData.get() >= PlayerThirst.MAX_THIRST / 2)
                attributeMultiplier = 0.7f;
            else if (ClientThirstData.get() >= PlayerThirst.MAX_THIRST / 4)
                attributeMultiplier = 0.3f;
            else
                attributeMultiplier = 0;

            final AttributeModifier ATTACK_MODIFIER =
                    new AttributeModifier( UUID.fromString("75a87111-8a82-4517-b68c-ff95ecc5be6b"), "MULTIPLY_MODIFIER",
                            attributeMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);

            final AttributeModifier MOVE_MODIFIER =
                    new AttributeModifier( UUID.fromString("0b73b5a1-6503-434a-ba6b-c563f2979286"), "MULTIPLY_MODIFIER",
                            attributeMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);

            if (player.gameMode.isSurvival()) {
                    removeModifiers(player, ATTACK_MODIFIER,MOVE_MODIFIER);
                    addModifiers(player, ATTACK_MODIFIER,MOVE_MODIFIER);

            }

        });
        return true;
    }

    private void addModifiers(ServerPlayer player, AttributeModifier ATTACK_MODIFIER,AttributeModifier MOVE_MODIFIER) {
        if(!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MOVE_MODIFIER))
            player.getAttribute(Attributes.MOVEMENT_SPEED).addPermanentModifier(MOVE_MODIFIER);
        if(!player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(ATTACK_MODIFIER))
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(ATTACK_MODIFIER);
    }

    private void removeModifiers(ServerPlayer player,  AttributeModifier ATTACK_MODIFIER,AttributeModifier MOVE_MODIFIER) {
        if(player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(MOVE_MODIFIER))
            player.getAttribute(Attributes.MOVEMENT_SPEED).removePermanentModifier(MOVE_MODIFIER.getId());
        if(player.getAttribute(Attributes.ATTACK_DAMAGE).hasModifier(ATTACK_MODIFIER))
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_MODIFIER);
    }


}
