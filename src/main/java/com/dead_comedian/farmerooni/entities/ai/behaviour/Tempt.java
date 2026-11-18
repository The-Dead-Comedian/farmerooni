package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;

public class Tempt extends Behavior<Animal> {

    private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range((double) 15.0F).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    private final float speedModifier;
    private double pRotX;
    private double pRotY;
    private int calmDown;
    private boolean isRunning;
    private final Predicate<ItemStack> items;
    private final boolean canScare;

    public Tempt(float speedModifier, Predicate<ItemStack> items, boolean canScare) {
        super(ImmutableMap.of());
        this.speedModifier = speedModifier;
        this.items = items;
        this.canScare = canScare;
        this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);

    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, Animal owner) {

        if (this.calmDown > 0) {
            --this.calmDown;
            return false;
        } else {
            return level.getNearestPlayer(this.targetingConditions, owner) != null;
        }
    }


    private boolean shouldFollow(LivingEntity entity) {
        return this.items.test(entity.getMainHandItem()) || this.items.test(entity.getOffhandItem());
    }

    @Override
    protected boolean canStillUse(ServerLevel level, Animal entity, long gameTime) {
        if (this.canScare()) {
            Player player = level.getNearestPlayer(this.targetingConditions, entity);
            if (player != null) {
                if (entity.distanceToSqr(player) < (double) 36.0F) {
                    if (Math.abs((double) player.getXRot() - this.pRotX) > (double) 5.0F || Math.abs(player.getYRot() - this.pRotY) > (double) 5.0F) {
                        return false;
                    }
                }
                this.pRotX = player.getXRot();
                this.pRotY = player.getYRot();
            }
        }

        return this.checkExtraStartConditions(level, entity);
    }

    protected boolean canScare() {
        return this.canScare;
    }


    @Override
    protected void start(ServerLevel level, Animal entity, long gameTime) {
        super.start(level, entity, gameTime);
        Player player = level.getNearestPlayer(this.targetingConditions, entity);
        this.isRunning = player != null;

    }

    @Override
    protected void stop(ServerLevel level, Animal entity, long gameTime) {
        super.stop(level, entity, gameTime);


        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
        this.calmDown = 100;
        this.isRunning = false;
    }


    public boolean isRunning() {
        return this.isRunning;
    }

    @Override
    protected void tick(ServerLevel level, Animal owner, long gameTime) {
        super.tick(level, owner, gameTime);
        Player player = level.getNearestPlayer(this.targetingConditions, owner);
        if (player != null) {
            BehaviorUtils.lookAtEntity(owner, player);
            if (owner.distanceToSqr(player) < (double) 5F) {
                owner.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            } else {
                BehaviorUtils.setWalkAndLookTargetMemories(owner, player, speedModifier, 1);
            }
        }
    }
}
