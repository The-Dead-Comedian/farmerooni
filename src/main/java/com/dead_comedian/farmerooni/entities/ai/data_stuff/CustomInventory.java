package com.dead_comedian.farmerooni.entities.ai.data_stuff;

import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CustomInventory extends SimpleContainer implements Container {

    private static final int size = 32;

    private final NonNullList<ItemStack> items =
        NonNullList.withSize(size, ItemStack.EMPTY);


    @Override
    public boolean canAddItem(ItemStack stack) {
        boolean flag = false;

        for (ItemStack itemstack : this.items) {
            if (itemstack.isEmpty() || stack.getCount() == 1) {
                flag = true;
                break;
            }
        }

        return flag;
    }


    @Override
    public int getContainerSize() {
        //Farmerooni.LOGGER.info("getContainerSize");
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                Farmerooni.LOGGER.info("not empty? custom inventory");
                return false;
            }
        }
        //Farmerooni.LOGGER.info("empty custom inventory");

        return true;
    }

    public boolean isFull() {
        for (ItemStack stack : items) {
            if (stack.isEmpty()) {
                //Farmerooni.LOGGER.info("not full? custom inventory");
                return false;
            }
        }
        Farmerooni.LOGGER.info("full custom inventory");

        return true;
    }


    @Override
    public ItemStack getItem(int slot) {
        return slot >= 0 && slot < size ? items.get(slot) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot < 0 || slot >= size) {
            Farmerooni.LOGGER.info("removeitem fail no slot custom inventory");
            return ItemStack.EMPTY;
        }

        ItemStack stack = items.get(slot);
        if (stack.isEmpty()) {
            Farmerooni.LOGGER.info("removeitem fail no item {} custom inventory", slot);
            return ItemStack.EMPTY;
        }

        ItemStack result = stack.split(amount);

        if (stack.isEmpty())
            items.set(slot, ItemStack.EMPTY);

        setChanged();
        Farmerooni.LOGGER.info("removeitem {}:{} custom inventory", slot, stack);
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot < 0 || slot >= size) {
            Farmerooni.LOGGER.info("silent removeitem fail no slot custom inventory");
            return ItemStack.EMPTY;
        }

        ItemStack stack = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        Farmerooni.LOGGER.info("silent removeitem {}:{} custom inventory", slot, stack);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot < 0 || slot >= size) {
            Farmerooni.LOGGER.info("setitem fail no slot custom inventory");
            return;
        }

        if (!stack.isEmpty()) {
            stack = stack.copy();
            stack.setCount(1); // every slot only stores one item
        }

        items.set(slot, stack);
        setChanged();
        Farmerooni.LOGGER.info("setitem {}:{} custom inventory", slot, stack);

    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < size; i++) {
            items.set(i, ItemStack.EMPTY);
        }
        Farmerooni.LOGGER.info("clearcontent custom inventory");
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        Farmerooni.LOGGER.info("canplaceitem {}:{} custom inventory", slot, stack);
        return stack.getCount() == 1;
    }

    public boolean add(ItemStack stack) {
        if (stack.isEmpty()) {
            Farmerooni.LOGGER.info("add fail empty stack custom inventory");
            return false;
        }

        boolean fug = false;
        ItemStack copy = stack.copy();
        for (int j = 0; j < copy.getCount(); j++) {
            for (int i = 0; i < size; i++) {
                if (items.get(i).isEmpty()) {
                    ItemStack copy2 = stack.copy();
                    copy2.setCount(1);
                    items.set(i, copy2);
                    fug = true;
                    Farmerooni.LOGGER.info("add {}:{} custom inventory", copy, i);
                }
            }
        }

        if (fug) {
            setChanged();
            return true;
        }

        Farmerooni.LOGGER.info("add fail empty stack custom inventory");
        return false;
    }
}