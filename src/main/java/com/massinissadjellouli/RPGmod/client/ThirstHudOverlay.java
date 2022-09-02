package com.massinissadjellouli.RPGmod.client;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.thirst.PlayerThirst;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ThirstHudOverlay {
    private static final ResourceLocation FILLED_THIRST = new ResourceLocation(RPGMod.MODID,"textures/thirst/filled_thirst.png");
    private static final ResourceLocation HALF_THIRST = new ResourceLocation(RPGMod.MODID,"textures/thirst/half_thirst.png");
    private static final ResourceLocation EMPTY_THIRST = new ResourceLocation(RPGMod.MODID,"textures/thirst/empty_thirst.png");


    public static final IGuiOverlay HUD_THIRST = (((gui, poseStack, partialTick, screenWidth, screenHeight) -> {
        final int AMOUNT_OF_BOTTLES = 10;
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0,EMPTY_THIRST);
        for(int i = 0; i < AMOUNT_OF_BOTTLES; i++){
            GuiComponent.blit(poseStack,x - 45 + (i * 9),y - 54, 0, 0, 12, 12, 12, 12);
        }

        RenderSystem.setShaderTexture(0,FILLED_THIRST);
        for (int i = 0; i < AMOUNT_OF_BOTTLES; i++){
            //Todo
        }
    }));

}
