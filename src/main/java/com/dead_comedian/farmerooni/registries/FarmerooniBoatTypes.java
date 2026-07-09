package com.dead_comedian.farmerooni.registries;

import com.google.common.base.Suppliers;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class FarmerooniBoatTypes {
        public static final EnumProxy<Boat.Type> PUTRID_BOAT_PROXY = new EnumProxy<>(
                Boat.Type.class,
                FarmerooniBlocks.PUTRID_PLANKS,
                "farmerooni:putrid",
                FarmerooniItems.PUTRID_BOAT,
                FarmerooniItems.PUTRID_CHEST_BOAT,
                Suppliers.memoize(() -> Items.STICK),
                false
        );

}
