package com.dead_comedian.farmerooni;

import com.dead_comedian.farmerooni.registries.*;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(Farmerooni.MOD_ID)
public class Farmerooni {
    public static final String MOD_ID = "farmerooni";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Farmerooni(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        FarmerooniBlocks.init(modEventBus);
        FarmerooniItems.init(modEventBus);
        FarmerooniTabs.init(modEventBus);
        FarmerooniBlockEntities.init(modEventBus);
        FarmerooniEntities.init(modEventBus);
        FarmerooniMenus.init(modEventBus);
        FarmerooniMemoryModules.init(modEventBus);
        FarmerooniSensorTypes.init(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
