package com.dead_comedian.farmerooni.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public class MobMixin {


    @ModifyReturnValue(method = "canUseSlot", at = @At("RETURN"))
    private boolean declareAttributesNew(boolean original) {

        LivingEntity mob = (LivingEntity) (Object) this;
        if (mob instanceof ZombieHorse) {
            return true;
        }
        if (mob instanceof SkeletonHorse) {
            return true;
        }
        return true;
    }

    @ModifyReturnValue(method = "isBodyArmorItem", at = @At("RETURN"))
    private boolean farmerooni$allowArmorOnUndeadHorses(boolean original, ItemStack stack) {
        if (original) return true;

        LivingEntity mob = (LivingEntity) (Object) this;
        if (mob instanceof SkeletonHorse || mob instanceof ZombieHorse) {
            if (stack.getItem() instanceof AnimalArmorItem animalArmorItem
                    && animalArmorItem.getBodyType() == AnimalArmorItem.BodyType.EQUESTRIAN) {
                return true;
            }
        }
        return original;
    }
}
