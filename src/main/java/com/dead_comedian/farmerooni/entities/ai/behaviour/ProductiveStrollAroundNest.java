package com.dead_comedian.farmerooni.entities.ai.behaviour;

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

public class ProductiveStrollAroundNest {

    public static OneShot<PathfinderMob> stroll(float speedModifier) {
        return strollFlyOrSwim(
                speedModifier,
                (mob) -> LandRandomPos.getPos(mob, 10, 7));
    }

    private static OneShot<PathfinderMob> strollFlyOrSwim(float speedModifier, Function<PathfinderMob, Vec3> target) {
        return BehaviorBuilder
            .create(
                (pathfinderMobInstance)
                -> pathfinderMobInstance.group(
                        pathfinderMobInstance.absent(MemoryModuleType.WALK_TARGET)
                ).apply(
                    pathfinderMobInstance,
                    (walkTargetMemoryAccessor) -> (serverLevel, pathfinderMob, l) -> {
                        if (pathfinderMob.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).isPresent()) {
                            Optional<Vec3> optional = Optional.ofNullable((Vec3) target.apply(pathfinderMob));

                            if (optional.isPresent()) {
                                BlockPos blockPos = new BlockPos((int) optional.get().x(), (int) optional.get().y(), (int) optional.get().z());
                                BlockPos nestPos = pathfinderMob.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();

                                if (blockPos.distToCenterSqr(nestPos.getX(), nestPos.getY(), nestPos.getZ()) < 10) {
                                    walkTargetMemoryAccessor.setOrErase(optional.map((vec3) -> new WalkTarget(vec3, speedModifier, 0)));
                                }
                            }
                            return true;
                            //todo scan the region or something
                        } else {
                            Optional<Vec3> optional2 = Optional.ofNullable((Vec3) target.apply(pathfinderMob));
                            walkTargetMemoryAccessor.setOrErase(optional2.map((p_258622_) -> new WalkTarget(p_258622_, speedModifier, 0)));
                            return true;
                        }
                    }
                )
            );
    }
}
