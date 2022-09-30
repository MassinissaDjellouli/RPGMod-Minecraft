package com.massinissadjellouli.RPGmod.screen;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.Utils.MouseUtil;
import com.massinissadjellouli.RPGmod.screen.renderer.EnergyInfoArea;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class RarityTableScreen extends AbstractContainerScreen<RarityTableMenu> {
    public RarityTableScreen(RarityTableMenu rarityTableMenu, Inventory inventory, Component component) {
        super(rarityTableMenu, inventory, component);
    }

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(RPGMod.MODID,
            "textures/gui/rarity_table_screen.png");



    @Override
    protected void renderBg(PoseStack stack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack,this.leftPos,this.topPos,0,0,imageWidth,imageHeight,imageWidth,imageHeight);
    }


}
