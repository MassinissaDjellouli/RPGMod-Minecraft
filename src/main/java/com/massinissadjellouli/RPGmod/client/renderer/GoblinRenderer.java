package com.massinissadjellouli.RPGmod.client.renderer;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.Models.GoblinModel;
import com.massinissadjellouli.RPGmod.entities.custom.Goblin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GoblinRenderer extends GeoEntityRenderer<Goblin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RPGMod.MODID, "textures/entities/goblin.png");

    public GoblinRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GoblinModel());
        shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(Goblin animatable,
                                    float partialTicks,
                                    PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder,
                                    int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.5f, 0.5f, 0.5f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    public ResourceLocation getTextureLocation(Goblin pEntity) {
        return TEXTURE;
    }
}
