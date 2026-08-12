package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.dead_comedian.farmerooni.registries.FarmerooniSchedules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class RestOrExit extends Behavior<TermiteEntity> {
    public RestOrExit() {
        super(
            ImmutableMap.of(
                FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_PRESENT
            )
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, TermiteEntity termite) {
        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, TermiteEntity entity, long gameTime) {
        return true;
    }

    @Override
    protected void start(ServerLevel level, TermiteEntity termite, long gameTime) {
        termite.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void tick(ServerLevel level, TermiteEntity termite, long gameTime) {
        if (level.getDayTime() < FarmerooniSchedules.TERMITE_REST_TIME) this.doStop(level, termite, gameTime);
    }

    @Override
    protected void stop(ServerLevel level, TermiteEntity termite, long gameTime) {
        if(level.getDayTime() < FarmerooniSchedules.TERMITE_REST_TIME){
            Brain<TermiteEntity> brain = termite.getBrain();
            if(brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).isPresent()){
                BlockPos nest = brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();
                termite.getBrain().eraseMemory(FarmerooniMemoryModules.WANTS_REST.get());

                TermiteNestBlockEntity be =
                    (TermiteNestBlockEntity) level.getBlockEntity(nest);

                be.TermiteWantOutHOOK(termite);

            }


            Farmerooni.LOGGER.info("morrow sunshine");
        }
    }
}

