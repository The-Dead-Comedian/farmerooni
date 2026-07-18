package com.dead_comedian.farmerooni.client.models;// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.Unicorn;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;

import java.util.HashMap;
import java.util.Map;

public class UnicornModel<T extends Unicorn> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "unicorn"), "main");
    public static final ModelLayerLocation ARMOR_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "unicorn_armor"), "main");
    private static final float DEG_125 = 2.1816616F;
     private static final float DEG_30 = ((float) Math.PI / 6F);
    private static final float DEG_15 = 0.2617994F;

    private final ModelPart[] ridingParts;

    private final ModelPart bone2;
    private final ModelPart bone;
    private final ModelPart body2;
    private final ModelPart front_left_leg2;
    private final ModelPart front_right_leg2;
    private final ModelPart neck2;
    private final ModelPart head2;
    private final ModelPart horn2;
    private final ModelPart he_saddle;
    private final ModelPart head_saddle;
    private final ModelPart saddle_mouth_line_r;
    private final ModelPart saddle_mouth_line;
    private final ModelPart saddle_mouth_r;
    private final ModelPart saddle_mouth_l;
    private final ModelPart mouth2;
    private final ModelPart left_ear2;
    private final ModelPart right_ear2;
    private final ModelPart tail2;
    private final ModelPart back_left_leg2;
    private final ModelPart back_right_leg2;
    private final ModelPart saddle;
    private final ModelPart body;
    private final ModelPart front_left_leg;
    private final ModelPart front_right_leg;
    private final ModelPart neck;
    private final ModelPart head;
    private final ModelPart horn;
    private final ModelPart mouth;
    private final ModelPart left_ear;
    private final ModelPart right_ear;
    private final ModelPart mane;
    private final ModelPart tail;
    private final ModelPart back_left_leg;
    private final ModelPart back_right_leg;

    private final Map<ModelPart, float[]> restPose = new HashMap<>();

    public UnicornModel(ModelPart root) {
        this.bone2 = root.getChild("bone2");
        this.bone = this.bone2.getChild("bone");
        this.body2 = this.bone.getChild("body2");
        this.front_left_leg2 = this.body2.getChild("front_left_leg2");
        this.front_right_leg2 = this.body2.getChild("front_right_leg2");
        this.neck2 = this.body2.getChild("neck2");
        this.head2 = this.neck2.getChild("head2");
        this.horn2 = this.head2.getChild("horn2");
        this.he_saddle = this.head2.getChild("he_saddle");
        this.head_saddle = this.he_saddle.getChild("head_saddle");
        this.saddle_mouth_line_r = this.he_saddle.getChild("saddle_mouth_line_r");
        this.saddle_mouth_line = this.he_saddle.getChild("saddle_mouth_line");
        this.saddle_mouth_r = this.he_saddle.getChild("saddle_mouth_r");
        this.saddle_mouth_l = this.he_saddle.getChild("saddle_mouth_l");
        this.mouth2 = this.head2.getChild("mouth2");
        this.left_ear2 = this.head2.getChild("left_ear2");
        this.right_ear2 = this.head2.getChild("right_ear2");
        this.tail2 = this.body2.getChild("tail2");
        this.back_left_leg2 = this.body2.getChild("back_left_leg2");
        this.back_right_leg2 = this.body2.getChild("back_right_leg2");
        this.saddle = this.body2.getChild("saddle");
        this.body = this.bone.getChild("body");
        this.front_left_leg = this.body.getChild("front_left_leg");
        this.front_right_leg = this.body.getChild("front_right_leg");
        this.neck = this.body.getChild("neck");
        this.head = this.neck.getChild("head");
        this.horn = this.head.getChild("horn");
        this.mouth = this.head.getChild("mouth");
        this.left_ear = this.head.getChild("left_ear");
        this.right_ear = this.head.getChild("right_ear");
        this.mane = this.neck.getChild("mane");
        this.tail = this.body.getChild("tail");
        this.back_left_leg = this.body.getChild("back_left_leg");
        this.back_right_leg = this.body.getChild("back_right_leg");

        captureRest(head, mouth, left_ear, right_ear, neck, mane, tail, body,
                front_left_leg, front_right_leg, back_left_leg, back_right_leg, saddle, saddle_mouth_r, saddle_mouth_l, saddle_mouth_line, saddle_mouth_line_r, head);
        captureRest(head2, mouth2, left_ear2, right_ear2, neck2, tail2, body2,
                front_left_leg2, front_right_leg2, back_left_leg2, back_right_leg2);


        this.ridingParts = new ModelPart[]{saddle_mouth_line, saddle_mouth_line_r};
    }

    private void captureRest(ModelPart... parts) {
        for (ModelPart p : parts) {
            restPose.put(p, new float[]{p.x, p.y, p.z, p.xRot, p.yRot, p.zRot});
        }
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone2 = partdefinition.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -9.0F));

        PartDefinition bone = bone2.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body2 = bone.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(64, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 9.0F, 15.0F));

        PartDefinition front_left_leg2 = body2.addOrReplaceChild("front_left_leg2", CubeListBuilder.create().texOffs(112, 21).mirror().addBox(-2.0F, -0.2F, -1.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(3.0F, 2.0F, -15.0F));

        PartDefinition front_right_leg2 = body2.addOrReplaceChild("front_right_leg2", CubeListBuilder.create().texOffs(112, 21).addBox(-2.0F, -0.2F, -1.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(-3.0F, 2.0F, -15.0F));

        PartDefinition neck2 = body2.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(64, 35).addBox(-2.05F, -11.7F, -3.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -3.5F, -14.0F));

        PartDefinition head2 = neck2.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(64, 13).addBox(-3.0F, -10.9F, -2.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -5.5F, -1.0F));

        PartDefinition horn2 = head2.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(95, 5).addBox(-1.0F, -18.7F, 0.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition he_saddle = head2.addOrReplaceChild("he_saddle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 23.75F, -0.5F, -0.5236F, 0.0F, 0.0F));

        PartDefinition head_saddle = he_saddle.addOrReplaceChild("head_saddle", CubeListBuilder.create().texOffs(19, 80).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
                .texOffs(0, 80).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -17.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition saddle_mouth_line_r = he_saddle.addOrReplaceChild("saddle_mouth_line_r", CubeListBuilder.create().texOffs(32, 82).addBox(-3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, -8.0F));

        PartDefinition saddle_mouth_line = he_saddle.addOrReplaceChild("saddle_mouth_line", CubeListBuilder.create().texOffs(32, 82).addBox(3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, -8.0F));

        PartDefinition saddle_mouth_r = he_saddle.addOrReplaceChild("saddle_mouth_r", CubeListBuilder.create().texOffs(29, 85).addBox(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition saddle_mouth_l = he_saddle.addOrReplaceChild("saddle_mouth_l", CubeListBuilder.create().texOffs(29, 85).addBox(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition mouth2 = head2.addOrReplaceChild("mouth2", CubeListBuilder.create().texOffs(64, 25).addBox(-2.0F, -10.9F, -6.8F, 4.0F, 5.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_ear2 = head2.addOrReplaceChild("left_ear2", CubeListBuilder.create().texOffs(83, 16).addBox(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, -0.01F));

        PartDefinition right_ear2 = head2.addOrReplaceChild("right_ear2", CubeListBuilder.create().texOffs(83, 16).addBox(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, -0.01F));

        PartDefinition tail2 = body2.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(106, 36).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -8.0F, 5.0F));

        PartDefinition back_left_leg2 = body2.addOrReplaceChild("back_left_leg2", CubeListBuilder.create().texOffs(112, 21).mirror().addBox(-2.0F, -0.2F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(3.0F, 2.0F, 3.0F));

        PartDefinition back_right_leg2 = body2.addOrReplaceChild("back_right_leg2", CubeListBuilder.create().texOffs(112, 21).addBox(-2.0F, -0.2F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offset(-3.0F, 2.0F, 3.0F));

        PartDefinition saddle = body2.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(26, 80).addBox(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -9.0F, -4.0F));

        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 9.0F, 15.0F));

        PartDefinition front_left_leg = body.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-3.0F, 0.0F, -1.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 2.0F, -15.0F));

        PartDefinition front_right_leg = body.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -1.9F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.0F, -15.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 35).addBox(-2.05F, -11.5F, -3.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.5F, -14.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, 4.0F, -2.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.5F, -1.0F));

        PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(37, 3).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, 4.0F, -7.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(19, 16).addBox(0.55F, 2.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.01F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(19, 16).addBox(-2.55F, 2.0F, 4.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -0.01F));

        PartDefinition mane = neck.addOrReplaceChild("mane", CubeListBuilder.create().texOffs(56, 36).addBox(-1.0F, 4.0F, 5.01F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.5F, -1.01F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 5.0F));

        PartDefinition back_left_leg = body.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 2.0F, 3.0F));

        PartDefinition back_right_leg = body.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 2.0F, 3.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
        super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);

        float bodyYaw = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
        float headYaw = Mth.rotLerp(partialTick, entity.yHeadRotO, entity.yHeadRot);
        float xRotLerp = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());

        float f3 = headYaw - bodyYaw;
        if (f3 > 20.0F) f3 = 20.0F;
        if (f3 < -20.0F) f3 = -20.0F;

        float f4 = xRotLerp * ((float) Math.PI / 180F);
        if (limbSwingAmount > 0.2F) {
            f4 += Mth.cos(limbSwing * 0.8F) * 0.15F * limbSwingAmount;
        }

        float f9 = (float) entity.tickCount + partialTick;

        boolean flag = entity.isSaddled();
        boolean flag1 = entity.isVehicle();

        saddle.visible = flag;
        saddle_mouth_l.visible = flag;
        saddle_mouth_r.visible = flag;
        head_saddle.visible = flag;

        for (ModelPart modelpart1 : this.ridingParts) {
            modelpart1.visible = flag1 && flag;
        }


        bone2.z = -14;
        bone2.y = -1;
        bone2.yScale = 1.1f;
        bone2.zScale = 1.1f;
        bone2.xScale = 1.1f;

        animateGroup(entity, limbSwing, limbSwingAmount, partialTick, f3, f4, f9,
                body, head, mouth, left_ear, right_ear, neck, mane, tail,
                front_left_leg, front_right_leg, back_left_leg, back_right_leg);

        animateGroup(entity, limbSwing, limbSwingAmount, partialTick, f3, f4, f9,
                body2, head2, mouth2, left_ear2, right_ear2, neck2, null, tail2,
                front_left_leg2, front_right_leg2, back_left_leg2, back_right_leg2);
    }

    private void animateGroup(T entity, float limbSwing, float limbSwingAmount, float partialTick,
                              float f3, float f4, float f9,
                              ModelPart body, ModelPart head, ModelPart mouth,
                              ModelPart leftEar, ModelPart rightEar, ModelPart neck, ModelPart mane,
                              ModelPart tail, ModelPart frontLeftLeg, ModelPart frontRightLeg,
                              ModelPart backLeftLeg, ModelPart backRightLeg) {

        float f5 = entity.getEatAnim(partialTick);
        float f6 = entity.getStandAnim(partialTick);
        float f7 = 1.0F - f6;
        float f8 = entity.getMouthAnim(partialTick);
        boolean wagging = entity.tailCounter != 0;


        float f13 = (1.0F - Math.max(f6, f5)) * (DEG_30 + f4 + f8 * Mth.sin(f9) * 0.05F);
        float neckXRotRaw = f6 * (DEG_15 + f4) + f5 * (DEG_125 + Mth.sin(f9) * 0.05F) + f13;
        float neckXRotDelta = neckXRotRaw - DEG_30;
        float neckYRotDelta = f6 * f3 * ((float) Math.PI / 180F) + (1.0F - Math.max(f6, f5)) * f3 * ((float) Math.PI / 180F); // rest = 0 already


        for (ModelPart part : new ModelPart[]{neck}) {
            if (part == null) continue;
            float[] r = restPose.get(part);
            part.xRot = r[3] + neckXRotDelta + 0.5f;
            part.yRot = r[4] + neckYRotDelta;

            part.y = r[1] + 2;
            part.z = r[2] - 2;
        }


        float[] bodyRest = restPose.get(body);
        body.xRot = bodyRest[3] + f6 * (-(float) Math.PI / 4F);

        float f10 = entity.isInWater() ? 0.4F : 1.0F;
        float f11 = Mth.cos(f10 * limbSwing * 0.6662F + (float) Math.PI);
        float f12 = f11 * 0.8F * limbSwingAmount;
        float f14 = DEG_30 * f6;
        float f15 = Mth.cos(f9 * 0.6F + (float) Math.PI);

        float[] flRest = restPose.get(frontLeftLeg);
        float[] frRest = restPose.get(frontRightLeg);
        frontLeftLeg.y = flRest[1];
        frontLeftLeg.z = flRest[2];
        frontRightLeg.y = frRest[1];
        frontRightLeg.z = frRest[2];

        frontLeftLeg.xRot = flRest[3] + ((-(float) Math.PI / 3F) + f15) * f6 + f12 * f7;
        frontRightLeg.xRot = frRest[3] + ((-(float) Math.PI / 3F) - f15) * f6 - f12 * f7;

        float[] blRest = restPose.get(backLeftLeg);
        float[] brRest = restPose.get(backRightLeg);
        backLeftLeg.xRot = blRest[3] + f14 - f11 * 0.5F * limbSwingAmount * f7;
        backRightLeg.xRot = brRest[3] + f14 + f11 * 0.5F * limbSwingAmount * f7;


        float tailXRotRaw = DEG_30 + limbSwingAmount * 0.75F;
        float tailYRaw = -5.0F + limbSwingAmount;
        float tailZRaw = 2.0F + limbSwingAmount * 2.0F;

        float[] tailRest = restPose.get(tail);
        tail.xRot = tailRest[3] + (tailXRotRaw - DEG_30);
        tail.y = tailRest[1] + (tailYRaw - (-5.0F));
        tail.z = tailRest[2] + (tailZRaw - 2.0F) - 2;
        tail.yRot = tailRest[4] + (wagging ? Mth.cos(f9 * 0.7F) : 0.0F);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }


    @Override
    public ModelPart root() {
        return bone2;
    }

}