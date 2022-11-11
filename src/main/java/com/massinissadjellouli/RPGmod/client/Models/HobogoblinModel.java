package com.massinissadjellouli.RPGmod.client.Models;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.entities.custom.Hobogoblin;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class HobogoblinModel extends AnimatedGeoModel<Hobogoblin> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RPGMod.MODID, "hobogoblin"), "main");

    public HobogoblinModel() {
    }


    @Override
    public ResourceLocation getModelResource(Hobogoblin object) {
        return new ResourceLocation(RPGMod.MODID, "geo/hobogoblin.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hobogoblin object) {
        return new ResourceLocation(RPGMod.MODID, "textures/entities/hobogoblin.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Hobogoblin animatable) {
        return new ResourceLocation(RPGMod.MODID, "animations/hobogoblin.animation.json");
    }
}