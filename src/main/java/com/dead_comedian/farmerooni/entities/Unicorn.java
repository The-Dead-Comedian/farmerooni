package com.dead_comedian.farmerooni.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AnimalArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Unicorn extends AbstractHorse {
    private static final EntityDataAccessor<Boolean> SKINNED = SynchedEntityData.defineId(Unicorn.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> POKE = SynchedEntityData.defineId(Unicorn.class, EntityDataSerializers.BOOLEAN);

    public Unicorn(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    public void handleStartJump(int jumpPower) {
        if (jumpPower < 40) {
            this.setPoke(!this.getPoke());
            level().playSound(this, this.blockPosition(), SoundEvents.ZOMBIE_HORSE_AMBIENT, SoundSource.NEUTRAL, 1, 1);
        } else {
            super.handleStartJump(jumpPower);
        }
    }

    protected void randomizeAttributes(RandomSource random) {
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MAX_HEALTH);
        Objects.requireNonNull(random);
        attributeInstance.setBaseValue(generateMaxHealth(random::nextInt));
        attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        Objects.requireNonNull(random);
        attributeInstance.setBaseValue(generateSpeed(random::nextDouble));
        attributeInstance = this.getAttribute(Attributes.JUMP_STRENGTH);
        Objects.requireNonNull(random);
        attributeInstance.setBaseValue(generateJumpStrength(random::nextDouble));
    }


    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SKINNED, false);
        builder.define(POKE, false);

    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("skinned", this.getSkin());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setSkin(compound.getBoolean("skinned"));
    }

    private void setSkin(boolean skinned) {
        this.entityData.set(SKINNED, skinned);
    }

    public boolean getSkin() {
        return this.entityData.get(SKINNED);
    }

    private void setPoke(boolean poke) {
         this.entityData.set(POKE, poke);
    }

    public boolean getPoke() {
         return this.entityData.get(POKE);
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        boolean flag = !this.isBaby() && this.isTamed() && player.isSecondaryUseActive();


        if (!this.isSaddled() && !this.isWearingBodyArmor() && player.getItemInHand(hand).is(Items.SHEARS)) {
            this.setSkin(true);
            BlockPos pos = this.blockPosition();
            level().playSound(this, pos, SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1, 1);
            level().playSound(this, pos, SoundEvents.ZOMBIE_HORSE_AMBIENT, SoundSource.NEUTRAL, 1, 1);
            for (int i = 0; i < 10; i++) {
                level().addParticle(ParticleTypes.DUST_PLUME, pos.getX() + this.level().getRandom().nextFloat(), pos.above().getY() + level().getRandom().nextFloat(), pos.getZ() + level().getRandom().nextFloat(), 0, 0, 0);
            }
            this.spawnAtLocation(new ItemStack(Items.ROTTEN_FLESH, random.nextIntBetweenInclusive(0, 5)), this.getEyeHeight());
            return InteractionResult.SUCCESS;
        }
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


    public void containerChanged(Container invBasic) {
        ItemStack itemstack = this.getBodyArmorItem();
        super.containerChanged(invBasic);
        ItemStack itemstack1 = this.getBodyArmorItem();
        if (this.tickCount > 20 && this.isBodyArmorItem(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }
    }

    @Override
    public void tick() {
        if (!level().isClientSide) {
            if (this.getPoke()) {
                double x = 4 * this.getLookAngle().x;
                double z = 4 * this.getLookAngle().z;

                AABB aabb = new AABB(this.getX() + x - 0.5, this.getY() - 1, this.getZ() + z - 0.5, this.getX() + x + 0.5, this.getY() + 0.5, this.getZ() + z + 0.5);

                //Makes sure the unicorn doesnt impale the rider's pets
                Predicate<Entity> predicate = (livingEntity) -> !(
                        livingEntity instanceof TamableAnimal tame
                                && this.getControllingPassenger() != null
                                && tame.getOwner() != null
                                && tame.getOwner().is(this.getControllingPassenger())
                )
                        && !livingEntity.is(this);


                List<Entity> damageMeBoi = level().getEntities(this, aabb, predicate);

                //todo: make custom damage source with custom death message

                System.out.println(damageMeBoi);
                for (Entity entity : damageMeBoi) {
                    if (entity instanceof LivingEntity livingEntity) {


                        Vec3 relativeVelocity = this.getDeltaMovement().subtract(livingEntity.getDeltaMovement());
                        double direction = Math.sqrt(Math.pow(relativeVelocity.x, 2)
                                + Math.pow(relativeVelocity.y, 2)
                                + Math.pow(relativeVelocity.z, 2));

                        livingEntity.hurt(this.damageSources().generic(), (float) (2.15 * direction));
                        livingEntity.knockback(1, -x, -z);
                    }
                }
            }
        }


        super.tick();
    }

    @Override
    public boolean dismountsUnderwater() {
        return super.dismountsUnderwater();
    }

    protected void playGallopSound(SoundType soundType) {
        super.playGallopSound(soundType);
        if (this.random.nextInt(10) == 0) {
            this.playSound(SoundEvents.HORSE_BREATHE, soundType.getVolume() * 0.6F, soundType.getPitch());
        }

    }

    protected SoundEvent getAmbientSound() {
        return this.getSkin() ? SoundEvents.SKELETON_HORSE_AMBIENT : SoundEvents.ZOMBIE_HORSE_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return this.getSkin() ? SoundEvents.SKELETON_HORSE_DEATH : SoundEvents.ZOMBIE_HORSE_DEATH;
    }

    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.getSkin() ? SoundEvents.SKELETON_HORSE_HURT : SoundEvents.ZOMBIE_HORSE_HURT;
    }

    protected SoundEvent getAngrySound() {
        return SoundEvents.HORSE_ANGRY;
    }

    @Override
    public @org.jetbrains.annotations.Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    public boolean canUseSlot(EquipmentSlot slot) {
        return true;
    }

    public boolean isBodyArmorItem(ItemStack stack) {
        Item var3 = stack.getItem();
        if (var3 instanceof AnimalArmorItem animalarmoritem) {
            if (animalarmoritem.getBodyType() == AnimalArmorItem.BodyType.EQUESTRIAN) {
                return true;
            }
        }

        return false;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {

        this.randomizeAttributes(level.getRandom());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}
