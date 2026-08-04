package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.NestData;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.parsing.packrat.Term;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class RestOrRegroup {

    public static class GoHome extends Behavior<TermiteEntity> {
        public GoHome() {
            super(
                ImmutableMap.of(
                    FarmerooniMemoryModules.NEST_DATA.get(), MemoryStatus.VALUE_PRESENT,
                    FarmerooniMemoryModules.WANTS_REST.get(), MemoryStatus.VALUE_PRESENT,
                    FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_ABSENT
                )
            );

        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, TermiteEntity termite) {
            return true;
        }

        @Override
        protected void start(ServerLevel level, TermiteEntity termite, long gameTime) {
            Brain<TermiteEntity> brain = termite.getBrain();

            BlockPos nest = brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();

            if (!brain.hasMemoryValue(MemoryModuleType.WALK_TARGET)) {
                brain.setMemory(
                    MemoryModuleType.WALK_TARGET,
                    new WalkTarget(nest, 1.0F, 0)
                );
            }
        }

        @Override
        protected void stop(ServerLevel level, TermiteEntity termite, long gameTime) {
            Brain<TermiteEntity> brain = termite.getBrain();

            BlockPos nest = brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();

            if (termite.distanceToSqr(Vec3.atCenterOf(nest.above())) <= 0.4) {
                TermiteNestBlockEntity be =
                    (TermiteNestBlockEntity) level.getBlockEntity(nest);

                if (be != null && be.TermiteWantInHOOK(termite)) {
                    be.TermiteRegroupOrRestHook(termite);
                }
            }
        }
    }

    public static class GoWork extends Behavior<TermiteEntity> {
        public GoWork() {
            super(
                ImmutableMap.of(
                    FarmerooniMemoryModules.NEST_DATA.get(), MemoryStatus.VALUE_PRESENT,
                    FarmerooniMemoryModules.LUMBER.get(), MemoryStatus.VALUE_PRESENT,
                    FarmerooniMemoryModules.LUMBER_CURSOR.get(), MemoryStatus.VALUE_ABSENT,
                    FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_ABSENT,
                    FarmerooniMemoryModules.WANTS_REST.get(), MemoryStatus.VALUE_ABSENT,
                    FarmerooniMemoryModules.DIG_LEADER.get(), MemoryStatus.VALUE_ABSENT
                )
            );

        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, TermiteEntity termite) {
            return true;
        }

        @Override
        protected void start(ServerLevel level, TermiteEntity termite, long gameTime) {
        }

        @Override
        protected void tick(ServerLevel level, TermiteEntity termite, long gameTime) {
            Brain<TermiteEntity> brain = termite.getBrain();

            BlockPos work = brain.getMemory(FarmerooniMemoryModules.LUMBER.get()).get().pos;

            //Farmerooni.LOGGER.info("walking to work");
            brain.setMemory(
                MemoryModuleType.WALK_TARGET,
                new WalkTarget(work, 1.0F, 0)
            );

            if (termite.distanceToSqr(Vec3.atCenterOf(work)) <= 1) {
                //Farmerooni.LOGGER.info("walked alladat");
                brain.setMemory(FarmerooniMemoryModules.WANTS_DIGGING.get(), true);
            }
        }

        @Override
        protected boolean canStillUse(ServerLevel level, TermiteEntity termite, long gameTime) {
            return termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER.get())
                && !termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER_CURSOR.get());
        }

        @Override
        protected void stop(ServerLevel level, TermiteEntity termite, long gameTime) {
        }
    }

    public static class FollowLeader extends Behavior<TermiteEntity> {
        public FollowLeader() {
            super(
                ImmutableMap.of(
                    FarmerooniMemoryModules.NEST_DATA.get(), MemoryStatus.VALUE_PRESENT,
                    FarmerooniMemoryModules.LUMBER.get(), MemoryStatus.VALUE_PRESENT,
                    FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_ABSENT,
                    FarmerooniMemoryModules.WANTS_REST.get(), MemoryStatus.VALUE_ABSENT,
                    FarmerooniMemoryModules.DIG_LEADER.get(), MemoryStatus.VALUE_PRESENT
                )
            );
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, TermiteEntity termite) {
            return true;
        }

        @Override
        protected void start(ServerLevel level, TermiteEntity termite, long gameTime) {
        }

        @Override
        protected void tick(ServerLevel level, TermiteEntity termite, long gameTime) {
            Brain<TermiteEntity> brain = termite.getBrain();

            Vec3 leader = brain.getMemory(FarmerooniMemoryModules.DIG_LEADER.get()).get().blockPosition().getBottomCenter();

            brain.setMemory(
                MemoryModuleType.WALK_TARGET,
                new WalkTarget(leader, 1.0F, 0)
            );
        }

        @Override
        protected boolean canStillUse(ServerLevel level, TermiteEntity termite, long gameTime) {
            return termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.WANTS_REST.get())
                && termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER.get())
                && !termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER_CURSOR.get());
        }

        @Override
        protected void stop(ServerLevel level, TermiteEntity termite, long gameTime) {
            Brain<TermiteEntity> brain = termite.getBrain();
            brain.eraseMemory(FarmerooniMemoryModules.DIG_LEADER.get());
        }
    }

}
