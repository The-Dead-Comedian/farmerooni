package com.dead_comedian.farmerooni.blocks.entities;

import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.menu.NestMenu;
import com.dead_comedian.farmerooni.registries.FarmerooniAttachments;
import com.dead_comedian.farmerooni.registries.FarmerooniBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class TermiteNestBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider {

    private NonNullList<ItemStack> items;
    private static int MAX_TERMITES = 8;
    private final List<UUID> residents = new ArrayList<>();


    public TermiteNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(FarmerooniBlockEntities.TERMITE_NEST_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
    }

    @Override
    public void syncData(Supplier<? extends AttachmentType<?>> type) {
        super.syncData(type);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {

            this.setData(FarmerooniAttachments.RESIDENT_COUNT, residents.size());


        if (world.getRandom().nextInt(5) == 0) {
            BlockPos blockpos1 = pos.offset(world.getRandom().nextInt(11) - 5,
                    world.getRandom().nextInt(8) - 2,
                    world.getRandom().nextInt(11) - 5);

            for (int k = 0; k < 5; ++k) {
                if (level.isEmptyBlock(blockpos1) && Blocks.BROWN_MUSHROOM.defaultBlockState().canSurvive(level, blockpos1)) {
                    pos = blockpos1;
                }

                blockpos1 = pos.offset(world.getRandom().nextInt(11) - 5,
                        world.getRandom().nextInt(8) - 5,
                        world.getRandom().nextInt(11) - 5);
            }

            if (level.isEmptyBlock(blockpos1) && Blocks.BROWN_MUSHROOM.defaultBlockState().canSurvive(level, blockpos1)) {
                level.setBlock(blockpos1, Blocks.BROWN_MUSHROOM.defaultBlockState(), 2);
            }
        }


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

    public boolean addTermiteResident(TermiteEntity entity) {
        if (this.residents.size() == MAX_TERMITES) {
            return true;
        }
        this.residents.add(entity.getUUID());
        return false;

    }

    public boolean removeTermiteResident(TermiteEntity entity) {
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
