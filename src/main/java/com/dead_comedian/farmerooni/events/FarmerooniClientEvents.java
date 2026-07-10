package com.dead_comedian.farmerooni.events;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.client.models.TermiteModel;
import com.dead_comedian.farmerooni.client.renderers.TermiteRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Farmerooni.MOD_ID, value = Dist.CLIENT)
public class FarmerooniClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TermiteModel.LAYER_LOCATION, TermiteModel::createBodyLayer);
    }

}
