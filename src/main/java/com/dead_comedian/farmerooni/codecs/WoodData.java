package com.dead_comedian.farmerooni.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;


public class WoodData {
    public record WoodTypeCodec(String name) {
        public static final Codec<WoodTypeCodec> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("type").forGetter(WoodTypeCodec::name)
                ).apply(instance, WoodTypeCodec::new)
        );
    }

    public record WoodTypeListCodec(List<WoodTypeCodec> types) {
        public static final Codec<WoodTypeListCodec> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        WoodTypeCodec.CODEC.listOf().fieldOf("types").forGetter(WoodTypeListCodec::types)
                ).apply(instance, WoodTypeListCodec::new)
        );
    }
}


