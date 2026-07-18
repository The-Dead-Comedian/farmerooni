package com.dead_comedian.farmerooni.datagen;

import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FarmerooniItemModelProvider extends ItemModelProvider {
    public FarmerooniItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(FarmerooniBlocks.PUTRID_SIGN.get().asItem());
        basicItem(FarmerooniBlocks.PUTRID_HANGING_SIGN.get().asItem());
        basicItem(FarmerooniBlocks.PUTRID_DOOR.get().asItem());
        basicItem(FarmerooniItems.PUTRID_BOAT.get().asItem());
        basicItem(FarmerooniItems.PUTRID_CHEST_BOAT.get().asItem());
        spawnEggItem(FarmerooniItems.TERMITE_SPAWN_EGG.get().asItem());
        spawnEggItem(FarmerooniItems.UNICORN_SPAWN_EGG.get().asItem());
    }
}
