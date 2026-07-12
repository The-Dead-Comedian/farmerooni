package com.dead_comedian.farmerooni;

import com.dead_comedian.farmerooni.client.renderers.TermiteRenderer;
import com.dead_comedian.farmerooni.client.screen.NestScreen;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniEntities;
import com.dead_comedian.farmerooni.registries.FarmerooniMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Farmerooni.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Farmerooni.MOD_ID, value = Dist.CLIENT)
public class FarmerooniClient {
    public FarmerooniClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(FarmerooniEntities.TERMITE.get(), TermiteRenderer::new);


        ItemBlockRenderTypes.setRenderLayer(FarmerooniBlocks.PUTRID_DOOR.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(FarmerooniBlocks.PUTRID_TRAPDOOR.get(), RenderType.cutoutMipped());


        Farmerooni.LOGGER.info("HELLO FROM CLIENT SETUP");
        Farmerooni.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
