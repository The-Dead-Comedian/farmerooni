package com.dead_comedian.farmerooni.mixins;

import com.dead_comedian.farmerooni.entities.ai.RabbitAi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends Animal implements VariantHolder<Variant> {


    protected RabbitMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"), cancellable = true)
    private void farmerooni$noGoalsMegamindFace(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "customServerAiStep", at = @At("HEAD"))
    private void farmerooni$customServerAiStep(CallbackInfo ci) {

        Rabbit mob = (Rabbit) (Object) this;
        this.level().getProfiler().push("rabbitBrain");
        ((Brain<Rabbit>) this.brain).tick((ServerLevel) this.level(), mob);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("rabbitActivityUpdate");
        RabbitAi.updateActivity(mob);
        this.level().getProfiler().pop();


        Path path = this.navigation.getPath();
        if (path != null && !path.isDone()) {
            float speed = brain.getMemory(MemoryModuleType.WALK_TARGET)
                    .map(WalkTarget::getSpeedModifier)
                    .orElse(1.0F);
            Vec3 next = path.getNextEntityPos(mob);
            this.moveControl.setWantedPosition(next.x, next.y, next.z, speed);
        }
    }


}