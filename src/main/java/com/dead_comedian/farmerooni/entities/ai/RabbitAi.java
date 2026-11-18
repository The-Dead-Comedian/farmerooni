package com.dead_comedian.farmerooni.entities.ai;

import com.dead_comedian.farmerooni.entities.Termite;
import com.dead_comedian.farmerooni.entities.ai.behaviour.*;
import com.dead_comedian.farmerooni.entities.ai.behaviour.Float;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.dead_comedian.farmerooni.registries.FarmerooniSensorTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;
import java.util.Set;

public class RabbitAi {

    public static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            FarmerooniMemoryModules.NEAREST_CROP.get());

    public static final ImmutableList<SensorType<? extends Sensor<? super Rabbit>>> SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES,
            FarmerooniSensorTypes.FRESH_CROPS_IN_YOUR_AREA.get()
    );

    public static Brain<?> makeBrain(Rabbit rabbit, Brain<Rabbit> brain) {
        initCoreActivity(rabbit, brain);
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

    private static void initCoreActivity(Rabbit rabbit, Brain<Rabbit> brain) {
        brain.addActivity(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        new Float(rabbit),
                        new ClimbOnTopOfPowderSnow(rabbit),
                        new Breed(1.2F),
                        new Tempt(1.0F, (stack) -> stack.is(ItemTags.RABBIT_FOOD), false),
                        new AvoidEntity(rabbit, Player.class, 8.0F, 2.2f, 2.2) {
                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, LivingEntity owner) {
                                return rabbit.getVariant() != Rabbit.Variant.EVIL && super.checkExtraStartConditions(level, owner);
                            }
                        },
                        new AvoidEntity(rabbit, Wolf.class, 8.0F, 2.2f, 2.2) {
                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, LivingEntity owner) {
                                return rabbit.getVariant() != Rabbit.Variant.EVIL && super.checkExtraStartConditions(level, owner);
                            }
                        },
                        new AvoidEntity(rabbit, Fox.class, 8.0F, 2.2f, 2.2) {
                            @Override
                            protected boolean checkExtraStartConditions(ServerLevel level, LivingEntity owner) {
                                return rabbit.getVariant() != Rabbit.Variant.EVIL && super.checkExtraStartConditions(level, owner);
                            }
                        }

                )
        );
    }

    private static void initIdleActivity(Brain<Rabbit> brain) {
        brain.addActivity(
                Activity.IDLE,
                0,
                ImmutableList.of(
                        new EatCrops(),
                        RandomStroll.stroll(1),
                        new RandomLookAround(UniformInt.of(10, 15), 30, 30, 90)
                )
        );
    }


}
