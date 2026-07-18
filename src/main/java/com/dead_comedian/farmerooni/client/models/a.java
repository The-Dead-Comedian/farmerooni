//// Made with Blockbench 5.1.4
//// Exported for Minecraft version 1.17 or later with Mojang mappings
//// Paste this class into your mod and generate all required imports
//
//
//public class a<T extends Entity> extends EntityModel<T> {
//	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
//	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "a"), "main");
//	private final ModelPart bone2;
//	private final ModelPart bone;
//	private final ModelPart body2;
//	private final ModelPart front_left_leg2;
//	private final ModelPart front_right_leg2;
//	private final ModelPart neck2;
//	private final ModelPart head2;
//	private final ModelPart horn2;
//	private final ModelPart he_saddle;
//	private final ModelPart head_saddle;
//	private final ModelPart saddle_mouth_line_r;
//	private final ModelPart saddle_mouth_line;
//	private final ModelPart saddle_mouth_r;
//	private final ModelPart saddle_mouth_l;
//	private final ModelPart mouth2;
//	private final ModelPart left_ear2;
//	private final ModelPart right_ear2;
//	private final ModelPart tail2;
//	private final ModelPart back_left_leg2;
//	private final ModelPart back_right_leg2;
//	private final ModelPart saddle;
//	private final ModelPart body;
//	private final ModelPart front_left_leg;
//	private final ModelPart front_right_leg;
//	private final ModelPart neck;
//	private final ModelPart head;
//	private final ModelPart horn;
//	private final ModelPart mouth;
//	private final ModelPart left_ear;
//	private final ModelPart right_ear;
//	private final ModelPart mane;
//	private final ModelPart tail;
//	private final ModelPart back_left_leg;
//	private final ModelPart back_right_leg;
//
//	public a(ModelPart root) {
//		this.bone2 = root.getChild("bone2");
//		this.bone = this.bone2.getChild("bone");
//		this.body2 = this.bone.getChild("body2");
//		this.front_left_leg2 = this.body2.getChild("front_left_leg2");
//		this.front_right_leg2 = this.body2.getChild("front_right_leg2");
//		this.neck2 = this.body2.getChild("neck2");
//		this.head2 = this.neck2.getChild("head2");
//		this.horn2 = this.head2.getChild("horn2");
//		this.he_saddle = this.head2.getChild("he_saddle");
//		this.head_saddle = this.he_saddle.getChild("head_saddle");
//		this.saddle_mouth_line_r = this.he_saddle.getChild("saddle_mouth_line_r");
//		this.saddle_mouth_line = this.he_saddle.getChild("saddle_mouth_line");
//		this.saddle_mouth_r = this.he_saddle.getChild("saddle_mouth_r");
//		this.saddle_mouth_l = this.he_saddle.getChild("saddle_mouth_l");
//		this.mouth2 = this.head2.getChild("mouth2");
//		this.left_ear2 = this.head2.getChild("left_ear2");
//		this.right_ear2 = this.head2.getChild("right_ear2");
//		this.tail2 = this.body2.getChild("tail2");
//		this.back_left_leg2 = this.body2.getChild("back_left_leg2");
//		this.back_right_leg2 = this.body2.getChild("back_right_leg2");
//		this.saddle = this.body2.getChild("saddle");
//		this.body = this.bone.getChild("body");
//		this.front_left_leg = this.body.getChild("front_left_leg");
//		this.front_right_leg = this.body.getChild("front_right_leg");
//		this.neck = this.body.getChild("neck");
//		this.head = this.neck.getChild("head");
//		this.horn = this.head.getChild("horn");
//		this.mouth = this.head.getChild("mouth");
//		this.left_ear = this.head.getChild("left_ear");
//		this.right_ear = this.head.getChild("right_ear");
//		this.mane = this.neck.getChild("mane");
//		this.tail = this.body.getChild("tail");
//		this.back_left_leg = this.body.getChild("back_left_leg");
//		this.back_right_leg = this.body.getChild("back_right_leg");
//	}
//
//
//	@Override
//	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//	}
//
//	@Override
//	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//		bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//	}
//}