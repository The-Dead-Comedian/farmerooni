package com.dead_comedian.farmerooni.blocks.entities;

import com.dead_comedian.farmerooni.menu.NestMenu;
import com.dead_comedian.farmerooni.registries.FarmerooniBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TermiteNestBlockEntity extends RandomizableContainerBlockEntity implements MenuProvider {

    private NonNullList<ItemStack> items;

    public TermiteNestBlockEntity(BlockPos pos, BlockState blockState) {
        super(FarmerooniBlockEntities.TERMITE_NEST_BLOCK_ENTITY.get(), pos, blockState);
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
    }


    public void tick(Level world, BlockPos pos, BlockState state) {

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
        System.out.println("AHHHHMABDATUKAM");
        return new NestMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }
}
