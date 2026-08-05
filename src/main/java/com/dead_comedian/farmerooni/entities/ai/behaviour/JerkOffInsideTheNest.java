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
    int goonticks;
    public JerkOffInsideTheNest(int goonticks) {
        super(
            ImmutableMap.of(
                FarmerooniMemoryModules.WANTS_REST.get(), MemoryStatus.VALUE_PRESENT,
                FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_PRESENT
            ),
            goonticks
        );
        this.goonticks = goonticks;
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
        termite.getBrain().setMemory(FarmerooniMemoryModules.GOON_TIME.get(), this.goonticks);
    }

    @Override
    protected void tick(ServerLevel level, TermiteEntity termite, long gameTime) {
        termite.getBrain().setMemory(FarmerooniMemoryModules.GOON_TIME.get(), (termite.getBrain().getMemory(FarmerooniMemoryModules.GOON_TIME.get())).get()-1);


        if(this.goonticks == 0){
            this.doStop(level, termite, gameTime);
        }
    }

    @Override
    protected void stop(ServerLevel level, TermiteEntity termite, long gameTime) {
        termite.getBrain().eraseMemory(FarmerooniMemoryModules.WANTS_REST.get());
        termite.getBrain().eraseMemory(FarmerooniMemoryModules.GOON_TIME.get());

        //termite.getBrain().setMemory(FarmerooniMemoryModules.WANTS_DIGGING.get(), true);

        Brain<TermiteEntity> brain = termite.getBrain();
        BlockPos nest = brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();

        TermiteNestBlockEntity be =
            (TermiteNestBlockEntity) level.getBlockEntity(nest);

        be.TermiteWantOutHOOK(termite);


        Farmerooni.LOGGER.info("jorking stopped, energy 100");

    }
}

