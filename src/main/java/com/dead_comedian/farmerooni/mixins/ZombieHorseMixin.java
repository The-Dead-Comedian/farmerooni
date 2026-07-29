package com.dead_comedian.farmerooni.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ZombieHorse.class)
public class ZombieHorseMixin extends AbstractHorse {
    protected ZombieHorseMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "randomizeAttributes", at = @At("HEAD"))
    public void randomizeAttributesNew(RandomSource random, CallbackInfo ci) {
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MAX_HEALTH);
        Objects.requireNonNull(random);
        attributeInstance.setBaseValue(generateMaxHealth(random::nextInt));
        attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        Objects.requireNonNull(random);
        attributeInstance.setBaseValue(generateSpeed(random::nextDouble));
    }

    @ModifyReturnValue(method = "mobInteract", at = @At("RETURN"))
    private InteractionResult declareAttributesNew(InteractionResult original,Player player, InteractionHand hand) {
        boolean flag = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();
        if (!this.isVehicle() && !flag) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (!itemstack.isEmpty()) {
                if (this.isFood(itemstack)) {
                    return this.fedFood(player, itemstack);
                }

                if (!this.isTamed()) {
                    this.makeMad();
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }
            }

            return super.mobInteract(player, hand);
        } else {
            return super.mobInteract(player, hand);
        }
    }
    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder declareAttributesNew(AttributeSupplier.Builder original) {

        return createBaseHorseAttributes().add(Attributes.MAX_HEALTH, (double)15.0F);
    }
}
