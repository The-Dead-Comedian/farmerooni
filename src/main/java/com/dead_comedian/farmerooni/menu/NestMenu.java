package com.dead_comedian.farmerooni.menu;

import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class NestMenu extends AbstractContainerMenu {

    private final Container container;

    public final TermiteNestBlockEntity blockEntity;

    public static final int MAIN_ROWS = 2;
    public static final int MAIN_COLUMNS = 9;
    public final int residents;

    private static final int TE_INVENTORY_SLOT_COUNT = 18;
    private static final int VANILLA_SLOT_COUNT = 36;


    public NestMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
        this(containerId, inv, (TermiteNestBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()),0);
    }

    public NestMenu(int id, Inventory inv,TermiteNestBlockEntity be, int residussy) {
        super(FarmerooniMenus.NEST_MENU.get(), id);

        this.residents=residussy;
        this.container = be;
        this.blockEntity = be;

        checkContainerSize(container, 18);
        container.startOpen(inv.player);

        addMainStorageSlots(container);


        addPlayerInventory(inv);
        addPlayerHotbar(inv);

    }


    @Override
    public void removed(Player player) {
        super.removed(player);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    private void addMainStorageSlots(Container container) {
        for (int row = 0; row < MAIN_ROWS; ++row) {
            for (int col = 0; col < MAIN_COLUMNS; ++col) {
                addSlot(new Slot(
                        container,
                        col + row * MAIN_COLUMNS,
                        8 + col * 18,
                        row * 18
                ));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();

            if (index < TE_INVENTORY_SLOT_COUNT) {
                if (!moveItemStackTo(stack,
                        TE_INVENTORY_SLOT_COUNT,
                        TE_INVENTORY_SLOT_COUNT + VANILLA_SLOT_COUNT,
                        false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack,
                        0,
                        TE_INVENTORY_SLOT_COUNT,
                        false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();

            slot.onTake(player, stack);
        }

        return result;
    }


    private void addPlayerInventory(Inventory inv) {
        int offset = 2;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                addSlot(new Slot(
                        inv,
                        col + row * 9 + 9,
                        8 + col * 18,
                        84 + row * 18 + offset
                ));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(inv, i, 8 + i * 18, 144));
        }
    }
}
