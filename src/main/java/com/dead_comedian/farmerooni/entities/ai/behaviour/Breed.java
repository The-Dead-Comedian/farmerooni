package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class Breed extends Behavior<Animal> {
    private static final TargetingConditions PARTNER_TARGETING =
            TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
    private final float speedModifier;
    @Nullable private Animal partner;
    private int loveTime;

    public Breed(float speedModifier) {
        super(ImmutableMap.of(), 200, 400);
        this.speedModifier = speedModifier;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Animal owner) {
        if (!owner.isInLove()) return false;
        this.partner = findPartner(level, owner);
        return this.partner != null;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Animal owner, long gameTime) {
        return partner != null && partner.isAlive() && partner.isInLove()
                && loveTime < 60 && !partner.isPanicking();
    }

    @Override
    protected void start(ServerLevel level, Animal owner, long gameTime) {
        loveTime = 0;
    }

    @Override
    protected void tick(ServerLevel level, Animal owner, long gameTime) {
        if (partner == null) return;
        BehaviorUtils.setWalkAndLookTargetMemories(owner, partner, speedModifier, 1);
        if (++loveTime >= 60 && owner.distanceToSqr(partner) < 9.0D) {
            owner.spawnChildFromBreeding(level, partner);
        }
    }

    @Override
    protected void stop(ServerLevel level, Animal owner, long gameTime) {
        partner = null;
        loveTime = 0;
    }

    @Nullable
    private Animal findPartner(ServerLevel level, Animal owner) {
        List<? extends Animal> list = level.getNearbyEntities(
                owner.getClass(), PARTNER_TARGETING, owner,
                owner.getBoundingBox().inflate(8.0D));
        double best = Double.MAX_VALUE;
        Animal result = null;
        for (Animal other : list) {
            if (owner.canMate(other) && !other.isPanicking()
                    && owner.distanceToSqr(other) < best) {
                result = other;
                best = owner.distanceToSqr(other);
            }
        }
        return result;
    }
}