package com.dead_comedian.farmerooni.entities.ai.sensor_type;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Set;

public class NestValidSensor extends Sensor<LivingEntity> {
    @Override
    protected void doTick(ServerLevel serverLevel, LivingEntity livingEntity) {

    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of();
    }
}
