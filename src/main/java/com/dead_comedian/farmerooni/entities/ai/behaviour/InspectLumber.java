package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.Tree;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.Vec3;

/*
    https://www.geeksforgeeks.org/dsa/depth-first-search-or-dfs-for-a-graph/
    https://www.geeksforgeeks.org/dsa/dfs-n-ary-tree-acyclic-graph-represented-adjacency-list/
*/
public class InspectLumber extends Behavior<TermiteEntity> {

    public InspectLumber() {
        super(
            ImmutableMap.of(
                //FarmerooniMemoryModules.NEST_REST.get(), MemoryStatus.VALUE_PRESENT
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                FarmerooniMemoryModules.LUMBER_CURSOR.get(), MemoryStatus.VALUE_PRESENT,
                FarmerooniMemoryModules.LUMBER.get(), MemoryStatus.REGISTERED
            )
        );
    }

    @Override
    protected void start(ServerLevel serverLevel, TermiteEntity termite, long l) {
        Brain<TermiteEntity> termbrain = termite.getBrain();
        BlockPos startingPoint = termbrain.getMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get()).get();

        if (termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).isEmpty()) {
            termbrain.setMemory(FarmerooniMemoryModules.LUMBER.get(), new Tree(startingPoint));
            Farmerooni.LOGGER.info("lumber tree memory has been created");
        }
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, TermiteEntity termite) {
        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, TermiteEntity termite, long l) {
        return termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER_CURSOR.get())
            && termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER.get());
    }

    @Override
    protected void stop(ServerLevel serverLevel, TermiteEntity termite, long l) {
    }

    @Override
    protected void tick(ServerLevel serverLevel, TermiteEntity owner, long gameTime) {
        //dont be a sped
        if (owner.getNavigation().isInProgress()) {
            return;
        }

        Brain<TermiteEntity> termbrain = owner.getBrain();
        BlockPos startingPoint = termbrain.getMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get()).get();

        Tree woodStructure = termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).get();



        //current node inspection
        Tree cursor = Tree.find(woodStructure, startingPoint);

        //dfs start
        boolean foundNewNeighbour = false;

        //for simplicity i think we should have a hard limit on how many blocks it recalls
        for (Direction direction : Direction.values()) {

            BlockPos neighbourPos = cursor.pos.relative(direction);

            if (!serverLevel.getBlockState(neighbourPos).is(BlockTags.PLANKS)) {
                continue;
            }

            //dfs visited check
            if (Tree.contains(woodStructure, neighbourPos)) {
                //Farmerooni.LOGGER.info("{} has been visited at {}, checking next neighbour", neighbourPos, direction.toString());
                continue;
            }

            Tree child = new Tree(neighbourPos, cursor);

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

            termbrain.setMemory(
                MemoryModuleType.WALK_TARGET,
                new WalkTarget(
                    Vec3.atCenterOf(neighbourPos),
                    0.6f,
                    0
                )
            );
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

                termbrain.setMemory(
                    MemoryModuleType.WALK_TARGET,
                    new WalkTarget(
                        Vec3.atCenterOf(cursor.parent.pos),
                        1.0F,
                        0
                    )
                );

                //Farmerooni.LOGGER.info("moving termite to cursor parent {}", cursor.parent.pos);

            } else {
                termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get());
                termbrain.setMemory(FarmerooniMemoryModules.WANTS_REST.get(), true);
                termbrain.setActiveActivityIfPossible(Activity.REST);

                //termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER.get());

                Farmerooni.LOGGER.info("clearing cursor, and sending lil bro home setting rest tru");

            }
            //Lumber.printTree(woodStructure);
            return;
        }
    }

    public static <E extends TermiteEntity> OneShot<E> InspectLumber(float speedModifier) {
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
                            lumberMemory.set(new Tree(startingPoint));
                            //Farmerooni.LOGGER.info("lumber tree memory has been created");

                        }

                        Tree woodStructure = termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).get();
                        ;

                        //current node inspection
                        Tree cursor = Tree.find(woodStructure, startingPoint);

                        //dfs start
                        boolean foundNewNeighbour = false;

                        //for simplicity i think we should have a hard limit on how many blocks it recalls
                        for (Direction direction : Direction.values()) {

                            BlockPos neighbourPos = cursor.pos.relative(direction);

                            if (!serverLevel.getBlockState(neighbourPos).is(BlockTags.PLANKS)) {
                                continue;
                            }

                            //dfs visited check
                            if (Tree.contains(woodStructure, neighbourPos)) {
                                //Farmerooni.LOGGER.info("{} has been visited at {}, checking next neighbour", neighbourPos, direction.toString());
                                continue;
                            }

                            Tree child = new Tree(neighbourPos, cursor);

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
                                termbrain.setActiveActivityIfPossible(Activity.REST);
                                //termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER.get());

                                //Farmerooni.LOGGER.info("clearing cursor, and sending lil bro home");

                                /*
                                walkTargetMemoryAccessor.set(new WalkTarget(
                                        termbrain.getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest(),
                                        speedModifier,
                                        0
                                ));

                                 */
                                return false;
                            }
                        }

                        return true;
                    }
                )
            );
    }
}
