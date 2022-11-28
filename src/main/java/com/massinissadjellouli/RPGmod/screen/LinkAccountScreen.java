package com.massinissadjellouli.RPGmod.screen;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import com.massinissadjellouli.RPGmod.client.renderer.ClientPlayerUIDData;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.ChangeClassC2SPacket;
import com.massinissadjellouli.RPGmod.networking.packet.UpdatePlayerUIDBackendC2SPacket;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.PlayerUIDCapabilityProvider;
import com.massinissadjellouli.RPGmod.networking.rpgmodWebsiteNetworking.WebsiteUtils;
import com.massinissadjellouli.RPGmod.worldEvents.WorldEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.ForgeHooks;

import static com.massinissadjellouli.RPGmod.classSystem.PlayerClassType.*;

public class LinkAccountScreen extends AbstractContainerScreen<LinkAccountMenu> {

    public LinkAccountScreen(LinkAccountMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 158;
        this.imageHeight = 135;
    }

    private static final ResourceLocation texture = new ResourceLocation(RPGMod.MODID,
            "textures/gui/link_account_screen.png");

    @Override
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);

    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderTexture(0, texture);
        this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }

        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
        Component title = Component.literal("Lier votre compte");
        this.font.draw(poseStack, title, imageWidth/2f - this.font.width(title)/2f, 15, -12829636);
        Component link = Component.literal("1. Rendez vous sur le site web");
        Component link1 = Component.literal("2. Créez un compte");
        Component link2 = Component.literal("3. Connectez vous");
        Component link3 = Component.literal("4. Cliquez sur \"Lier mon compte\"");
        Component link4 = Component.literal("5. Entrez le code suivant :");
        poseStack.pushPose();
        poseStack.scale(0.9f, 0.9f, 0.9f);
        this.font.draw(poseStack, link, imageWidth/2f - this.font.width(link)/2f + 10, 40, -12829636);
        this.font.draw(poseStack, link1, imageWidth/2f - this.font.width(link1)/2f + 10, 50, -12829636);
        this.font.draw(poseStack, link2, imageWidth/2f - this.font.width(link2)/2f + 10, 60, -12829636);
        this.font.draw(poseStack, link3, imageWidth/2f - this.font.width(link3)/2f + 10, 70, -12829636);
        this.font.draw(poseStack, link4, imageWidth/2f - this.font.width(link4)/2f + 10, 80, -12829636);
        poseStack.popPose();
        MutableComponent code = Component.literal(ClientPlayerUIDData.getUid());
        poseStack.pushPose();
        poseStack.scale(0.7f, 0.7f, 0.7f);
        MutableComponent t1 = Component.literal("Cela vous permet de sauvegarder");
        MutableComponent t2 = Component.literal("vos statistiques pour ce monde");
        this.font.draw(poseStack, code, imageWidth/2f - this.font.width(code)/2f + 30, 130, -12829636);
        this.font.draw(poseStack, t1, imageWidth/2f - this.font.width(t1)/2f + 30, 160, -12829636);
        this.font.draw(poseStack, t2, imageWidth/2f - this.font.width(t2)/2f + 30, 170, -12829636);
        poseStack.popPose();

    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }


}