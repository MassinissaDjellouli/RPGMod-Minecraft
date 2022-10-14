package com.massinissadjellouli.RPGmod.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MessagesHudOverlay {

    public static final IGuiOverlay HUD_MESSAGE = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if(!ClientGamemodeData.isSurvival() ){
            return;
        }
        Font font = Minecraft.getInstance().font;
        font.draw(poseStack, Component.literal(ClientLastMessageReceived.get()).withStyle(ClientLastMessageReceived.isImportant()?ChatFormatting.GOLD:ChatFormatting.AQUA),10,10,10);

    }));

}
