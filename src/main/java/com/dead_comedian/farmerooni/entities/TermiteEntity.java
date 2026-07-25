package com.dead_comedian.farmerooni.entities;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.entities.ai.TermiteAi;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class TermiteEntity extends Animal {
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
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

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
        this.level().getProfiler().push("termiteActivityUpdate");
        TermiteAi.updateActivity(this);
        this.level().getProfiler().pop();

        super.customServerAiStep();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return TermiteAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    protected Brain.Provider<TermiteEntity> brainProvider() {
        return Brain.provider(TermiteAi.MEMORY_MODULES, TermiteAi.SENSORS);
    }

    @Override
    public Brain<TermiteEntity> getBrain() {
        return (Brain<TermiteEntity>) super.getBrain();
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
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
                .add(Attributes.ARMOR, 2f);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateAnimations();
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
                if(!((TermiteNestBlockEntity) level.getBlockEntity(pos)).addTermiteResident(this)){
                    this.getBrain().setMemory(FarmerooniMemoryModules.NEST.get(), pos);
                    if(level instanceof ServerLevel slevel) slevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

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
                this.getBrain().getMemory(FarmerooniMemoryModules.NEST.get()).isPresent() &&
                this.level().getBlockEntity(
                     this.getBrain().getMemory(FarmerooniMemoryModules.NEST.get()).get()
                ) != null
        ) {
            ((TermiteNestBlockEntity) this.level().getBlockEntity(
                    this.getBrain().getMemory(FarmerooniMemoryModules.NEST.get()).get()
            )).removeTermiteResident(this);

            if(this.level() instanceof ServerLevel slevel) slevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

            Farmerooni.LOGGER.info("killed termite unlinked to nest");
        }

        super.remove(reason);
    }


    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

}
