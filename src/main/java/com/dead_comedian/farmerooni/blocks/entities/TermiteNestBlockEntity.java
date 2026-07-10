package com.dead_comedian.farmerooni.blocks.entities;

import com.dead_comedian.farmerooni.registries.FarmerooniBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TermiteNestBlockEntity extends BlockEntity {
    public TermiteNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(FarmerooniBlockEntities.TERMITE_NEST_BLOCK_ENTITY.get(), pos, blockState);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {

        if (world.getRandom().nextInt(5) == 0) {
            BlockPos blockpos1 = pos.offset(world.getRandom().nextInt(11) - 5,
                    world.getRandom().nextInt(8) - 2,
                    world.getRandom().nextInt(11) - 5);

            for (int k = 0; k < 5; ++k) {
                if (level.isEmptyBlock(blockpos1) && Blocks.BROWN_MUSHROOM.defaultBlockState().canSurvive(level, blockpos1)) {
                    pos = blockpos1;
                }

                blockpos1 = pos.offset(world.getRandom().nextInt(11) - 5,
                        world.getRandom().nextInt(8) - 5,
                        world.getRandom().nextInt(11) - 5);
            }

            if (level.isEmptyBlock(blockpos1) && Blocks.BROWN_MUSHROOM.defaultBlockState().canSurvive(level, blockpos1)) {
                level.setBlock(blockpos1, Blocks.BROWN_MUSHROOM.defaultBlockState(), 2);
            }
        }


    }

}
