package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Farmerooni.MOD_ID);

    public static final Supplier<BlockEntityType<TermiteNestBlockEntity>> TERMITE_NEST_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("termite_nest_block_entity", () -> BlockEntityType.Builder.of(
                    TermiteNestBlockEntity::new, FarmerooniBlocks.TERMITE_NEST.get()).build(null));

    public static void init(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}