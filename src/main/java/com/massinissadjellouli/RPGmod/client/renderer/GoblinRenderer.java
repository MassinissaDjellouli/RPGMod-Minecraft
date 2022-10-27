package com.massinissadjellouli.RPGmod.client.renderer;

import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.client.Models.GoblinModel;
import com.massinissadjellouli.RPGmod.entities.Goblin;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class GoblinRenderer extends MobRenderer<Goblin, GoblinModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(RPGMod.MODID,"textures/entities/goblin.png");
    public GoblinRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GoblinModel(pContext.bakeLayer(GoblinModel.LAYER_LOCATION)), 0.5f);
    }


    @Override
    public ResourceLocation getTextureLocation(Goblin pEntity) {
        return TEXTURE;
    }
}
