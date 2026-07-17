package com.dead_comedian.farmerooni.entities.ai;

import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class RandomStrollAroundNest {

    public static OneShot<PathfinderMob> stroll(float speedModifier) {
        return stroll(speedModifier, true);
    }

    public static OneShot<PathfinderMob> stroll(float speedModifier, boolean mayStrollFromWater) {
        return strollFlyOrSwim(speedModifier, (p_258601_) -> LandRandomPos.getPos(p_258601_, 10, 7), mayStrollFromWater ? (p_258615_) -> true : (p_350044_) -> !p_350044_.isInWaterOrBubble());
    }

    public static BehaviorControl<PathfinderMob> stroll(float speedModifier, int maxHorizontalDistance, int maxVerticalDistance) {
        return strollFlyOrSwim(speedModifier, (p_258605_) -> LandRandomPos.getPos(p_258605_, maxHorizontalDistance, maxVerticalDistance), (p_258616_) -> true);
    }

    private static OneShot<PathfinderMob> strollFlyOrSwim(float speedModifier, Function<PathfinderMob, Vec3> target, Predicate<PathfinderMob> canStroll) {
        return BehaviorBuilder.create((pathfinderMobInstance) -> pathfinderMobInstance.group(pathfinderMobInstance.absent(MemoryModuleType.WALK_TARGET)).apply(pathfinderMobInstance, (walkTargetMemoryAccessor) -> (serverLevel, pathfinderMob, l) -> {
                    if (!canStroll.test(pathfinderMob)) {
                        return false;
                    } else {
                        if (pathfinderMob.getBrain().getMemory(FarmerooniMemoryModules.NEST.get()).isPresent()) {
                            Optional<Vec3> optional = Optional.ofNullable((Vec3) target.apply(pathfinderMob));
                            if (optional.isPresent()) {
                                Level level = pathfinderMob.level();
                                BlockPos blockPos = new BlockPos((int) optional.get().x(), (int) optional.get().y(), (int) optional.get().z());
                                BlockPos nestPos = pathfinderMob.getBrain().getMemory(FarmerooniMemoryModules.NEST.get()).get();
                                DifficultyInstance difficulty = level.getCurrentDifficultyAt(nestPos);
                                if (blockPos.distToCenterSqr(nestPos.getX(), nestPos.getY(), nestPos.getZ()) < 10) {
                                    walkTargetMemoryAccessor.setOrErase(optional.map((vec3) -> new WalkTarget(vec3, speedModifier, 0)));
                                }
                            }
                            return true;
                        } else {
                            Optional<Vec3> optional2 = Optional.ofNullable((Vec3) target.apply(pathfinderMob));
                            walkTargetMemoryAccessor.setOrErase(optional2.map((p_258622_) -> new WalkTarget(p_258622_, speedModifier, 0)));
                            return true;
                        }

                    }
                }

        ));
    }
}
