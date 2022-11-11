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

public class ItemCompressorScreen extends AbstractContainerScreen<ItemCompressorMenu> {
    public ItemCompressorScreen(ItemCompressorMenu itemCompressorMenu, Inventory inventory, Component component) {
        super(itemCompressorMenu, inventory, component);
    }

    private EnergyInfoArea energyInfoArea;
    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(RPGMod.MODID,
            "textures/gui/item_compressor_screen.png");
    public static final ResourceLocation PROGRESS_BAR_TEXTURE = new ResourceLocation(RPGMod.MODID,
            "textures/gui/item_compressor_screen_progress.png");


    @Override
    protected void renderBg(PoseStack stack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack, this.leftPos, this.topPos, 0, 0, imageWidth, imageHeight, imageWidth, imageHeight);
        renderProgressBar(stack, x, y);
        energyInfoArea.draw(stack);
    }


    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderEnergyAreaToolTips(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderEnergyAreaToolTips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        if (isMouseOverArea(pMouseX, pMouseY, x, y, 93, 18, 8, 51)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseOverArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    //148,17
    private void renderProgressBar(PoseStack stack, int x, int y) {
        if (menu.isCrafting()) {
            RenderSystem.setShaderTexture(0, PROGRESS_BAR_TEXTURE);
            blit(stack, x + 148, y + 18, 176, 0, 4, menu.getScaledProgress());
        }
    }


    @Override
    protected void init() {
        super.init();
        assigEnergyInfoArea();
    }

    private void assigEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 93, y + 18, menu.blockEntity.getEnergyStorage());
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
