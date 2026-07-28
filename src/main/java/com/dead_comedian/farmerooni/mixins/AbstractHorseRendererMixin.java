package com.dead_comedian.farmerooni.mixins;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Mule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseRenderer.class)
public abstract class AbstractHorseRendererMixin {

    @Inject(method = "scale(Lnet/minecraft/world/entity/LivingEntity;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", at = @At("HEAD"))
    public void randomizeAttributesNew(LivingEntity par1, PoseStack par2, float par3, CallbackInfo ci) {
        double maxHP = par1.getAttribute(Attributes.MAX_HEALTH).getBaseValue() - 20;
        if (!(par1 instanceof Mule || par1 instanceof Donkey)) {
            par2.scale((float) (1 + maxHP / 43), (float) (1 + maxHP / 38), (float) (1 + maxHP / 43));
        }
    }
}
