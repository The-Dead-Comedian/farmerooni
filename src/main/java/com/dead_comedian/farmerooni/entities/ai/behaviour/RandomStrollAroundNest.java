package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.PathfinderMob;
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


    public static OneShot<PathfinderMob> stroll(float speedModifier, boolean mayStrollFromWater, boolean canClimb) {
        Function<PathfinderMob, Vec3> target = canClimb
                ? RandomStrollAroundNest::getClimbablePos
                : (p_258601_) -> LandRandomPos.getPos(p_258601_, 10, 7);

        return strollFlyOrSwim(
                speedModifier,
                target,
                mayStrollFromWater ? (p_258615_) -> true : (p_350044_) -> !p_350044_.isInWaterOrBubble()
        );
    }

    private static Vec3 getClimbablePos(PathfinderMob mob) {
        RandomSource random = mob.getRandom();
        Level level = mob.level();
        BlockPos origin = mob.blockPosition();

        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(15) - 7;
            int y = random.nextInt(11) - 3;
            int z = random.nextInt(15) - 7;
            BlockPos candidate = origin.offset(x, y, z);

            if (!level.getBlockState(candidate).isAir()) {
                continue;
            }

            boolean onGround = !level.getBlockState(candidate.below()).isAir();
            boolean againstWall = !level.getBlockState(candidate.north()).isAir()
                    || !level.getBlockState(candidate.south()).isAir()
                    || !level.getBlockState(candidate.east()).isAir()
                    || !level.getBlockState(candidate.west()).isAir();

            if (onGround || againstWall) {
                return Vec3.atBottomCenterOf(candidate);
            }
        }
        return null;
    }

    private static OneShot<PathfinderMob> strollFlyOrSwim(
            float speedModifier,
            Function<PathfinderMob, Vec3> target,
            Predicate<PathfinderMob> canStroll
    ) {
        return BehaviorBuilder.create((pathfinderMobInstance) -> pathfinderMobInstance.group(
                pathfinderMobInstance.absent(MemoryModuleType.WALK_TARGET),
                pathfinderMobInstance.absent(FarmerooniMemoryModules.LUMBER.get())
        ).apply(pathfinderMobInstance, (walkTargetMemoryAccessor, lumbermemoerythingy) -> (serverLevel, pathfinderMob, l) -> {
            if (!canStroll.test(pathfinderMob)) {
                return false;
            } else {
                if (pathfinderMob.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).isPresent()) {
                    Optional<Vec3> optional = Optional.ofNullable(target.apply(pathfinderMob));
                    if (optional.isPresent()) {
                        Level level = pathfinderMob.level();
                        BlockPos blockPos = new BlockPos(
                                (int) optional.get().x(),
                                (int) optional.get().y(),
                                (int) optional.get().z()
                        );
                        BlockPos nestPos = pathfinderMob.getBrain()
                                .getMemory(FarmerooniMemoryModules.NEST_DATA.get())
                                .get()
                                .nest();
                        DifficultyInstance difficulty = level.getCurrentDifficultyAt(nestPos);
                        if (blockPos.distToCenterSqr(nestPos.getX(), nestPos.getY(), nestPos.getZ()) < 10) {
                            walkTargetMemoryAccessor.setOrErase(
                                    optional.map((vec3) -> new WalkTarget(vec3, speedModifier, 0))
                            );
                        }
                    }
                    return true;
                } else {
                    Optional<Vec3> optional2 = Optional.ofNullable(target.apply(pathfinderMob));
                    walkTargetMemoryAccessor.setOrErase(
                            optional2.map((p_258622_) -> new WalkTarget(p_258622_, speedModifier, 0))
                    );
                    return true;
                }
            }
        }));
    }
}