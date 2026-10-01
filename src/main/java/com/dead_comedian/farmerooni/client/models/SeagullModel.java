package com.dead_comedian.farmerooni.client.models;// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.dead_comedian.farmerooni.Farmerooni;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

import javax.swing.text.html.parser.Entity;

public class SeagullModel<T extends Mob> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "seagull"), "main");
	private final ModelPart root;
	private final ModelPart gull;
	private final ModelPart leg_left;
	private final ModelPart leg_left2;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart right_wing;
	private final ModelPart left_wing;

	public SeagullModel(ModelPart root) {
		this.root = root.getChild("root");
		this.gull = this.root.getChild("gull");
		this.leg_left = this.gull.getChild("leg_left");
		this.leg_left2 = this.gull.getChild("leg_left2");
		this.body = this.gull.getChild("body");
		this.head = this.body.getChild("head");
		this.right_wing = this.body.getChild("right_wing");
		this.left_wing = this.body.getChild("left_wing");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition gull = root.addOrReplaceChild("gull", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition leg_left = gull.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(22, 33).addBox(-4.0F, -2.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));

		PartDefinition leg_left2 = gull.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(34, 33).addBox(-6.0F, -2.0F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.5F, 0.0F, 0.0F));

		PartDefinition body = gull.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -12.0F, -6.0F, 9.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-4.0F, -12.0F, 5.0F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 3.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-3.5F, -16.0F, -9.0F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(30, 44).addBox(-1.5F, -13.5F, -13.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 39).addBox(-1.5F, -14.0F, -13.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(22, 18).mirror().addBox(-7.0F, -12.0F, -6.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 39).mirror().addBox(-7.0F, -12.0F, 2.0F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(22, 18).addBox(6.0F, -12.0F, -6.0F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(22, 39).addBox(6.0F, -12.0F, 2.0F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Mob entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public ModelPart root() {
		return root;
	}
}