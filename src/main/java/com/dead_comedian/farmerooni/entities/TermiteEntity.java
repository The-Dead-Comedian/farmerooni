package com.dead_comedian.farmerooni.entities;

import com.dead_comedian.farmerooni.entities.ai.TermiteAi;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
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

    public void updateAnimations(){
        if(this.getDeltaMovement().horizontalDistance()<0.01F){
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

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }

}
