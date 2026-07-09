package com.dead_comedian.farmerooni.client.models;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.animations.TermiteAnimations;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class TermiteModel<T extends TermiteEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "termite"), "main");
    private final ModelPart root;
    private final ModelPart head;

    public TermiteModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root().getChild("model").getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-1.0F, 22.25F, -1.5F));

        PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition lr_leg = model.addOrReplaceChild("lr_leg", CubeListBuilder.create().texOffs(14, 9).addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 1.0F));

        PartDefinition mr_leg = model.addOrReplaceChild("mr_leg", CubeListBuilder.create().texOffs(14, 9).addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fr_leg = model.addOrReplaceChild("fr_leg", CubeListBuilder.create().texOffs(14, 9).addBox(-5.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition ll_leg = model.addOrReplaceChild("ll_leg", CubeListBuilder.create().texOffs(14, 9).addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 1.0F));

        PartDefinition ml_leg = model.addOrReplaceChild("ml_leg", CubeListBuilder.create().texOffs(14, 9).addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition fl_leg = model.addOrReplaceChild("fl_leg", CubeListBuilder.create().texOffs(14, 9).addBox(0.0F, 0.0F, -0.5F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, -1.0F));

        PartDefinition abdomen = model.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(0, 15).addBox(-1.0F, -0.5F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -0.5F, -0.25F));

        PartDefinition lower_abdomen = abdomen.addOrReplaceChild("lower_abdomen", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.75F));

        PartDefinition head = model.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 12).addBox(1.25F, -3.9F, -5.75F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 12).mirror().addBox(-1.25F, -3.9F, -5.75F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 9).addBox(-1.5F, -0.9F, -3.75F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -0.6F, -0.75F));

        PartDefinition jaw_left = head.addOrReplaceChild("jaw_left", CubeListBuilder.create().texOffs(14, 10).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.6F, -2.75F));

        PartDefinition jaw_right = head.addOrReplaceChild("jaw_right", CubeListBuilder.create().texOffs(14, 10).mirror().addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, 0.6F, -2.75F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
        this.animateWalk(TermiteAnimations.WALK, limbSwing, limbSwingAmount, 2f, 2.5f);

        this.animate(entity.idleAnimationState, TermiteAnimations.IDLE, ageInTicks, 2f);

    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
        pNetHeadYaw = Math.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Math.clamp(pHeadPitch, -25.0F, 45.0F);

        this.head.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float) Math.PI / 180F);
    }

    @Override
    public ModelPart root() {
        return root;
    }
}