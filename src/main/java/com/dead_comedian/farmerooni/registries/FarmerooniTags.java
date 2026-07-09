package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FarmerooniTags {
    public static class Items{
        public static final TagKey<Item>  PUTRID_LOGS = create("putrid_logs");
        public static final TagKey<Item>  PUTRID_WOOD = create("putrid_wood");

        private static TagKey<Item> create(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, name));
        }
    }
    public static class Blocks{
        public static final TagKey<Block> PUTRID_LOGS = create("putrid_logs");

        private static TagKey<Block> create(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, name));
        }
    }
}
