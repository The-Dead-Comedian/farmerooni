package com.dead_comedian.farmerooni.registries;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class FarmerooniBlockFamilies {
    private static final Map<Block, BlockFamily> BLOCK_FAMILIES = Maps.newHashMap();
    
    public static final BlockFamily PUTRID = create(FarmerooniBlocks.PUTRID_PLANKS.get())
            .stairs(FarmerooniBlocks.PUTRID_STAIRS.get())
            .slab(FarmerooniBlocks.PUTRID_SLAB.get())
            .fence(FarmerooniBlocks.PUTRID_FENCE.get())
            .fenceGate(FarmerooniBlocks.PUTRID_FENCE_GATE.get())
            .door(FarmerooniBlocks.PUTRID_DOOR.get())
            .trapdoor(FarmerooniBlocks.PUTRID_TRAPDOOR.get())
            .pressurePlate(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get())
            .button(FarmerooniBlocks.PUTRID_BUTTON.get())
            .sign(FarmerooniBlocks.PUTRID_SIGN.get(), FarmerooniBlocks.PUTRID_WALL_SIGN.get())
            .recipeGroupPrefix("wooden")
            .recipeUnlockedBy("has_planks")
            .dontGenerateModel()
            .getFamily();

    private static BlockFamily.Builder create(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockFamily = BLOCK_FAMILIES.put(baseBlock, builder.getFamily());
        if (blockFamily != null) {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(baseBlock));
        } else {
            return builder;
        }
    }

    public static Stream<BlockFamily> getAllFamilies() {
        return BLOCK_FAMILIES.values().stream();
    }
}
