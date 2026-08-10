package com.dead_comedian.farmerooni.helper;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.codecs.WoodData;
import com.dead_comedian.farmerooni.registries.FarmerooniCodecs;
import com.dead_comedian.farmerooni.registries.FarmerooniTags;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class TermiteHelper {

    public static boolean isWood(Block block, ServerLevel level) {
        Registry<WoodData.WoodTypeListCodec> registry = level.registryAccess().registryOrThrow(FarmerooniCodecs.PREFIX_WOOD);

        Optional<WoodData.WoodTypeListCodec> data =
            registry.getOptional(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "prefix_wood"));

        if (data.isPresent()) {
            List<WoodData.WoodTypeCodec> list = data.get().types();
            for (WoodData.WoodTypeCodec woodTypeCodec : list) {
                String name = woodTypeCodec.name();
                return block.getName().toString().contains(name) ||
                    block.getName().toString().contains("leaves") ||
                    block.getName().toString().contains("sapling");
            }
        }
        return block.defaultBlockState().is(FarmerooniTags.Blocks.EXTRA_WOOD);
    }

    public static boolean isWood(ItemStack stacc, Level level){
        if(level.isClientSide()) return false;

        boolean verdict = false;
        if ((stacc.getItem() instanceof BlockItem bockinum)) {
            Block block = bockinum.getBlock();
            verdict = TermiteHelper.isWood(block, (ServerLevel) level);
        }
        return verdict;
    }


    //not finna lie grok-sonnet gpt  wrote tis shit
    public static void transferAll(Container source, Container destination) {
        for (int sourceslot = 0; sourceslot < source.getContainerSize(); sourceslot++) {
            ItemStack sourcestack = source.getItem(sourceslot);

            if (sourcestack.isEmpty()) {
                continue;
            }

            // existig slots
            for (int destslot = 0; destslot < destination.getContainerSize(); destslot++) {
                ItemStack deststack = destination.getItem(destslot);

                if (deststack.isEmpty()) {
                    continue;
                }

                if (!ItemStack.isSameItemSameComponents(sourcestack, deststack)) {
                    continue;
                }

                int max = Math.min(
                    destination.getMaxStackSize(deststack),
                    deststack.getMaxStackSize()
                );

                int space = max - deststack.getCount();

                if (space <= 0) {
                    continue;
                }

                int moved = Math.min(space, sourcestack.getCount());

                deststack.grow(moved);
                sourcestack.shrink(moved);

                destination.setChanged();

                if (sourcestack.isEmpty()) {
                    break;
                }
            }

            if (sourcestack.isEmpty()) {
                source.setItem(sourceslot, ItemStack.EMPTY);
                continue;
            }

            // empty ones
            for (int destslot = 0; destslot < destination.getContainerSize(); destslot++) {
                if (!destination.getItem(destslot).isEmpty()) {
                    continue;
                }

                int moved = Math.min(
                    sourcestack.getCount(),
                    destination.getMaxStackSize(sourcestack)
                );

                ItemStack movedstack = sourcestack.copy();
                movedstack.setCount(moved);

                destination.setItem(destslot, movedstack);
                sourcestack.shrink(moved);

                if (sourcestack.isEmpty()) {
                    break;
                }
            }

            // void the fucker
            source.setItem(sourceslot, ItemStack.EMPTY);
        }

        source.setChanged();
        destination.setChanged();
    }

}
