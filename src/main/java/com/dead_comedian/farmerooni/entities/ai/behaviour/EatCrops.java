package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EatCrops extends Behavior<Rabbit> {
    public EatCrops() {
        super(ImmutableMap.of(FarmerooniMemoryModules.NEAREST_CROP.get(), MemoryStatus.VALUE_PRESENT));
    }

    private int tttimer = 40;

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Rabbit owner) {
        return owner.getBrain().hasMemoryValue(FarmerooniMemoryModules.NEAREST_CROP.get());
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Rabbit entity, long gameTime) {
        return true;
    }


    @Override
    protected void tick(ServerLevel level, Rabbit owner, long gameTime) {
        super.tick(level, owner, gameTime);

        if (owner.getBrain().hasMemoryValue(FarmerooniMemoryModules.NEAREST_CROP.get())) {
            BlockPos pos = owner.getBrain().getMemory(FarmerooniMemoryModules.NEAREST_CROP.get()).get();

            BehaviorUtils.setWalkAndLookTargetMemories(owner, pos, 1f, 0);
            if (pos.getCenter().equals(owner.blockPosition().getCenter()) && level.getBlockState(pos).getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(level.getBlockState(pos))) {
                BlockState block = level.getBlockState(pos).getBlock().defaultBlockState();
                owner.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                if (tttimer > 0) {
                    tttimer--;
                    if (tttimer % 2 == 0) {
                        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, level.getBlockState(pos)), pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, 5, 0, 0, 0, 0);
                    }
                } else {
                    level.destroyBlock(pos, true);
                    level.setBlock(pos, block, 3);

                    tttimer = 40;
                }
            }

        }
    }
}
