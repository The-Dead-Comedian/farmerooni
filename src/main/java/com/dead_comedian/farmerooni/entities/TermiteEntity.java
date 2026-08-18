package com.dead_comedian.farmerooni.entities;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.ai.TermiteAi;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.NestData;
import com.dead_comedian.farmerooni.helper.TermiteHelper;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.dead_comedian.farmerooni.registries.FarmerooniSchedules;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class TermiteEntity extends Animal implements InventoryCarrier {
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(TermiteEntity.class, EntityDataSerializers.BYTE);

    private static final Vec3i ITEM_PICKUP_REACH = new Vec3i(3, 3, 3);
    private final SimpleContainer inventory = new SimpleContainer(16);//new CustomInventory();

    public TermiteEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    /// ///////////////////////////////

    public final AnimationState idleAnimationState = new AnimationState();


    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        this.writeInventoryToTag(nbt, this.registryAccess());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.readInventoryFromTag(nbt, this.registryAccess());
    }

    @Override
    protected Vec3i getPickupReach() {
        return ITEM_PICKUP_REACH;
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return TermiteHelper.isWood(stack, this.level()) && hasSpaceInInventory();
    }

    @Override
    public boolean canTakeItem(ItemStack itemstack) {
        return true;
    }

    public boolean hasSpaceInInventory() {
        int count = 0;
        for (ItemStack stack : this.getInventory().getItems()) {
            count = count + stack.getCount();
        }
        return count < 16;
    }

    public int getSpaceInInventory() {
        int count = 0;
        for (ItemStack stack : this.getInventory().getItems()) {
            count = count + stack.getCount();
        }


        return 16 - count;
    }

    @Override
    public void aiStep() {
        for (ItemEntity itementity : this.level()
                .getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(getPickupReach().getX(), getPickupReach().getY(), getPickupReach().getZ()))) {
            if (!itementity.isRemoved() && !itementity.getItem().isEmpty() && !itementity.hasPickUpDelay() && this.wantsToPickUp(itementity.getItem())) {
                this.pickUpItem(itementity);
            }
        }

        super.aiStep();
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        Farmerooni.LOGGER.info("pikced up item {}", itemEntity);
        ItemStack initialStack = itemEntity.getItem();

        if (initialStack.getCount() > getSpaceInInventory()) {

            ItemStack pickUpStack = new ItemStack(initialStack.getItem(), getSpaceInInventory());
            ItemStack remainingStack = new ItemStack(initialStack.getItem(), initialStack.getCount() - getSpaceInInventory());


            ItemEntity pickUpItemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), pickUpStack, 0, 0, 0);
            ItemEntity remainingItemEntity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), remainingStack, 0, 0, 0);

            itemEntity.discard();
            this.level().addFreshEntity(remainingItemEntity);

            InventoryCarrier.pickUpItem(this, this, pickUpItemEntity);
        } else {
            InventoryCarrier.pickUpItem(this, this, itemEntity);
        }

    }

    @Override
    protected void sendDebugPackets() {
        super.sendDebugPackets();
        DebugPackets.sendEntityBrain(this);
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("termiteBrain");
        ((Brain<TermiteEntity>) this.brain).tick((ServerLevel) this.level(), this);
        this.level().getProfiler().pop();

        this.level().getProfiler().push("termiteActivityUpdateFromSchedule");
        brain.updateActivityFromSchedule(
                level().getDayTime(),
                level().getGameTime()
        );
        this.level().getProfiler().pop();


        this.level().getProfiler().push("termiteActivityUpdate");
        TermiteAi.updateActivity(this);
        this.level().getProfiler().pop();

        super.customServerAiStep();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain bbraing = this.brainProvider().makeBrain(dynamic);
        bbraing.setSchedule(FarmerooniSchedules.TERMITESCHDEULE.get());
        return TermiteAi.makeBrain(this, bbraing);
    }

    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }


    public boolean isClimbing() {

        // additional memory checks look for whether the memory is false or absent, therefore not letting it climb while IN the nest
        return ((Byte) this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
        //&& (((this.getBrain().getMemory(FarmerooniMemoryModules.INSIDE_NEST.get()).isPresent() && !this.getBrain().getMemory(FarmerooniMemoryModules.INSIDE_NEST.get()).get())) || this.getBrain().getMemory(FarmerooniMemoryModules.INSIDE_NEST.get()).isEmpty());
    }

    public void setClimbing(boolean climbing) {
        byte b0 = (Byte) this.entityData.get(DATA_FLAGS_ID);
        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.entityData.set(DATA_FLAGS_ID, b0);
    }


    @Override
    protected Brain.Provider<TermiteEntity> brainProvider() {
        return Brain.provider(TermiteAi.MEMORY_MODULES, TermiteAi.SENSORS);
    }

    @Override
    public Brain<TermiteEntity> getBrain() {
        return (Brain<TermiteEntity>) super.getBrain();
    }


    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte) 0);
    }

    public void updateAnimations() {
        if (this.getDeltaMovement().horizontalDistance() < 0.01F) {
            idleAnimationState.startIfStopped(tickCount);
        }
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.ARMOR, 2f)
                .add(Attributes.ATTACK_DAMAGE, 5);

    }

    @Override
    public void tick() {
        super.tick();
        this.updateAnimations();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return true;
    }

    @Override
    public boolean canAttack(LivingEntity livingentity, TargetingConditions condition) {
        return true;
    }

    /*
                    look for the nest when summoned, spawn-egged, hatched, natural spawned etc etc
                 */
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        BlockPos poss = this.blockPosition();
        Farmerooni.LOGGER.info("new termite finding nest");
        for (BlockPos pos : BlockPos.betweenClosed(poss.offset(-15, -2, -15), poss.offset(15, 2, 15))) {
            if (level.getBlockState(pos).is(FarmerooniBlocks.TERMITE_NEST.get())) {
                if (!((TermiteNestBlockEntity) level.getBlockEntity(pos)).addTermiteResident(this)) {
                    this.getBrain().setMemory(FarmerooniMemoryModules.NEST_DATA.get(), new NestData(
                            ((TermiteNestBlockEntity) level.getBlockEntity(pos)).colony,
                            pos
                    ));
                    if (level instanceof ServerLevel slevel)
                        slevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

                    Farmerooni.LOGGER.info("new termite linked to existing nest");
                    break;
                }
            }
        }

        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public void remove(RemovalReason reason) {
        //todo dimension checking

        if (
                this.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).isPresent() &&
                        this.level().getBlockEntity(
                                this.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest()
                        ) != null
        ) {
            ((TermiteNestBlockEntity) this.level().getBlockEntity(
                    this.getBrain().getMemory(FarmerooniMemoryModules.NEST_DATA.get()).get().nest()
            )).removeTermiteResident(this);

            if (this.level() instanceof ServerLevel slevel)
                slevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

            Farmerooni.LOGGER.info("killed termite unlinked to nest");
        }

        super.remove(reason);
    }


    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

    @Override
    public SimpleContainer getInventory() {
        return inventory;
    }
}
