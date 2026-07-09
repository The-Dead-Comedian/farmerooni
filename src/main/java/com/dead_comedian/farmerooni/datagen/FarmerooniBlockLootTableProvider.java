package com.dead_comedian.farmerooni.datagen;


import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class FarmerooniBlockLootTableProvider extends BlockLootSubProvider {
    protected FarmerooniBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(FarmerooniBlocks.PUTRID_LOG.get());
        dropSelf(FarmerooniBlocks.PUTRID_WOOD.get());
        dropSelf(FarmerooniBlocks.STRIPPED_PUTRID_LOG.get());
        dropSelf(FarmerooniBlocks.STRIPPED_PUTRID_WOOD.get());
        dropSelf(FarmerooniBlocks.PUTRID_PLANKS.get());
        dropSelf(FarmerooniBlocks.PUTRID_SLAB.get());
        dropSelf(FarmerooniBlocks.PUTRID_STAIRS.get());
        dropSelf(FarmerooniBlocks.PUTRID_BUTTON.get());
        dropSelf(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get());
        dropSelf(FarmerooniBlocks.PUTRID_FENCE.get());
        dropSelf(FarmerooniBlocks.PUTRID_FENCE_GATE.get());
        dropSelf(FarmerooniBlocks.TERMITE_EGGS.get());
        dropSelf(FarmerooniBlocks.PUTRID_CROWN.get());
        dropSelf(FarmerooniBlocks.TERMITE_NEST.get());
        dropSelf(FarmerooniBlocks.PUTRID_HANGING_SIGN.get());
        dropSelf(FarmerooniBlocks.PUTRID_SIGN.get());
        dropSelf(FarmerooniBlocks.PUTRID_WALL_HANGING_SIGN.get());
        dropSelf(FarmerooniBlocks.PUTRID_WALL_SIGN.get());
        dropSelf(FarmerooniBlocks.PUTRID_TRAPDOOR.get());
        add(FarmerooniBlocks.PUTRID_DOOR.get(), this::createDoorTable);
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {

        return FarmerooniBlocks.BLOCKS.getEntries().stream().map(Holder::value)
//                .filter(block -> block != HolyHellBlocks.ATLAS_STATUE.get())
                ::iterator;
    }
}