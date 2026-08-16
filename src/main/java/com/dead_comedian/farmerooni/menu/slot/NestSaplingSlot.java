//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.dead_comedian.farmerooni.menu.slot;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class NestSaplingSlot extends Slot {

    public NestSaplingSlot(Container furnaceContainer, int slot, int xPosition, int yPosition) {
        super(furnaceContainer, slot, xPosition, yPosition);
    }

    public boolean mayPlace(ItemStack stack) {
        return stack.is(ItemTags.SAPLINGS);
    }

    public int getMaxStackSize(ItemStack stack) {
        return super.getMaxStackSize(stack);
    }
}
