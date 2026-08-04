package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;

//wait 5 fucking seconds god fucking damn it
public class JerkOffInsideTheNest extends Behavior<TermiteEntity> {
    public JerkOffInsideTheNest() {
        super(
            ImmutableMap.of(
                FarmerooniMemoryModules.WANTS_REST.get(), MemoryStatus.VALUE_PRESENT,
                FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_PRESENT
            ),
            100
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
        Farmerooni.LOGGER.info("jorking it");
    }

    @Override
    protected void tick(ServerLevel level, TermiteEntity termite, long gameTime) {
    }

    @Override
    protected void stop(ServerLevel level, TermiteEntity termite, long gameTime) {
        termite.getBrain().eraseMemory(FarmerooniMemoryModules.WANTS_REST.get());

        Brain<TermiteEntity> brain = termite.getBrain();
        BlockPos nest = brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();

        TermiteNestBlockEntity be =
            (TermiteNestBlockEntity) level.getBlockEntity(nest);

        be.TermiteWantOutHOOK(termite);


        Farmerooni.LOGGER.info("jorking stopped, energy 100");

    }
}

