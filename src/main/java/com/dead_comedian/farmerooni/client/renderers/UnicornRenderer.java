package com.dead_comedian.farmerooni.client.renderers;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.models.UnicornModel;
import com.dead_comedian.farmerooni.client.renderers.render_layer.UnicornArmorRenderLayer;
import com.dead_comedian.farmerooni.entities.Unicorn;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Horse;

public class UnicornRenderer extends MobRenderer<Unicorn, UnicornModel<Unicorn>> {

    public UnicornRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new UnicornModel<>(pContext.bakeLayer(UnicornModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new UnicornArmorRenderLayer(this, pContext.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Unicorn pEntity) {
        return pEntity.getSkin() ? ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "textures/entity/unicorn/unicorn_skeleton.png") : ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "textures/entity/unicorn/unicorn.png");
    }


    @Override
    public void render(Unicorn pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }


}