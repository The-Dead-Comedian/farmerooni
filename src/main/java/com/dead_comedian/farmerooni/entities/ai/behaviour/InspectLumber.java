package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.Lumber;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Function;

/*
    https://www.geeksforgeeks.org/dsa/depth-first-search-or-dfs-for-a-graph/
    https://www.geeksforgeeks.org/dsa/dfs-n-ary-tree-acyclic-graph-represented-adjacency-list/
*/
public class InspectLumber {

    public static <E extends TermiteEntity> OneShot<E> InspectLumber(float speedModifier) {
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
                        TermiteEntityInstance.present(FarmerooniMemoryModules.LUMBER_CURSOR.get()),
                        TermiteEntityInstance.registered(FarmerooniMemoryModules.LUMBER.get())

                ).apply(
                    TermiteEntityInstance,
                    (
                        walkTargetMemoryAccessor,
                        currentLumber,
                        lumberMemory

                    ) -> (serverLevel, TermiteEntity, l) -> {
                        Brain<TermiteEntity> termbrain = TermiteEntity.getBrain();
                        BlockPos startingPoint = termbrain.getMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get()).get();

                        if (termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).isEmpty()) {
                            lumberMemory.set(new Lumber(startingPoint));
                            //Farmerooni.LOGGER.info("lumber tree memory has been created");

                        }

                        Lumber woodStructure = termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).get();;

                        //current node inspection
                        Lumber cursor = Lumber.find(woodStructure, startingPoint);

                        //dfs start
                        boolean foundNewNeighbour = false;

                        //for simplicity i think we should have a hard limit on how many blocks it recalls
                        for (Direction direction : Direction.values()) {

                            BlockPos neighbourPos = cursor.pos.relative(direction);

                            if (!serverLevel.getBlockState(neighbourPos).is(BlockTags.PLANKS)){
                                continue;
                            }

                            //dfs visited check
                            if (Lumber.contains(woodStructure, neighbourPos)){
                                //Farmerooni.LOGGER.info("{} has been visited at {}, checking next neighbour", neighbourPos, direction.toString());
                                continue;
                            }

                            Lumber child = new Lumber(neighbourPos, cursor);

                            cursor.addNeighbour(child);
                            //Farmerooni.LOGGER.info("added {} as neighbour to {}", cursor.pos, child.pos);

                            termbrain.setMemory(
                                    FarmerooniMemoryModules.LUMBER_CURSOR.get(),
                                    neighbourPos
                            );

                            termbrain.setMemory(
                                    FarmerooniMemoryModules.LUMBER.get(),
                                    woodStructure
                            );

                            walkTargetMemoryAccessor.set(new WalkTarget(
                                    Vec3.atCenterOf(neighbourPos),
                                    0.6f,
                                    0
                            ));
                            //Farmerooni.LOGGER.info("moving to the neighbour {}", neighbourPos);

                            //continue it looping
                            foundNewNeighbour = true;
                            break;
                        }

                        if (!foundNewNeighbour) {
                            //Farmerooni.LOGGER.info("ran out of neighbours, stopping everything, backtracking");

                            if (cursor.parent != null) {
                                termbrain.setMemory(
                                        FarmerooniMemoryModules.LUMBER_CURSOR.get(),
                                        cursor.parent.pos
                                );
                                //Farmerooni.LOGGER.info("cursor changing to cursor's parent");

                                walkTargetMemoryAccessor.set(new WalkTarget(
                                        Vec3.atCenterOf(cursor.parent.pos),
                                        speedModifier,
                                        0
                                ));
                                //Farmerooni.LOGGER.info("moving termite to cursor parent {}", cursor.parent.pos);

                            } else {
                                termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get());
                                termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER.get());

                                //Farmerooni.LOGGER.info("clearing cursor, and sending lil bro home");

                                walkTargetMemoryAccessor.set(new WalkTarget(
                                        termbrain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest(),
                                        speedModifier,
                                        0
                                ));
                            }
                        }

                        return true;
                    }
                )
            );
    }
}
