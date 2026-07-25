package com.dead_comedian.farmerooni.blocks.entities;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.menu.NestMenu;
import com.dead_comedian.farmerooni.registries.FarmerooniBlockEntities;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TermiteNestBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider {

    private NonNullList<ItemStack> items;
    private static int MAX_TERMITES = 8;
    private final List<UUID> residents = new ArrayList<>();
    int counter = 0;


    public TermiteNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(FarmerooniBlockEntities.TERMITE_NEST_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
    }

    @Override
    public void syncData(Supplier<? extends AttachmentType<?>> type) {
        super.syncData(type);
    }


    //todo remove this if its not needed by the end
    //it probably isnt, the BE has a tick of its own and also handles most of termite logic
    public void tick(Level world, BlockPos pos, BlockState state) {

    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("gui.farmerooni.termite_nest");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new NestMenu(i, inventory, this, this.getResidents().size());
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    public void disbandTerritory(){
        if(this.level instanceof ServerLevel level) {
            this.residents.forEach(uuid -> {
                TermiteEntity revenantlmao = ((TermiteEntity) level.getEntity(uuid));
                revenantlmao.getBrain().eraseMemory(FarmerooniMemoryModules.NEST.get());
                level.sendParticles(ParticleTypes.ANGRY_VILLAGER, revenantlmao.getX(), revenantlmao.getY() + 1.0, revenantlmao.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

                Farmerooni.LOGGER.info("existing termite removed to broken nest");
            });

            this.residents.clear();
        }
    }

    public void createTerritory(){
        Farmerooni.LOGGER.info("new nest looking for existing termites");
        List<TermiteEntity> termites = level.getEntitiesOfClass(
            TermiteEntity.class,
            new AABB(this.getBlockPos()).inflate(15, 2, 15),
            termitty -> {
                return !termitty.getBrain().getMemory(FarmerooniMemoryModules.NEST.get()).isPresent();
            }
        );
        termites.stream()
            .limit(8)
            .forEach(termtity -> {
                if(!this.addTermiteResident(termtity)){
                    termtity.getBrain().setMemory(FarmerooniMemoryModules.NEST.get(), this.getBlockPos());
                    if(level instanceof ServerLevel slevel) slevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, termtity.getX(), termtity.getY() + 1.0, termtity.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

                    Farmerooni.LOGGER.info("existing termite added to new nest");
                }
            });
    }

    public boolean addTermiteResident(TermiteEntity entity) {
        if (this.residents.size() == MAX_TERMITES) {
            return true;
        }
        BlockPos bp = this.getBlockPos();
        if(level instanceof ServerLevel slevel) slevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, bp.getX(), bp.getY() + 1.0, bp.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

        this.residents.add(entity.getUUID());
        return false;

    }

    public boolean removeTermiteResident(TermiteEntity entity) {
        BlockPos bp = this.getBlockPos();
        if(level instanceof ServerLevel slevel) slevel.sendParticles(ParticleTypes.ANGRY_VILLAGER, bp.getX(), bp.getY() + 1.0, bp.getZ(), 1, 0.0, 0.0, 0.0, 0.0);

        this.residents.remove(entity.getUUID());
        return false;

    }

    public List<UUID> getResidents() {
        return residents;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        this.residents.clear();
        ListTag uuidList = tag.getList("Residents", Tag.TAG_INT_ARRAY);

        for (Tag tug : uuidList) {
            if (tug instanceof IntArrayTag) {
                residents.add(NbtUtils.loadUUID(tug));
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ListTag residentList = new ListTag();
        for (UUID uuid : residents) {
            residentList.add(NbtUtils.createUUID(uuid));
        }
        tag.put("Residents", residentList);

    }
}
