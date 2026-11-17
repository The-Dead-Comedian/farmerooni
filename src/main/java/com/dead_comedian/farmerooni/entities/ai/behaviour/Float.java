package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;


public class Float extends Behavior<Mob> {
    public Float(Mob mob) {
        super(ImmutableMap.of());
        if (mob.getNavigation() != null) {
            mob.getNavigation().setCanFloat(true);
        }
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Mob mob) {
        return mob.isInWater() && mob.getFluidHeight(FluidTags.WATER) > mob.getFluidJumpThreshold() || mob.isInLava() || mob.isInFluidType((fluidType, height) -> mob.canSwimInFluidType(fluidType) && height > mob.getFluidJumpThreshold());
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Mob entity, long gameTime) {
        return true;
    }

    @Override
    protected void tick(ServerLevel level, Mob owner, long gameTime) {
        super.tick(level, owner, gameTime);
        owner.getJumpControl().jump();
    }
}
