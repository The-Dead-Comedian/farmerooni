package com.dead_comedian.farmerooni.registries;


import com.dead_comedian.farmerooni.Farmerooni;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class FarmerooniMemoryModules {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, Farmerooni.MOD_ID);

    public static final Supplier<MemoryModuleType<BlockPos>> NEST = register("nest_pos", BlockPos.CODEC);

    private static <T> Supplier<MemoryModuleType<T>> register(String name, Codec<T> codec) {
        return register(name, Optional.of(codec));
    }

    private static <T> Supplier<MemoryModuleType<T>> register(String name, Optional<Codec<T>> codec) {
        return MEMORY_MODULE_TYPES.register(name, () -> new MemoryModuleType<>(codec));
    }

    public static void init(IEventBus modEventBus) {
        MEMORY_MODULE_TYPES.register(modEventBus);
    }
}