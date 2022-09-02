package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.ThirstHudOverlay;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirstProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.apache.http.io.SessionOutputBuffer;

@Mod.EventBusSubscriber(modid = RPGMod.MODID,value = Dist.CLIENT)
public class ModEvents {
 @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent event){
     if(event.getObject() instanceof Player){
         if(!((Player) event.getObject()).getCapability(PlayerThirstProvider.PLAYER_THIRST).isPresent()){
             event.addCapability(new ResourceLocation(RPGMod.MODID,"properties"), new PlayerThirstProvider());
         }
     }
 }
 @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event){
     if(event.isWasDeath()) {
         event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(oldStore -> {
             event.getOriginal().getCapability(PlayerThirstProvider.PLAYER_THIRST).ifPresent(newStore -> {
                 newStore.copyFrom(oldStore);
             });
         });
     }
 }

 @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
    event.register(PlayerThirst.class);
 }



}
