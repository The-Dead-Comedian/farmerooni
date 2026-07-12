package com.dead_comedian.farmerooni.blocks;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniEntities;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TermiteEgg extends GlowLichenBlock {

    public TermiteEgg(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos poss, RandomSource random) {
        //level.levelEvent(2001, pos, Block.getId(state));
        Farmerooni.LOGGER.info("tickin shi");
        TermiteEntity term = FarmerooniEntities.TERMITE.get().create(level);
        if (term != null) {
            //term.setAge(-24000);

            term.moveTo((double) poss.getX() + 0.3 + (double) 1 * 0.2, (double) poss.getY(), (double) poss.getZ() + 0.3, 0.0F, 0.0F);

            for (BlockPos pos : BlockPos.betweenClosed(poss.offset(-15, -2, -15), poss.offset(15, 2, 15))) {
                Farmerooni.LOGGER.info("finding shi");
                if (level.getBlockState(pos).is(FarmerooniBlocks.TERMITE_NEST.get())) {
                    if(!((TermiteNestBlockEntity) level.getBlockEntity(pos)).addTermiteResident(term)){
                        term.getBrain().setMemory(FarmerooniMemoryModules.NEST.get(), pos);
                        Farmerooni.LOGGER.info("found shi");

                        break;
                    }
                }
            }

            level.addFreshEntity(term);
        }

        level.destroyBlock(poss, false);
    }




}
