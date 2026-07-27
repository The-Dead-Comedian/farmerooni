package com.dead_comedian.farmerooni.entities.ai;

import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.ai.behaviour.ProductiveStrollAroundNest;
import com.dead_comedian.farmerooni.entities.ai.behaviour.RandomStrollAroundNest;
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
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public class TermiteAi {

    public static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.NEAREST_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.PATH,
            FarmerooniMemoryModules.NEST_DATA.get(),
            FarmerooniMemoryModules.SCOUT_HAS_DISCOVERY.get()

    );

    public static final ImmutableList<SensorType<? extends Sensor<? super TermiteEntity>>> SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            FarmerooniSensorTypes.NEST_VALID_SENSOR.get(),
            FarmerooniSensorTypes.TILFS_NEAR_ME_SENSOR.get()
    );

    public static Brain<?> makeBrain(TermiteEntity termite, Brain<TermiteEntity> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initWarActivity(termite, brain);
        initScoutingActivity(brain);

        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void updateActivity(TermiteEntity termite) {
        /*
        termite.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
                Activity.FIGHT,
                Activity.IDLE,
                Activity.INVESTIGATE
        ));

         */
        if (termite.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            termite.getBrain().setActiveActivityIfPossible(Activity.FIGHT);
        }

        termite.setAggressive(termite.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET));
    }

    private static void initCoreActivity(Brain<TermiteEntity> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink()
                )
        );
    }


    private static void initIdleActivity(Brain<TermiteEntity> brain) {
        brain.addActivity(
                Activity.IDLE,
                0,
                ImmutableList.of(
                        RandomStrollAroundNest.stroll(1),
                        new RandomLookAround(UniformInt.of(10, 15), 30, 30, 90)
                )
        );

    }

    private static void initWarActivity(TermiteEntity termite, Brain<TermiteEntity> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.<Mob>create(p_35118_ -> !p_35118_.isAlive()),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.3F),
                        SetEntityLookTarget.create(p_219535_ -> isTarget(termite, p_219535_), (float) termite.getAttributeValue(Attributes.FOLLOW_RANGE)),

                        MeleeAttack.create(30)
                ),
                MemoryModuleType.ATTACK_TARGET
        );

    }

    private static void initScoutingActivity(Brain<TermiteEntity> brain) {
        brain.addActivity(
                Activity.INVESTIGATE,
                0,
                ImmutableList.of(
                        ProductiveStrollAroundNest.stroll(1F),
                        new DoNothing(5, 10)
                )
        );

    }

    private static boolean isTarget(TermiteEntity termite, LivingEntity entity) {
        return termite.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(p_219509_ -> p_219509_ == entity).isPresent();
    }

}
