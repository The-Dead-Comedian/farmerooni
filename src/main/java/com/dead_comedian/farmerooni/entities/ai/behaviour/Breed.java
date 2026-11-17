package com.dead_comedian.farmerooni.entities.ai.behaviour;

import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Animal;

import java.util.Map;

public class Breed  extends Behavior<Animal> {
    public Breed(Map<MemoryModuleType<?>, MemoryStatus> entryCondition) {
        super(entryCondition);
    }


}
