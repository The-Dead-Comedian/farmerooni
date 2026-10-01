package com.dead_comedian.farmerooni.client.renderers;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.models.SeagullModel;
import com.dead_comedian.farmerooni.client.models.TermiteModel;
import com.dead_comedian.farmerooni.entities.Seagull;
import com.dead_comedian.farmerooni.entities.Termite;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SeagullRenderer extends MobRenderer<Seagull, SeagullModel<Seagull>> {
    public SeagullRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new SeagullModel<>(pContext.bakeLayer(SeagullModel.LAYER_LOCATION)), 0.4f);

    }

    @Override
    public ResourceLocation getTextureLocation(Seagull pEntity) {
        return ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "textures/entity/seagull.png");
    }


    @Override
    public void render(Seagull pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}