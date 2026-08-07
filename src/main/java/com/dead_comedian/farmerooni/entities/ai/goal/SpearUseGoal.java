//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.dead_comedian.farmerooni.entities.ai.goal;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class SpearUseGoal<T extends Monster> extends Goal {
    private static final double MAX_FLEEING_TIME = (double) reducedTickDelay(100);
    private final T mob;
    private @Nullable SpearUseState state;
    private final double speedModifierWhenCharging;
    private final double speedModifierWhenRepositioning;
    private final float approachDistanceSq;
    private final float targetInRangeRadiusSq;
    private int cooldown = 30;

    public SpearUseGoal(final T mob, final double speedModifierWhenCharging, final double speedModifierWhenRepositioning, final float approachDistance, final float targetInRangeRadius) {
        this.mob = mob;
        this.speedModifierWhenCharging = speedModifierWhenCharging;
        this.speedModifierWhenRepositioning = speedModifierWhenRepositioning;
        this.approachDistanceSq = approachDistance * approachDistance;
        this.targetInRangeRadiusSq = targetInRangeRadius * targetInRangeRadius;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        return this.ableToAttack() && !this.mob.isUsingItem();
    }

    private boolean ableToAttack() {
        return this.mob.getTarget() != null && this.mob.getVehicle() instanceof AbstractHorse;
    }

    public boolean canContinueToUse() {
        return this.state != null && !this.state.done && this.ableToAttack();
    }

    public void start() {
        super.start();
        this.mob.setAggressive(true);
        this.state = new SpearUseState();
    }

    public void stop() {
        super.stop();
        this.mob.getNavigation().stop();
        this.mob.setAggressive(false);
        this.state = null;
        this.mob.stopUsingItem();
    }

    public void tick() {
        if (this.state != null) {
            LivingEntity target = this.mob.getTarget();
            double targetDistSqr = this.mob.distanceToSqr(target.getX(), target.getY(), target.getZ());
            Entity mount = this.mob.getRootVehicle();
            float speedModifier = 1.0F;
            if (mount instanceof Mob) {
                speedModifier = 1.4F;
            }

            int mountDistance = this.mob.isPassenger() ? 2 : 0;
            this.mob.lookAt(target, 30.0F, 30.0F);
            this.mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (this.state.notEngagedYet()) {
                if (targetDistSqr > (double) this.approachDistanceSq) {
                    this.mob.getNavigation().moveTo(target, (double) speedModifier * this.speedModifierWhenRepositioning);
                    return;
                }

                this.state.startEngagement(60);
                this.mob.startUsingItem(InteractionHand.MAIN_HAND);
            }

            if (this.state.tickAndCheckEngagement()) {
                this.mob.stopUsingItem();
                double distance = Math.sqrt(targetDistSqr);
                this.state.awayPos = LandRandomPos.getPosAway((PathfinderMob) this.mob, (int) Math.max(0.0F, (14 + mountDistance) - distance), (int) Math.max(1.0F, (11 + mountDistance) - distance), target.position());
                this.state.fleeingTime = 1;
            }

            if (!this.state.tickAndCheckFleeing()) {
                if (this.state.awayPos != null) {
                    this.mob.getNavigation().moveTo(this.state.awayPos.x, this.state.awayPos.y, this.state.awayPos.z, (double) speedModifier * this.speedModifierWhenRepositioning);
                    if (this.mob.getNavigation().isDone()) {
                        if (this.state.fleeingTime > 0) {
                            this.state.done = true;
                            return;
                        }

                        this.state.awayPos = null;
                    }
                } else {
                    this.mob.getNavigation().moveTo(target, (double) speedModifier * this.speedModifierWhenCharging);
                    AABB aabb=this.mob.getBoundingBox().inflate(1.6);
                    if (aabb.intersects(this.mob.getTarget().getBoundingBox())) {
                        if (cooldown >= 0) {
                            this.cooldown++;
                        } else {
                            cooldown = 0;
                        }
                        if (cooldown >= 29) {
                            this.mob.getTarget().hurt(this.mob.level().damageSources().mobAttack(this.mob), 5);
                        }
                    }


                    if (targetDistSqr < (double) this.targetInRangeRadiusSq || this.mob.getNavigation().isDone()) {
                        double distance = Math.sqrt(targetDistSqr);
                        this.state.awayPos = LandRandomPos.getPosAway(this.mob, (int) ((6 + mountDistance) - distance), (int) ((7 + mountDistance) - distance), target.position());
                    }
                }

            }
        }
    }


    public static class SpearUseState {
        private int engageTime = -1;
        private int fleeingTime = -1;
        private @Nullable Vec3 awayPos;
        private boolean done = false;

        public boolean notEngagedYet() {
            return this.engageTime < 0;
        }

        public void startEngagement(final int spearDownTime) {
            this.engageTime = spearDownTime;
        }

        public boolean tickAndCheckEngagement() {
            if (this.engageTime > 0) {
                --this.engageTime;
                if (this.engageTime == 0) {
                    return true;
                }
            }

            return false;
        }

        public boolean tickAndCheckFleeing() {
            if (this.fleeingTime > 0) {
                ++this.fleeingTime;
                if ((double) this.fleeingTime > SpearUseGoal.MAX_FLEEING_TIME) {
                    this.done = true;
                    return true;
                }
            }

            return false;
        }
    }

}
