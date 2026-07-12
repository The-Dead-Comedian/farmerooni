package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class FarmerooniAttachments {

        private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
                DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Farmerooni.MOD_ID);


        public static final Supplier<AttachmentType<Integer>> RESIDENT_COUNT = ATTACHMENT_TYPES.register(
                "resident_count",
                () -> AttachmentType.builder(() -> 8)
                        .serialize(Codec.INT)
                        .sync(ByteBufCodecs.INT)
                        .build()
        );

        public static void init(IEventBus modBus) {
            ATTACHMENT_TYPES.register(modBus);
        }
    }

