package com.massinissadjellouli.RPGmod.client;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class MessagesHudOverlay {

    public static final IGuiOverlay HUD_MESSAGE = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        if(!ClientGamemodeData.isSurvival() ){
            return;
        }



        Font font = Minecraft.getInstance().font;
        font.draw(poseStack, Component.literal("test").withStyle(ChatFormatting.GOLD),10,10,10);

    }));

}
