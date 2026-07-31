package com.dead_comedian.farmerooni.entities.ai.sensor_type;

import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.dead_comedian.farmerooni.registries.FarmerooniSensorTypes;
import com.dead_comedian.farmerooni.registries.FarmerooniTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Set;

public class InsideNestSensor extends Sensor<TermiteEntity> {
    int counter = 1;

    @Override
    protected void doTick(ServerLevel serverLevel, TermiteEntity termite) {
        BlockPos blockPos = termite.blockPosition();
        boolean flag = false;

        do {
            if (counter < 10) {
                if (serverLevel.getBlockState(blockPos.above(counter)).is(FarmerooniTags.Blocks.NEST_INTERIOR_BLOCKS)) {
                    termite.getBrain().setMemory(FarmerooniMemoryModules.IN_NEST.get(), Unit.INSTANCE);
                    System.out.println(FarmerooniMemoryModules.IN_NEST.get());
                    flag = true;
                }
            } else {
                counter = 1;
                termite.getBrain().eraseMemory(FarmerooniMemoryModules.IN_NEST.get());
                flag = true;
            }


        } while (!flag);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of();
    }
}
