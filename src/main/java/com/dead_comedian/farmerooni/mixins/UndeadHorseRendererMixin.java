package com.dead_comedian.farmerooni.mixins;

import com.dead_comedian.farmerooni.client.renderers.render_layer.UndeadArmorRenderLayer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UndeadHorseRenderer.class)
public class UndeadHorseRendererMixin {


    @Inject(method = "<init>", at = @At("TAIL"))
    private void randomizeAttributesNew(EntityRendererProvider.Context context, ModelLayerLocation layer, CallbackInfo ci) {
        ((UndeadHorseRenderer) (Object) this).addLayer(new UndeadArmorRenderLayer(((UndeadHorseRenderer) (Object) this), context.getModelSet()));
    }


}
