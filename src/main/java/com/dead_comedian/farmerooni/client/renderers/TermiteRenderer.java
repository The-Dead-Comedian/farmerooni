package com.dead_comedian.farmerooni.client.renderers;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.models.TermiteModel;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TermiteRenderer extends MobRenderer<TermiteEntity, TermiteModel<TermiteEntity>> {
    public TermiteRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TermiteModel<>(pContext.bakeLayer(TermiteModel.LAYER_LOCATION)), 0.4f);

    }

    @Override
    public ResourceLocation getTextureLocation(TermiteEntity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "textures/entity/termite.png");
    }


    @Override
    public void render(TermiteEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}