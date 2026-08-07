package com.dead_comedian.farmerooni.mixins;

import com.dead_comedian.farmerooni.entities.Unicorn;
import com.dead_comedian.farmerooni.entities.ai.goal.SpearUseGoal;
import com.dead_comedian.farmerooni.registries.FarmerooniEntities;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Zombie.class)
public abstract class ZombieMixin {
    @Inject(method = "addBehaviourGoals", at = @At("HEAD"))
    public void addAttackHorseGoals(CallbackInfo ci) {
        Zombie zombie = (Zombie) (Object) this;
        zombie.goalSelector.addGoal(1, new SpearUseGoal<>(zombie, (double) 1.0F, (double) 1.0F, 15.0F, 1.0F));
    }

    @Inject(method = "finalizeSpawn", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/monster/Zombie;setCanBreakDoors(Z)V"
    ))
    public void spawnZobieHonse(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        RandomSource randomsource = level.getRandom();
        Zombie zombie = (Zombie) (Object) this;

        if (spawnGroupData instanceof Zombie.ZombieGroupData) {
            if (randomsource.nextFloat() <= 0.2F) {
                RandomSource randomsource2 = level.getRandom();
                if (randomsource2.nextFloat() > 0.3) {
                    ZombieHorse horse = EntityType.ZOMBIE_HORSE.create(zombie.level());
                    if (horse != null) {
                        horse.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), 0.0F);
                        horse.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null);
                        zombie.startRiding(horse);
                        zombie.equipItemIfPossible(Items.IRON_SWORD.getDefaultInstance());
                        level.addFreshEntity(horse);
                    }
                } else {
                    Unicorn horse = FarmerooniEntities.UNICORN.get().create(zombie.level());
                    if (horse != null) {
                        horse.moveTo(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYRot(), 0.0F);
                        horse.finalizeSpawn(level, difficulty, MobSpawnType.JOCKEY, null);
                        zombie.startRiding(horse);
                        level.addFreshEntity(horse);
                    }
                }
            }
        }
    }
}
