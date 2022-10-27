package com.massinissadjellouli.RPGmod.client.Models;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.entities.custom.Goblin;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class GoblinModel extends AnimatedGeoModel<Goblin> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RPGMod.MODID, "goblin"), "main");

	public GoblinModel() {
	}


	@Override
	public ResourceLocation getModelResource(Goblin object) {
		return new ResourceLocation(RPGMod.MODID,"geo/goblin.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Goblin object) {
		return new ResourceLocation(RPGMod.MODID,"textures/entities/goblin.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Goblin animatable) {
		return new ResourceLocation(RPGMod.MODID,"animations/goblin.animation.json");
	}
}