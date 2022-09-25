package com.massinissadjellouli.RPGmod.screen;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ItemCompressorScreen extends AbstractContainerScreen<ItemCompressorMenu> {
    public ItemCompressorScreen(ItemCompressorMenu itemCompressorMenu, Inventory inventory, Component component) {
        super(itemCompressorMenu, inventory, component);
    }

    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(RPGMod.MODID,
            "textures/gui/item_compressor_screen.png");
    public static final ResourceLocation PROGRESS_BAR_TEXTURE = new ResourceLocation(RPGMod.MODID,
            "textures/gui/item_compressor_screen_progress.png");

    @Override
    protected void renderBg(PoseStack stack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, BG_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(stack,this.leftPos,this.topPos,0,0,imageWidth,imageHeight,imageWidth,imageHeight);
        renderProgressBar(stack,x,y);
    }

    //148,17
    private void renderProgressBar(PoseStack stack, int x, int y) {
        if(menu.isCrafting()){
            RenderSystem.setShaderTexture(0, PROGRESS_BAR_TEXTURE);
            blit(stack,x + 148,y + 18, 176,0,4,menu.getScaledProgress());
        }
    }


    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack,mouseX,mouseY);
    }
}
