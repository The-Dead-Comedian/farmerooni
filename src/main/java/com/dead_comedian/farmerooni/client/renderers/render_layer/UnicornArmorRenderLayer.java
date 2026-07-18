package com.dead_comedian.farmerooni.client.renderers.render_layer;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.models.UnicornModel;
import com.dead_comedian.farmerooni.entities.Unicorn;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class UnicornArmorRenderLayer extends RenderLayer<Unicorn, UnicornModel<Unicorn>> {
    private final UnicornModel<Unicorn> model;

    public UnicornArmorRenderLayer(RenderLayerParent<Unicorn, UnicornModel<Unicorn>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.model = new UnicornModel<>(modelSet.bakeLayer(UnicornModel.ARMOR_LOCATION));
    }

    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Unicorn livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack itemstack = livingEntity.getBodyArmorItem();
        Item var13 = itemstack.getItem();
        if (var13 instanceof AnimalArmorItem animalarmoritem) {
            if (animalarmoritem.getBodyType() == AnimalArmorItem.BodyType.EQUESTRIAN) {
                ((UnicornModel<Unicorn>) this.getParentModel()).copyPropertiesTo(this.model);
                this.model.prepareMobModel(livingEntity, limbSwing, limbSwingAmount, partialTicks);
                this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                int i;
                if (itemstack.is(ItemTags.DYEABLE)) {
                    i = FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemstack, -6265536));
                } else {
                    i = -1;
                }
//                System.out.println();
                VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, animalarmoritem.getTexture().getPath().toString().replaceAll("horse", "unicorn"))));
                this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, i);
                return;
            }
        }
    }
}
