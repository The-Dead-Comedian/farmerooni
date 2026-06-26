package com.dead_comedian.farmerooni.registries;

import net.minecraft.world.level.block.state.properties.BlockSetType;

public class FarmerooniBlockSetTypes {
        public static final BlockSetType PUTRID = register("farmerooni:putrid");

        private static BlockSetType register(String name) {
            var type = new BlockSetType(name);

            return BlockSetType.register(type);
        }
    }
