package com.dead_comedian.farmerooni.mixins;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class TamableAnimalMixin extends LivingEntity implements EquipmentUser, Leashable, Targeting {
    protected TamableAnimalMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void registerExtraGoals(CallbackInfo ci) {
        Mob mob = ((Mob) (Object) this);
        if (mob instanceof TamableAnimal tamableAnimal) {
            tamableAnimal.goalSelector.addGoal(1, new AvoidEntityGoal<>(tamableAnimal, Creeper.class, 10f, 1.5, 1.5));
        }
    }

    @Shadow
    public void setDropChance(EquipmentSlot equipmentSlot, float v) {

    }

    @Shadow
    public @Nullable LeashData getLeashData() {
        return null;
    }

    @Shadow
    public void setLeashData(@Nullable Leashable.LeashData leashData) {

    }

    @Shadow
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Shadow
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return null;
    }

    @Shadow
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Shadow
    public HumanoidArm getMainArm() {
        return null;
    }

    @Shadow
    public @Nullable LivingEntity getTarget() {
        return null;
    }
}
