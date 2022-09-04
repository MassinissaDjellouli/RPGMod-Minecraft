package com.massinissadjellouli.RPGmod.events;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.ClientGamemodeData;
import com.massinissadjellouli.RPGmod.client.ThirstHudOverlay;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.*;
import com.massinissadjellouli.RPGmod.tags.ModTags;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;
import org.apache.commons.lang3.text.WordUtils;

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
            ModPackets.sendToServer(new ThirstEffectC2SPacket());

        }
    }

    @SubscribeEvent
    public static void itemTooltip(ItemTooltipEvent event){
        String tooltip = "";
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        Item item = event.getItemStack().getItem();
        ChatFormatting style = ChatFormatting.WHITE;
        for (ModTags.Items.RarityTags tagKey : ModTags.Items.RarityTags.values()){
            style = tagKey.style;
            TagKey<Item> tag = tagKey.tagKey;
            if(tagManager.getTag(tag).contains(item)){
                tooltip = tagKey.name;
                break;
            }
        }
        event.getToolTip().add(Component.literal(tooltip).withStyle(style));
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
