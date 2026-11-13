package com.dead_comedian.farmerooni.entities.ai;

import com.dead_comedian.farmerooni.entities.Termite;
import com.dead_comedian.farmerooni.entities.ai.behaviour.*;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.dead_comedian.farmerooni.registries.FarmerooniSensorTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public class RabbitAi {

    public static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH
    );

    public static final ImmutableList<SensorType<? extends Sensor<? super Rabbit>>> SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES
    );

    public static Brain<?> makeBrain(Rabbit rabbit, Brain<Rabbit> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);


        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(Rabbit rabbit) {
        rabbit.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
                Activity.IDLE
        ));
    }

    private static void initCoreActivity(Brain<Rabbit> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink()
                        )
        );
    }

    private static void initIdleActivity(Brain<Rabbit> brain) {
        brain.addActivity(
                Activity.IDLE,
                0,
                ImmutableList.of(
                        RandomStroll.stroll(1),
                        new RandomLookAround(UniformInt.of(10, 15), 30, 30, 90)
                )
        );
    }


}
