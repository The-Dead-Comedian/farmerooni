package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Farmerooni.MOD_ID);

    public static final Supplier<CreativeModeTab> FARMEROONI_TAB = CREATIVE_MODE_TAB.register("farmerooni_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FarmerooniBlocks.STRIPPED_PUTRID_LOG.get()))
                    .title(Component.translatable("creativetab.farmerooni_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(FarmerooniBlocks.PUTRID_LOG);
                        pOutput.accept(FarmerooniBlocks.PUTRID_WOOD);
                        pOutput.accept(FarmerooniBlocks.STRIPPED_PUTRID_LOG);
                        pOutput.accept(FarmerooniBlocks.STRIPPED_PUTRID_WOOD);
                        pOutput.accept(FarmerooniBlocks.PUTRID_CROWN);
                        pOutput.accept(FarmerooniBlocks.PUTRID_PLANKS);
                        pOutput.accept(FarmerooniBlocks.PUTRID_SLAB);
                        pOutput.accept(FarmerooniBlocks.PUTRID_STAIRS);
                        pOutput.accept(FarmerooniBlocks.PUTRID_BUTTON);
                        pOutput.accept(FarmerooniBlocks.PUTRID_FENCE);
                        pOutput.accept(FarmerooniBlocks.PUTRID_FENCE_GATE);
                        pOutput.accept(FarmerooniBlocks.PUTRID_PRESSURE_PLATE.get());
                        pOutput.accept(FarmerooniBlocks.PUTRID_DOOR.get());
                        pOutput.accept(FarmerooniBlocks.PUTRID_TRAPDOOR.get());
                        pOutput.accept(FarmerooniItems.PUTRID_SIGN.get());
                        pOutput.accept(FarmerooniItems.PUTRID_HANGING_SIGN.get());
                        pOutput.accept(FarmerooniItems.PUTRID_BOAT.get());
                        pOutput.accept(FarmerooniItems.PUTRID_CHEST_BOAT.get());
                        pOutput.accept(FarmerooniBlocks.TERMITE_NEST);
                        pOutput.accept(FarmerooniBlocks.TERMITE_EGGS);
                    })
                    .build());


    public static void init(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}