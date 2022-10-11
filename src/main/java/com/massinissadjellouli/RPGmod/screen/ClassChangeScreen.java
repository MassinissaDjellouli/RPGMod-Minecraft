package com.massinissadjellouli.RPGmod.screen;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassProvider;
import com.massinissadjellouli.RPGmod.classSystem.PlayerClassType;
import com.massinissadjellouli.RPGmod.client.ClientLastMessageReceived;
import com.massinissadjellouli.RPGmod.networking.ModPackets;
import com.massinissadjellouli.RPGmod.networking.packet.ChangeClassC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

import static com.massinissadjellouli.RPGmod.classSystem.PlayerClassType.*;

public class ClassChangeScreen extends AbstractContainerScreen<ClassChangeMenu> {

    public ClassChangeScreen(ClassChangeMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 158;
        this.imageHeight = 135;
    }

    private static final ResourceLocation texture = new ResourceLocation(RPGMod.MODID,
            "textures/gui/change_class_screen.png");

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
        this.font.draw(poseStack, "Choix de votre classe", 23, 15, -12829636);
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

        this.addRenderableWidget(new Button(this.leftPos + 54, this.topPos + 34, 51, 20, Component.literal("Mineur"),
                e -> {
                    ModPackets.sendToServer(new ChangeClassC2SPacket(MINEUR));
                    onClose();
                    ClientLastMessageReceived.setImportant("Vous etes maintenant mineur!");
                }));
        this.addRenderableWidget(new Button(this.leftPos + 46, this.topPos + 67, 67, 20, Component.literal("Bucheron"),
                e -> {
                    ModPackets.sendToServer(new ChangeClassC2SPacket(BUCHERON));
                    onClose();
                    ClientLastMessageReceived.setImportant("Vous etes maintenant bucheron!");
                }));
        this.addRenderableWidget(new Button(this.leftPos + 51, this.topPos + 102, 56, 20, Component.literal("Soldat"),
                e -> {
                    ModPackets.sendToServer(new ChangeClassC2SPacket(SOLDAT));
                    onClose();
                    ClientLastMessageReceived.setImportant("Vous etes maintenant soldat!");
                }));
    }

}