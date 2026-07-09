package com.dead_comedian.farmerooni.datagen;

import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniItems;
import com.dead_comedian.farmerooni.registries.FarmerooniTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class FarmerooniItemTagProvider extends ItemTagsProvider {
    public FarmerooniItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    protected void addTags(HolderLookup.Provider arg) {

        planks();
        stairs();
        slabs();
        fences();
        fenceGates();
        doors();
        trapdoors();
        pressurePlates();
        buttons();
        signs();
        logs();
        boats();
      
    }
    

    private void planks() {
        this.tag(ItemTags.PLANKS).add(FarmerooniBlocks.PUTRID_PLANKS.get().asItem());
    }

    private void stairs() {
        this.tag(ItemTags.WOODEN_STAIRS).add(FarmerooniBlocks.PUTRID_STAIRS.get().asItem());
    }

    private void slabs() {
        this.tag(ItemTags.WOODEN_SLABS).add(FarmerooniBlocks.PUTRID_SLAB.get().asItem());
    }

    private void fences() {
        this.tag(ItemTags.WOODEN_FENCES).add(FarmerooniBlocks.PUTRID_FENCE.get().asItem());
    }

    private void fenceGates() {
        this.tag(ItemTags.FENCE_GATES).add(FarmerooniBlocks.PUTRID_FENCE_GATE.get().asItem());
    }

    private void doors() {
        this.tag(ItemTags.WOODEN_DOORS).add(FarmerooniBlocks.PUTRID_DOOR.get().asItem());
    }

    private void trapdoors() {
        this.tag(ItemTags.WOODEN_TRAPDOORS).add(FarmerooniBlocks.PUTRID_TRAPDOOR.get().asItem());
    }

    private void pressurePlates() {
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES).add(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get().asItem());
    }

    private void buttons() {
        this.tag(ItemTags.WOODEN_BUTTONS).add(FarmerooniBlocks.PUTRID_BUTTON.get().asItem());
    }

    private void signs() {
        this.tag(ItemTags.SIGNS).add(FarmerooniItems.PUTRID_SIGN.get());
        this.tag(ItemTags.HANGING_SIGNS).add(FarmerooniItems.PUTRID_HANGING_SIGN.get());
    }

    private void logs() {
        this.tag(ItemTags.LOGS_THAT_BURN).addTag(FarmerooniTags.Items.PUTRID_LOGS);
        this.tag(FarmerooniTags.Items.PUTRID_LOGS).add(FarmerooniBlocks.PUTRID_LOG.get().asItem()).add(FarmerooniBlocks.PUTRID_WOOD.get().asItem()).add(FarmerooniBlocks.STRIPPED_PUTRID_LOG.get().asItem()).add(FarmerooniBlocks.STRIPPED_PUTRID_WOOD.get().asItem());
    }

    private void boats() {
        this.tag(ItemTags.BOATS).add(FarmerooniItems.PUTRID_BOAT.get());
        this.tag(ItemTags.CHEST_BOATS).add(FarmerooniItems.PUTRID_CHEST_BOAT.get());
    }
    
}
