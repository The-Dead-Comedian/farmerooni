package com.dead_comedian.farmerooni.client.renderers;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.models.UnicornModel;
import com.dead_comedian.farmerooni.client.renderers.render_layer.UnicornArmorRenderLayer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class UnicornRenderer extends MobRenderer<Horse, UnicornModel<Horse>> {

    public UnicornRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new UnicornModel<>(pContext.bakeLayer(UnicornModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new UnicornArmorRenderLayer(this,pContext.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Horse pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "textures/entity/unicorn/unicorn.png");
    }


    @Override
    public void render(Horse pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }


}