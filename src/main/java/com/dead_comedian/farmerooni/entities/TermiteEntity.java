package com.dead_comedian.farmerooni.entities;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.ai.TermiteAi;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.NestData;
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
import net.minecraft.world.InteractionHand;
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
    ;
    private static final Vec3i ITEM_PICKUP_REACH = new Vec3i(1, 1, 1);
    private final SimpleContainer inventory = new SimpleContainer(1);

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


//        private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(RevenantEntity.class, EntityDataSerializers.INT);

    /// ///////////////////////////////

    public final AnimationState idleAnimationState = new AnimationState();

//
//        public RevenantStates getState() {
//            int stateId = this.entityData.get(STATE);
//
//            return RevenantStates.BY_ID.apply(stateId);
//        }
//
//        public void setState(RevenantStates state) {
//            this.entityData.set(STATE, state.getId());
//        }


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
        ItemStack itemstack = this.getItemInHand(InteractionHand.MAIN_HAND);
        return !itemstack.isEmpty()
                && net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.level(), this);
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        InventoryCarrier.pickUpItem(this, this, itemEntity);
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
        return ((Byte) this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
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
