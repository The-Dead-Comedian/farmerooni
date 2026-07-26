package com.dead_comedian.farmerooni.mixins;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class TamableAnimalMixin {
    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void registerExtraGoals(CallbackInfo ci) {
        Mob mob = (Mob) (Object) this;
        if (mob instanceof TamableAnimal tamableAnimal) {
            tamableAnimal.goalSelector.addGoal(1, new AvoidEntityGoal<>(tamableAnimal, Creeper.class, 10f, 1.5, 1.5));
        }
    }
}
