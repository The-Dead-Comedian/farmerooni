package com.dead_comedian.farmerooni.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements ContainerListener, HasCustomInventoryScreen, OwnableEntity, PlayerRideableJumping, Saddleable {

    @Unique
    private ItemStack farmerooni$prevArmor = ItemStack.EMPTY;

    protected AbstractHorseMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "containerChanged", at = @At("HEAD"))
    private void farmerooni$captureBeforeArmor(Container invBasic, CallbackInfo ci) {
        this.farmerooni$prevArmor = this.getBodyArmorItem();
    }

    @Inject(method = "containerChanged", at = @At("RETURN"))
    private void farmerooni$playArmorSound(Container invBasic, CallbackInfo ci) {
        LivingEntity mob = (LivingEntity) (Object) this;
        if (mob instanceof SkeletonHorse || mob instanceof ZombieHorse) {
            ItemStack itemstack1 = this.getBodyArmorItem();
            if (this.tickCount > 20 && this.isBodyArmorItem(itemstack1) && this.farmerooni$prevArmor != itemstack1) {
                this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
            }
        }
    }



    @Shadow
    public void containerChanged(Container container) { }

    @Shadow
    public boolean isFood(ItemStack itemStack) { return false; }


    @Shadow
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) { return null; }

    @Shadow
    public void openCustomInventoryScreen(Player player) { }

    @Shadow
    public @Nullable UUID getOwnerUUID() { return null; }

    @Shadow
    public void onPlayerJump(int i) { }

    @Shadow
    public boolean canJump() { return false; }

    @Shadow
    public void handleStartJump(int i) { }

    @Shadow
    public void handleStopJump() { }

    @Shadow
    public boolean isSaddleable() { return false; }

    @Shadow
    public void equipSaddle(ItemStack itemStack, @Nullable SoundSource soundSource) { }

    @Shadow
    public boolean isSaddled() { return false; }
}