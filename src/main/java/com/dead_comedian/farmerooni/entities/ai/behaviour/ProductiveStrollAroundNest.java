package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ProductiveStrollAroundNest {

    public static <E extends TermiteEntity> OneShot<E> stroll(float speedModifier) {
        return strollFlyOrSwim(
                speedModifier,
                (mob) -> LandRandomPos.getPos(mob, 10, 7));
    }

    private static <E extends TermiteEntity> OneShot<E> strollFlyOrSwim(float speedModifier, Function<TermiteEntity, Vec3> target) {
        return BehaviorBuilder
            .create(
                (TermiteEntityInstance)
                -> TermiteEntityInstance.group(
                        TermiteEntityInstance.absent(MemoryModuleType.WALK_TARGET),
                        TermiteEntityInstance.absent(FarmerooniMemoryModules.LUMBER_CURSOR.get())

                ).apply(
                    TermiteEntityInstance,
                    (
                        walkTargetMemoryAccessor,
                        scoutDiscovery

                    ) -> (serverLevel, TermiteEntity, l) -> {
                        if (TermiteEntity.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).isPresent()) {
                            Optional<Vec3> optional = Optional.ofNullable((Vec3) target.apply(TermiteEntity));

                            if (optional.isPresent()) {
                                BlockPos blockPos = new BlockPos((int) optional.get().x(), (int) optional.get().y(), (int) optional.get().z());
                                BlockPos nestPos = TermiteEntity.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest();

                                if (blockPos.distToCenterSqr(nestPos.getX(), nestPos.getY(), nestPos.getZ()) < 20) {
                                    walkTargetMemoryAccessor.setOrErase(optional.map((vec3) -> new WalkTarget(vec3, speedModifier, 0)));
                                }
                            }
                            //todo scan the region or something
                            Optional<BlockPos> starterooni = BlockPos.betweenClosedStream(
                                    TermiteEntity.blockPosition().offset(-1,-1,-1),
                                    TermiteEntity.blockPosition().offset(1,1,1)
                            ).filter(
                                    pos -> serverLevel.getBlockState(pos).is(BlockTags.PLANKS)
                            ).findFirst();

                            if(starterooni.isPresent()){
                                TermiteEntity.getBrain().setMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get(), starterooni);
                                Farmerooni.LOGGER.info("termite found a piece of plank, starting lumber inspection");
                            }

                            return true;
                        } else {
                            Optional<Vec3> optional2 = Optional.ofNullable((Vec3) target.apply(TermiteEntity));
                            walkTargetMemoryAccessor.setOrErase(optional2.map((p_258622_) -> new WalkTarget(p_258622_, speedModifier, 0)));
                            return true;
                        }
                    }
                )
            );
    }
}
