package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.entities.Termite;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.Tree;
import com.dead_comedian.farmerooni.helper.TermiteHelper;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
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
public class CollectLumber extends Behavior<Termite> {
    int breakProgress;
    int breakTicks;

    final int breakCooldown = 5;

    public CollectLumber() {
        super(
            ImmutableMap.of(
                //FarmerooniMemoryModules.NEST_REST.get(), MemoryStatus.VALUE_PRESENT
                MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
                FarmerooniMemoryModules.INSIDE_NEST.get(), MemoryStatus.VALUE_ABSENT,
                FarmerooniMemoryModules.LUMBER.get(), MemoryStatus.VALUE_PRESENT,
                FarmerooniMemoryModules.LUMBER_CURSOR.get(), MemoryStatus.VALUE_PRESENT
            )
        );
    }

    @Override
    protected void start(ServerLevel serverLevel, Termite termite, long l) {
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Termite termite) {
        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel serverLevel, Termite termite, long l) {
        return true;
    }

    @Override
    protected void stop(ServerLevel serverLevel, Termite termite, long l) {
    }

    @Override
    protected void tick(ServerLevel serverLevel, Termite owner, long gameTime) {
        Brain<Termite> termbrain = owner.getBrain();
        if(termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).isEmpty()){
            termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER.get());
            termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get());

            termbrain.eraseMemory(FarmerooniMemoryModules.WANTS_DIGGING.get());
            termbrain.setMemory(FarmerooniMemoryModules.WANTS_REST.get(), true);
            return;
        }

        /*
        if(((CustomInventory) owner.getInventory()).isFull()){
            termbrain.eraseMemory(FarmerooniMemoryModules.WANTS_DIGGING.get());
            termbrain.setMemory(FarmerooniMemoryModules.WANTS_REST.get(), true);

            return;
        }

         */

        Tree woodStructure = termbrain.getMemory(FarmerooniMemoryModules.LUMBER.get()).get();

        Tree cursor = Tree.leaf(woodStructure);

        //todo, move on if cursor already broken or air

        termbrain.setMemory(
            MemoryModuleType.WALK_TARGET,
            new WalkTarget(
                Vec3.atCenterOf(cursor.pos),
                1.4f,
                0
            )
        );
        //Farmerooni.LOGGER.info("breaker cursor at {}, walking to it", cursor.pos);

        //dont be a sped
        if (owner.distanceToSqr(cursor.pos.getCenter()) >= 2){
            if (TermiteHelper.isWood(owner.level().getBlockState(cursor.pos).getBlock(), owner.level())) return;
        }

        if (chipAway(cursor, owner)){
            Tree parent = cursor.parent;

            if (parent == null){
                //are you gon stop it or something?
                //Farmerooni.LOGGER.info("breaker cursor parent doesnt exist, exiting collecting");

                termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER.get());
                termbrain.eraseMemory(FarmerooniMemoryModules.LUMBER_CURSOR.get());

                termbrain.eraseMemory(FarmerooniMemoryModules.WANTS_DIGGING.get());
                termbrain.setMemory(FarmerooniMemoryModules.WANTS_REST.get(), true);
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
    public boolean chipAway(Tree cursor, Termite trm){
        //avoid being a pickaxe lmao
        if (!TermiteHelper.isWood(trm.level().getBlockState(cursor.pos).getBlock(), trm.level())) return true;

        this.breakTicks++;

        if(this.breakTicks == this.breakCooldown){
            this.breakTicks = 0;
            this.breakProgress++;
        }
        trm.level().destroyBlockProgress(trm.getId(), cursor.pos, this.breakProgress);
        //Farmerooni.LOGGER.info("breaking block at cursor {}", this.breakProgress);


        if(this.breakProgress == 10){
            this.breakProgress = 0;

            if (!trm.level().getBlockState(cursor.pos).isEmpty()) trm.level().destroyBlock(cursor.pos, true);
            //Farmerooni.LOGGER.info("block broken at breaker cursor");

            return true;
        }

        return false;
    }
}
