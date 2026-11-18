package com.dead_comedian.farmerooni.entities.ai.behaviour;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.lang.Float;
import java.util.*;
import java.util.function.Predicate;

public class AvoidEntity extends Behavior<LivingEntity> {

    private final float walkSpeedModifier;
    private final double sprintSpeedModifier;
    @Nullable
    protected LivingEntity toAvoid;
    protected final float maxDist;
    @Nullable
    protected Path path;
    protected final PathNavigation pathNav;
    protected final Class<? extends LivingEntity> avoidClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    public final TargetingConditions avoidEntityTargeting;

    public AvoidEntity(PathfinderMob mob, Class<? extends LivingEntity> entityClassToAvoid, float maxDistance, float walkSpeedModifier, double sprintSpeedModifier) {
        this(mob, entityClassToAvoid, (p_25052_) -> true, maxDistance, walkSpeedModifier, sprintSpeedModifier, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    public AvoidEntity(PathfinderMob mob, Class<? extends LivingEntity> entityClassToAvoid, Predicate<LivingEntity> avoidPredicate, float maxDistance, float walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
        super(ImmutableMap.of());
        this.avoidClass = entityClassToAvoid;
        this.avoidPredicate = avoidPredicate;
        this.maxDist = maxDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.predicateOnAvoidEntity = predicateOnAvoidEntity;
        this.pathNav = mob.getNavigation();
        this.avoidEntityTargeting = TargetingConditions.forCombat();
    }


    public AvoidEntity(PathfinderMob mob, Class<? extends LivingEntity> entityClassToAvoid, float maxDistance, float walkSpeedModifier, double sprintSpeedModifier, Predicate<LivingEntity> predicateOnAvoidEntity) {
        this(mob, entityClassToAvoid, (p_25049_) -> true, maxDistance, walkSpeedModifier, sprintSpeedModifier, predicateOnAvoidEntity);
    }


    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, LivingEntity owner) {

        List<? extends LivingEntity> list = level.getEntitiesOfClass(this.avoidClass,
                owner.getBoundingBox().inflate((double) this.maxDist, (double) 3.0F, (double) this.maxDist),
                (livingEntity) -> !livingEntity.isSpectator());
        if (!list.isEmpty()) {
            list.sort((entityA, entityB) -> Float.compare(owner.distanceTo(entityA), owner.distanceTo(entityB)));
            this.toAvoid = list.getFirst();


            System.out.println(this.toAvoid);

            if (this.toAvoid == null) {
                return false;
            } else {
                Vec3 vec3 = DefaultRandomPos.getPosAway((PathfinderMob) owner, 16, 7, this.toAvoid.position());
                if (vec3 == null) {
                    return false;
                } else if (this.toAvoid.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.toAvoid.distanceToSqr(owner)) {
                    return false;
                } else {
                    this.path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
                    return this.path != null;
                }
            }
        }
        return super.checkExtraStartConditions(level,owner);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, LivingEntity entity, long gameTime) {
        return !this.pathNav.isDone();
    }

    @Override
    protected void start(ServerLevel level, LivingEntity entity, long gameTime) {
        if (this.path != null) {
            BehaviorUtils.setWalkAndLookTargetMemories(entity, this.path.getTarget(), this.walkSpeedModifier, 1);
        }
    }

    @Override
    protected void stop(ServerLevel level, LivingEntity entity, long gameTime) {
        this.toAvoid = null;
    }

    @Override
    protected void tick(ServerLevel level, LivingEntity owner, long gameTime) {
        if (this.toAvoid!=null && owner.distanceToSqr(this.toAvoid) < (double) 49.0F) {
            ((PathfinderMob) owner).getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            ((PathfinderMob) owner).getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }
}
