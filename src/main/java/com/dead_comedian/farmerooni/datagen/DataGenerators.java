package com.dead_comedian.farmerooni.datagen;


import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Farmerooni.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        var blockTagProvider = new FarmerooniBlockTagProvider(
                generator.getPackOutput(),
                event.getLookupProvider(),
                event.getExistingFileHelper()
        );
        generator.addProvider(event.includeServer(), new FarmerooniBlockstatesProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), blockTagProvider);
        generator.addProvider(
                event.includeServer(),
                new FarmerooniItemTagProvider(
                        generator.getPackOutput(),
                        event.getLookupProvider(),
                        blockTagProvider.contentsGetter()
                )
        );
        generator.addProvider(event.includeClient(), new FarmerooniRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeClient(), new FarmerooniItemModelProvider(packOutput, Farmerooni.MOD_ID, existingFileHelper));

        generator.addProvider(true, new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(FarmerooniBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));


    }
}