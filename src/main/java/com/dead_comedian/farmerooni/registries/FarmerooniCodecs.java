package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.codecs.WoodData;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class FarmerooniCodecs {

    public static final ResourceKey<Registry<WoodData.WoodTypeListCodec>> PREFIX_WOOD = key("prefix_wood");

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, name));
    }

}
