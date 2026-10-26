// Made with Blockbench 5.1.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class seagull<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "seagull"), "main");
	private final ModelPart root;
	private final ModelPart gull;
	private final ModelPart leg_left;
	private final ModelPart leg_left2;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart right_wing;
	private final ModelPart left_wing;

	public seagull(ModelPart root) {
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

		PartDefinition gull = root.addOrReplaceChild("gull", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, 1.0F));

		PartDefinition leg_left = gull.addOrReplaceChild("leg_left", CubeListBuilder.create().texOffs(22, 33).addBox(-2.0F, 1.0F, -2.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -4.0F, 0.5F));

		PartDefinition leg_left2 = gull.addOrReplaceChild("leg_left2", CubeListBuilder.create().texOffs(34, 33).addBox(-1.0F, 1.0F, -2.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -4.0F, 0.5F));

		PartDefinition body = gull.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.6F, -8.1F, -7.3F, 9.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-3.6F, -8.1F, 3.7F, 7.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.4F, -1.9F, 3.3F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -5.3333F, -3.5F, 6.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(30, 44).addBox(-1.0F, -2.8333F, -7.5F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(30, 39).addBox(-1.0F, -3.3333F, -7.5F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offset(-0.1F, -7.7667F, -6.8F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(22, 18).mirror().addBox(-0.5F, -1.5F, -3.75F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(22, 39).mirror().addBox(-0.5F, -1.5F, 4.25F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.1F, -6.6F, -3.55F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(22, 18).addBox(-0.5F, -1.5F, -3.75F, 1.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(22, 39).addBox(-0.5F, -1.5F, 4.25F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.9F, -6.6F, -3.55F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}