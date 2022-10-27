package com.massinissadjellouli.RPGmod.client.Models;// Made with Blockbench 4.4.3
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.massinissadjellouli.RPGmod.RPGMod;
import com.massinissadjellouli.RPGmod.entities.Goblin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;


public class GoblinModel extends EntityModel<Goblin> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(RPGMod.MODID, "goblin"), "main");
	private final ModelPart Root;

	public GoblinModel(ModelPart root) {
		this.Root = root.getChild("Root");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Root = partdefinition.addOrReplaceChild("Root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition LeftLeg = Root.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(24, 37).addBox(-7.0F, -11.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition RightLeg = Root.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 37).addBox(2.0F, -11.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Axe = Root.addOrReplaceChild("Axe", CubeListBuilder.create().texOffs(48, 35).addBox(-0.5F, -26.0F, -1.0F, 2.0F, 26.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.5F, -8.0F, -2.0F, 0.6981F, 0.0F, 0.0F));

		PartDefinition bone5 = Axe.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(56, 45).addBox(-2.5F, -20.0F, 20.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(56, 57).addBox(-2.5F, -20.0F, 16.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(44, 61).addBox(-2.5F, -21.0F, 14.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-2.5F, -19.0F, 13.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 4).addBox(-2.5F, -22.0F, 16.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(4, 18).addBox(-2.5F, -23.0F, 18.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 37).addBox(-2.5F, -22.0F, 19.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 4).addBox(-2.5F, -21.0F, 20.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(2, 22).addBox(-2.5F, -13.0F, 15.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.5F, -11.0F, 16.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -4.0F, -23.0F));

		PartDefinition Head = Root.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -32.0F, -6.0F, 21.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, -1.0F));

		Head.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(18, 37).addBox(-1.0F, -24.0F, -10.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(50, 0).addBox(-1.0F, -27.0F, -10.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

		PartDefinition bone9 = Head.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, 2.0F));

		bone9.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-5.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-4.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 0).mirror().addBox(-2.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-10.0F, -28.0F, -5.0F, 0.0F, 1.1345F, 0.0F));

		bone9.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(0.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -28.0F, -5.0F, 0.0F, -1.1345F, 0.0F));

		PartDefinition Torso = Root.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 18).addBox(-8.0F, -22.0F, -3.0F, 17.0F, 11.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition LeftForearm = Root.addOrReplaceChild("LeftForearm", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

		LeftForearm.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 54).addBox(-14.0F, -17.0F, -11.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5667F, -0.007F, -0.0003F));

		PartDefinition LeftArm = Root.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(22, 54).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -21.0F, 2.0F));

		PartDefinition RightArm = Root.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(56, 32).addBox(20.0F, 0.0F, -3.0F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -21.0F, 2.0F));

		PartDefinition RightForearm = Root.addOrReplaceChild("RightForearm", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

		RightForearm.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(50, 18).addBox(9.0F, -17.0F, -11.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5667F, -0.007F, -0.0003F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Goblin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}