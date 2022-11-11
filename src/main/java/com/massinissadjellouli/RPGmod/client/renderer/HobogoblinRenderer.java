package com.massinissadjellouli.RPGmod.client.renderer;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.Models.HobogoblinModel;
import com.massinissadjellouli.RPGmod.entities.custom.Hobogoblin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HobogoblinRenderer extends GeoEntityRenderer<Hobogoblin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RPGMod.MODID, "textures/entities/hobogoblin.png");

    public HobogoblinRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new HobogoblinModel());
        shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(Hobogoblin animatable,
                                    float partialTicks,
                                    PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer,
                                    @Nullable VertexConsumer vertexBuilder,
                                    int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(1.3f, 1.3f, 1.3f);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    public ResourceLocation getTextureLocation(Hobogoblin pEntity) {
        return TEXTURE;
    }
}
