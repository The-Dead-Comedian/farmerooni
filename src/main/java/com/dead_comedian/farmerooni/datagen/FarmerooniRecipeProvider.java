package com.dead_comedian.farmerooni.datagen;

import com.dead_comedian.farmerooni.registries.FarmerooniBlockFamilies;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniItems;
import com.dead_comedian.farmerooni.registries.FarmerooniTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

import java.util.concurrent.CompletableFuture;

public class FarmerooniRecipeProvider extends RecipeProvider {
    public FarmerooniRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public void buildRecipes(RecipeOutput exporter) {
   FarmerooniBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateRecipe).forEach(family -> RecipeProvider.generateRecipes(exporter, family, FeatureFlagSet.of(FeatureFlags.VANILLA)));
        woodFromLogs(exporter, FarmerooniBlocks.PUTRID_WOOD.get(), FarmerooniBlocks.PUTRID_LOG.get());
        woodFromLogs(exporter, FarmerooniBlocks.STRIPPED_PUTRID_WOOD.get(), FarmerooniBlocks.STRIPPED_PUTRID_LOG.get());
        planksFromLog(exporter, FarmerooniBlocks.PUTRID_PLANKS.get(), FarmerooniTags.Items.PUTRID_LOGS, 4);

        woodenBoat(exporter, FarmerooniItems.PUTRID_BOAT.get(), FarmerooniBlocks.PUTRID_PLANKS.get());
        chestBoat(exporter, FarmerooniItems.PUTRID_CHEST_BOAT.get(), FarmerooniItems.PUTRID_BOAT.get());
        hangingSign(exporter, FarmerooniItems.PUTRID_HANGING_SIGN.get(), FarmerooniBlocks.STRIPPED_PUTRID_LOG.get());
    }
}
