package com.dead_comedian.farmerooni.datagen;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class FarmerooniBlockstatesProvider extends BlockStateProvider {
    public FarmerooniBlockstatesProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Farmerooni.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ResourceLocation planksTexture = this.blockTexture(FarmerooniBlocks.PUTRID_PLANKS.get());

//         Blocks
        simpleBlockWithItem(FarmerooniBlocks.TERMITE_NEST.get(), (ModelFile) this.models().cubeColumn(
                "termite_nest",
                ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/termite_nest"),
                ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/termite_nest_top")));


        simpleBlockWithItem(FarmerooniBlocks.PUTRID_PLANKS.get(), cubeAll(FarmerooniBlocks.PUTRID_PLANKS.get()));
        createWood(FarmerooniBlocks.PUTRID_WOOD.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_log"));
        createWood(FarmerooniBlocks.STRIPPED_PUTRID_WOOD.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/stripped_putrid_log"));

        createLog(FarmerooniBlocks.PUTRID_LOG.get());
        createLog(FarmerooniBlocks.STRIPPED_PUTRID_LOG.get());

        createButtonWithItem(FarmerooniBlocks.PUTRID_BUTTON.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_planks"));
        createFenceGatesWithItem(FarmerooniBlocks.PUTRID_FENCE_GATE.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_planks"));
        createFenceWithItem(FarmerooniBlocks.PUTRID_FENCE.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_planks"));
        createPressurePlateWithItem(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_planks"));
        createSlabWithItem(FarmerooniBlocks.PUTRID_SLAB.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_planks"));
        createStairsWithItem(FarmerooniBlocks.PUTRID_STAIRS.get(), ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "block/putrid_planks"));

        this.signBlock(
                FarmerooniBlocks.PUTRID_SIGN.get(),
                FarmerooniBlocks.PUTRID_WALL_SIGN.get(),
                planksTexture
        );
        this.hangingSignBlock(
                FarmerooniBlocks.PUTRID_HANGING_SIGN.get(),
                FarmerooniBlocks.PUTRID_WALL_HANGING_SIGN.get(),
                planksTexture
        );
        createTrapdoorWithItem(FarmerooniBlocks.PUTRID_TRAPDOOR.get());
        doorBlock(FarmerooniBlocks.PUTRID_DOOR.get(),
                ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID,"block/putrid_door_bottom"),
                ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID,"block/putrid_door_top"));
    }


    private void createLog(Block block) {
        this.logBlock((RotatedPillarBlock) block);
        this.simpleBlockItem(block, models().withExistingParent(
                this.getResourceLocation(block).getPath(),
                ResourceLocation.withDefaultNamespace("block/cube_column")
        ));
    }

    private void createWood(Block block, ResourceLocation texture) {
        this.axisBlock((RotatedPillarBlock) block, texture, texture);
        this.simpleBlockItem(block, models().withExistingParent(
                this.getResourceLocation(block).getPath(),
                ResourceLocation.withDefaultNamespace("block/cube_column")
        ));
    }

    private void createStairsWithItem(Block block, ResourceLocation texture) {
        this.stairsBlock((StairBlock) block, texture);
        this.simpleBlockItem(block, models().stairs(
                this.getResourceLocation(block).getPath(),
                texture,
                texture,
                texture
        ));
    }

    private void createSlabWithItem(Block block, ResourceLocation texture) {
        this.slabBlock((SlabBlock) block, texture, texture);
        this.simpleBlockItem(block, models().slab(
                this.getResourceLocation(block).getPath(),
                texture,
                texture,
                texture
        ));
    }

    private void createFenceWithItem(Block block, ResourceLocation texture) {
        this.fenceBlock((FenceBlock) block, texture);
        this.simpleBlockItem(block, models().fenceInventory(
                this.getResourceLocation(block).getPath(),
                texture
        ));
    }

    private void createFenceGatesWithItem(Block block, ResourceLocation texture) {
        this.fenceGateBlock((FenceGateBlock) block, texture);
        this.simpleBlockItem(block, models().fenceGate(
                this.getResourceLocation(block).getPath(),
                texture
        ));
    }

    private void createTrapdoorWithItem(Block block) {
        var texture = this.blockTexture(block);

        this.trapdoorBlock(
                (TrapDoorBlock) block,
                texture,
                true
        );
        this.simpleBlockItem(block, models().trapdoorOrientableBottom(
                this.getResourceLocation(block).getPath(),
                texture
        ));
    }

    private void createButtonWithItem(Block block, ResourceLocation texture) {
        this.buttonBlock((ButtonBlock) block, texture);
        this.simpleBlockItem(block, models().buttonInventory(
                this.getResourceLocation(block).getPath(),
                texture
        ));
    }

    private void createPressurePlateWithItem(Block block, ResourceLocation texture) {
        this.pressurePlateBlock((PressurePlateBlock) block, texture);
        this.simpleBlockItem(block, models().pressurePlate(
                this.getResourceLocation(block).getPath(),
                texture
        ));
    }

    private ResourceLocation getResourceLocation(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
