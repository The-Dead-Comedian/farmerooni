package com.dead_comedian.farmerooni.datagen;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FarmerooniBlockTagProvider extends BlockTagsProvider {

    public FarmerooniBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Farmerooni.MOD_ID, existingFileHelper);
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
        logs();
        signs();
        mineableAxe();
    }


    private void mineableAxe() {
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(FarmerooniBlocks.PUTRID_LOG.get())
                .add(FarmerooniBlocks.STRIPPED_PUTRID_LOG.get())
                .add(FarmerooniBlocks.PUTRID_WOOD.get())
                .add(FarmerooniBlocks.STRIPPED_PUTRID_WOOD.get())

                .add(FarmerooniBlocks.PUTRID_PLANKS.get())
                .add(FarmerooniBlocks.PUTRID_SLAB.get())
                .add(FarmerooniBlocks.PUTRID_STAIRS.get())
                .add(FarmerooniBlocks.PUTRID_BUTTON.get())
                .add(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get())

                .add(FarmerooniBlocks.PUTRID_CROWN.get())

                .add(FarmerooniBlocks.TERMITE_NEST.get())
                .add(FarmerooniBlocks.TERMITE_EGGS.get())
        ;
    }

    private void planks() {
        this.tag(BlockTags.PLANKS)
                .add(FarmerooniBlocks.PUTRID_PLANKS.get());
    }

    private void stairs() {
        this.tag(BlockTags.STAIRS)
                .add(FarmerooniBlocks.PUTRID_STAIRS.get());

        this.tag(BlockTags.WOODEN_STAIRS)
                .add(FarmerooniBlocks.PUTRID_STAIRS.get());
    }

    private void slabs() {
        this.tag(BlockTags.SLABS)
                .add(FarmerooniBlocks.PUTRID_SLAB.get());

        this.tag(BlockTags.WOODEN_SLABS)
                .add(FarmerooniBlocks.PUTRID_SLAB.get());
    }

    private void fences() {
        this.tag(BlockTags.WOODEN_FENCES)
                .add(FarmerooniBlocks.PUTRID_FENCE.get());
        this.tag(BlockTags.FENCES)
                .add(FarmerooniBlocks.PUTRID_FENCE.get());
    }

    private void fenceGates() {
        this.tag(BlockTags.FENCE_GATES)
                .add(FarmerooniBlocks.PUTRID_FENCE_GATE.get());
    }

    private void doors() {
        this.tag(BlockTags.WOODEN_DOORS)
                .add(FarmerooniBlocks.PUTRID_DOOR.get());
    }

    private void trapdoors() {
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(FarmerooniBlocks.PUTRID_TRAPDOOR.get());
    }

    private void pressurePlates() {
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get());
    }

    private void buttons() {
        this.tag(BlockTags.WOODEN_BUTTONS)
                .add(FarmerooniBlocks.PUTRID_BUTTON.get());
    }

    private void signs() {
        this.tag(BlockTags.STANDING_SIGNS)
                .add(FarmerooniBlocks.PUTRID_SIGN.get());
        this.tag(BlockTags.WALL_SIGNS)
                .add(FarmerooniBlocks.PUTRID_WALL_SIGN.get());
        this.tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(FarmerooniBlocks.PUTRID_HANGING_SIGN.get());
        this.tag(BlockTags.WALL_HANGING_SIGNS)
                .add(FarmerooniBlocks.PUTRID_WALL_HANGING_SIGN.get());
    }

    private void logs() {

        // TO-DO: custom block tags

//        this.tag(VerdanceBlockTags.MULBERRY_LOGS)
//                .add(FarmerooniBlocks.PUTRID_LOG.get())
//                .add(FarmerooniBlocks.PUTRID_WOOD.get())
//                .add(VerdanceBlocks.STRIPPED_MULBERRY_LOG.get())
//                .add(VerdanceBlocks.STRIPPED_MULBERRY_WOOD.get());
//        this.tag(BlockTags.LOGS_THAT_BURN)
//                .addTag(VerdanceBlockTags.MULBERRY_LOGS);
        this.tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(FarmerooniBlocks.PUTRID_LOG.get());
    }


}