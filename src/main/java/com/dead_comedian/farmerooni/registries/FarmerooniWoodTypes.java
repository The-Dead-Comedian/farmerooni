package com.dead_comedian.farmerooni.registries;

import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class FarmerooniWoodTypes {
    public static final WoodType PUTRID = register("farmerooni:putrid", FarmerooniBlockSetTypes.PUTRID);

    private static WoodType register(String name, BlockSetType setType) {
        var type = new WoodType(name, setType);

        return WoodType.register(type);
    }
}