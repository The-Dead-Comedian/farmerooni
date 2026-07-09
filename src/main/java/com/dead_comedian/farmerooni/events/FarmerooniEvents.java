package com.dead_comedian.farmerooni.events;

import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

@EventBusSubscriber
public class FarmerooniEvents {

    @SubscribeEvent
    private static void onAddBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, FarmerooniBlocks.PUTRID_SIGN.get(), FarmerooniBlocks.PUTRID_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, FarmerooniBlocks.PUTRID_HANGING_SIGN.get(), FarmerooniBlocks.PUTRID_WALL_HANGING_SIGN.get());
    }
}
