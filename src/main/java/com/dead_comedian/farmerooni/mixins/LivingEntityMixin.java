package com.dead_comedian.farmerooni.mixins;

import com.dead_comedian.farmerooni.entities.ai.RabbitAi;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.extensions.ILivingEntityExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin extends Entity implements Attackable, ILivingEntityExtension {


    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "makeBrain", at = @At("RETURN"))
    private Brain<?> farmerooni$deLobotomise(Brain<?> original, Dynamic<?> dynamic) {
        LivingEntity mob = (LivingEntity) (Object) this;

        if (mob instanceof Rabbit rabbit) {
            return RabbitAi.makeBrain(rabbit, Brain.provider(RabbitAi.MEMORY_MODULES, RabbitAi.SENSORS).makeBrain(dynamic));
        }

        return original;
    }

    @ModifyReturnValue(method = "brainProvider", at = @At("RETURN"))
    private Brain.Provider<?> farmerooni$heresYourBrainSir(Brain.Provider<?> original) {
        LivingEntity mob = (LivingEntity) (Object) this;

        if (mob instanceof Rabbit) {
            return Brain.provider(RabbitAi.MEMORY_MODULES, RabbitAi.SENSORS);
        }

        return original;
    }

    @ModifyReturnValue(method = "getBrain", at = @At("RETURN"))
    private Brain<?> farmerooni$brainWhere(Brain<?> original) {
        LivingEntity mob = (LivingEntity) (Object) this;

        if (mob instanceof Rabbit) {
            return (Brain<Rabbit>) original;
        }

        return original;
    }


    @Shadow
    public @Nullable LivingEntity getLastAttacker() {
        return null;
    }

    @Shadow
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Shadow
    public void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Shadow
    public void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
