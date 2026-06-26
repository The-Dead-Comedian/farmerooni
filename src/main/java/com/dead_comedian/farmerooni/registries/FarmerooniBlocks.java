package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Farmerooni.MOD_ID);

    public static final DeferredBlock<RotatedPillarBlock> PUTRID_LOG = register("putrid_log",
            () -> log(MapColor.COLOR_GRAY, MapColor.COLOR_ORANGE));

    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_PUTRID_LOG = register("stripped_putrid_log",
            () -> log(MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE));

    public static final DeferredBlock<RotatedPillarBlock> PUTRID_WOOD = register("putrid_wood",
            () -> log(MapColor.COLOR_GRAY, MapColor.COLOR_ORANGE));

    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_PUTRID_WOOD = register("stripped_putrid_wood",
            () -> log(MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE));


    public static final DeferredBlock<Block> PUTRID_CROWN = register("putrid_crown",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                    .noOcclusion()
                    .noCollission()));

    public static final DeferredBlock<Block> PUTRID_PLANKS = register("putrid_planks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<SlabBlock> PUTRID_SLAB = register("putrid_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));

    public static final DeferredBlock<StairBlock> PUTRID_STAIRS = register("putrid_stairs",
            () -> new StairBlock(PUTRID_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));

    public static final DeferredBlock<FenceBlock> PUTRID_FENCE = register("putrid_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));

    public static final DeferredBlock<FenceGateBlock> PUTRID_FENCE_GATE = register("putrid_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));

    public static final DeferredBlock<ButtonBlock> PUTRID_BUTTON = register("putrid_button",
            () -> new ButtonBlock(FarmerooniBlockSetTypes.PUTRID, 25, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));

    public static final Supplier<Block> PUTRID_PRESSURE_PLATE = register(
            "putrid_pressure_plate",
            () -> new PressurePlateBlock(FarmerooniBlockSetTypes.PUTRID, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));

    public static final DeferredBlock<Block> TERMITE_NEST = register("termite_nest",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(FarmerooniBlocks.PUTRID_PLANKS.get())));

    public static final DeferredBlock<GlowLichenBlock> TERMITE_EGGS = register("termite_eggs",
            () -> new GlowLichenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FROGSPAWN)));


    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerItem(String name, Supplier<T> block) {
        FarmerooniItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static RotatedPillarBlock log(MapColor topMapColor, MapColor sideMapColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }


    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
