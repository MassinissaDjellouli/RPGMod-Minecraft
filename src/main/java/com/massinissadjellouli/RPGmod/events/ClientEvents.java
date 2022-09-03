package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ClientThirstData;
import com.massinissadjellouli.RPGmod.client.ThirstHudOverlay;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.DrankC2SPacket;
import com.massinissadjellouli.RPGmod.networking.packet.GamemodeDataSyncC2SPacket;
import com.massinissadjellouli.RPGmod.networking.packet.JumpReduceThirstC2SPacket;
import com.massinissadjellouli.RPGmod.networking.packet.ReduceThirstByTickC2SPacket;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RPGMod.MODID,value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onLivingEntityUseItem(LivingEntityUseItemEvent.Finish event){
        if(event.getEntity().level.isClientSide && event.getItem().is(Items.POTION) && event.getDuration() == 0 ){
                    ModPackets.sendToServer(new DrankC2SPacket());
        }
    }
    @SubscribeEvent
    public static void onPlayerJump(LivingEvent.LivingJumpEvent event){
        LocalPlayer player = Minecraft.getInstance().player;
        if(event.getEntity().is(player) && event.getEntity().level.isClientSide){
            ModPackets.sendToServer(new JumpReduceThirstC2SPacket());

        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        if(event.side.isClient()){
            if(event.player.isSprinting()){
                PlayerThirst.setReduceByTick(PlayerThirst.getReduceByTick() + 0.4f);
            }
            ModPackets.sendToServer(new ReduceThirstByTickC2SPacket());
            ModPackets.sendToServer(new GamemodeDataSyncC2SPacket());
        }
    }

    @SubscribeEvent
    public static void onGMChange(PlayerEvent.PlayerChangeGameModeEvent event){
        ClientGamemodeData.setIsSurvival(event.getCurrentGameMode().isSurvival());
    }
    @Mod.EventBusSubscriber(modid = RPGMod.MODID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("thirst", ThirstHudOverlay.HUD_THIRST);
        }
    }

}
