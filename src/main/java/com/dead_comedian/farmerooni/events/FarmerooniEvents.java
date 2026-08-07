package com.dead_comedian.farmerooni.events;

import com.dead_comedian.farmerooni.codecs.WoodData;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.Unicorn;
import com.dead_comedian.farmerooni.helper.IsBlockWoodHelper;
import com.dead_comedian.farmerooni.registries.FarmerooniBlocks;
import com.dead_comedian.farmerooni.registries.FarmerooniCodecs;
import com.dead_comedian.farmerooni.registries.FarmerooniEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber
public class FarmerooniEvents {

    @SubscribeEvent
    private static void onAddBlocks(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, FarmerooniBlocks.PUTRID_SIGN.get(), FarmerooniBlocks.PUTRID_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, FarmerooniBlocks.PUTRID_HANGING_SIGN.get(), FarmerooniBlocks.PUTRID_WALL_HANGING_SIGN.get());
    }

    @SubscribeEvent
    public static void datapackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(FarmerooniCodecs.PREFIX_WOOD, WoodData.WoodTypeListCodec.CODEC, WoodData.WoodTypeListCodec.CODEC);

    }


    @SubscribeEvent
    public static void aaa(PlayerInteractEvent.RightClickBlock event) {
        Block pumpkin = event.getLevel().getBlockState(event.getPos()).getBlock();
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            System.out.println(IsBlockWoodHelper.isWood(pumpkin, serverLevel));
        }
    }


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(FarmerooniEntities.TERMITE.get(), TermiteEntity.createAttributes().build());
        event.put(FarmerooniEntities.UNICORN.get(), Unicorn.createMobAttributes().build());
    }

}
