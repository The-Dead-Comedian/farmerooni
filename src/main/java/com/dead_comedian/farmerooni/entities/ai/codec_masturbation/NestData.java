package com.dead_comedian.farmerooni.entities.ai.codec_masturbation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;

import java.util.UUID;

public record NestData(UUID colony, BlockPos nest) {
    public static final Codec<NestData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            UUIDUtil.CODEC.fieldOf("colony").forGetter(NestData::colony),
            BlockPos.CODEC.fieldOf("nest").forGetter(NestData::nest)
        ).apply(instance, NestData::new)
    );
}