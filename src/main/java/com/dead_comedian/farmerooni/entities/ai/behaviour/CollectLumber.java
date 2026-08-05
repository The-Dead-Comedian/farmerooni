package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.Tree;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.phys.Vec3;

/*
    https://www.geeksforgeeks.org/dsa/depth-first-search-or-dfs-for-a-graph/
    https://www.geeksforgeeks.org/dsa/dfs-n-ary-tree-acyclic-graph-represented-adjacency-list/
*/
public class CollectLumber extends Behavior<TermiteEntity> {
    int breakProgress;
    int breakTicks;

    final int breakCooldown = 5;

    public CollectLumber() {
        super(
            ImmutableMap.of(
                //FarmerooniMemoryModules.NEST_REST.get(), MemoryStatus.VALUE_PRESENT
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                FarmerooniMemoryModules.LUMBER.get(), MemoryStatus.VALUE_PRESENT
            )
        );
    }

    @Override
    protected void start(ServerLevel serverLevel, TermiteEntity termite, long l) {
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, TermiteEntity termite) {
        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, TermiteEntity termite, long l) {
        return termite.getBrain().hasMemoryValue(FarmerooniMemoryModules.LUMBER.get());
    }

    @Override
    protected void stop(ServerLevel serverLevel, TermiteEntity termite, long l) {
    }

    @Override
    protected void tick(ServerLevel serverLevel, TermiteEntity owner, long gameTime) {
        Brain<TermiteEntity> termbrain = owner.getBrain();
        Tree woodStructure = termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).get();

        Tree cursor = Tree.leaf(woodStructure);

        termbrain.setMemory(
            MemoryModuleType.WALK_TARGET,
            new WalkTarget(
                Vec3.atCenterOf(cursor.pos),
                0.6f,
                0
            )
        );
        //Farmerooni.LOGGER.info("breaker cursor at {}, walking to it", cursor.pos);

        //dont be a sped
        if (owner.getNavigation().isInProgress()) {
            return;
        }


        if (chipAway(cursor, owner)){
            Tree parent = cursor.parent;

            if (parent == null){
                //are you gon stop it or something?
                //Farmerooni.LOGGER.info("breaker cursor parent doesnt exist, exiting collecting");

                termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER.get());
                termbrain.eraseMemory(FarmerooniMemoryModules.WANTS_DIGGING.get());
            }
            else {
                parent.removeNeighbour(cursor);
                //Farmerooni.LOGGER.info("list {} removed {}", parent.pos, cursor.pos);
            }
        }
    }
    /*
        return means block was broken
     */
    public boolean chipAway(Tree cursor, TermiteEntity trm){
        //avoid being a pickaxe lmao
        if (!trm.level().getBlockState(cursor.pos).is(BlockTags.PLANKS)) return true;

        this.breakTicks++;

        if(this.breakTicks == this.breakCooldown){
            this.breakTicks = 0;
            this.breakProgress++;
        }
        trm.level().destroyBlockProgress(trm.getId(), cursor.pos, this.breakProgress);
        //Farmerooni.LOGGER.info("breaking block at cursor {}", this.breakProgress);

        if(this.breakProgress == 10){
            this.breakProgress = 0;

            trm.level().removeBlock(cursor.pos, false);
            //Farmerooni.LOGGER.info("block broken at breaker cursor");

            return true;
        }
        return false;
    }
}
