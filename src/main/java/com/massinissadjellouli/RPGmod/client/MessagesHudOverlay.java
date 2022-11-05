package com.massinissadjellouli.RPGmod.client;

import com.massinissadjellouli.RPGmod.worldEvents.WorldEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MessagesHudOverlay {

    public static final IGuiOverlay HUD_MESSAGE = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if(!ClientGamemodeData.isSurvival() ){
            return;
        }
        Font font = Minecraft.getInstance().font;
        font.drawShadow(poseStack, Component.literal(ClientLastMessageReceived.get()).withStyle(ClientLastMessageReceived.isImportant()?ChatFormatting.GOLD:ChatFormatting.AQUA),10,10,10);

    }));
    public static final IGuiOverlay HUD_WORLD_EVEN_COUNTDOWN = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if(WorldEvent.ongoingEvent == null){
            return;
        }
        Font font = Minecraft.getInstance().font;
        MutableComponent component = Component.literal(WorldEvent.ongoingEvent.getRemainingTime()).withStyle(ChatFormatting.WHITE);
        MutableComponent text = Component.literal("Temps restant à l'event:").withStyle(ChatFormatting.WHITE);
        poseStack.pushPose();
        font.drawShadow(poseStack,text
                ,screenWidth - font.width(text),screenHeight - 20,10);
        font.drawShadow(poseStack,component
                ,screenWidth - font.width(component),screenHeight - 10,10);
        poseStack.popPose();
    }));
    public static final IGuiOverlay HUD_TITLE = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        Font font = Minecraft.getInstance().font;
        MutableComponent component = Component.literal(ClientLastTitleReceived.getTitle())
                .withStyle(ClientLastTitleReceived.getTitleColor());
        poseStack.pushPose();
        float scale = 1.5f;
        poseStack.scale(scale,scale,scale);
        font.drawShadow(poseStack,component
                ,screenWidth/(2f * scale) - font.width(component)/2f ,screenHeight/ (2f * scale) - 7,10);
        poseStack.popPose();
        if(!ClientLastTitleReceived.getSubtitle().equals("")){
            component = Component.literal(ClientLastTitleReceived.getSubtitle())
                    .withStyle(ClientLastTitleReceived.getTitleColor());
            poseStack.pushPose();
            font.drawShadow(poseStack,component
                    ,screenWidth/2f - font.width(component)/2f ,screenHeight/ 2f + 10,10);
        poseStack.popPose();
        }

    }));
}
