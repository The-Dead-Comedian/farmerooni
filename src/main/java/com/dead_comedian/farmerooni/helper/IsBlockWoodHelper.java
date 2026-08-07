package com.dead_comedian.farmerooni.helper;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.codecs.WoodData;
import com.dead_comedian.farmerooni.registries.FarmerooniCodecs;
import com.dead_comedian.farmerooni.registries.FarmerooniTags;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Optional;

public class IsBlockWoodHelper {

    public static boolean isWood(Block block, ServerLevel level) {
        Registry<WoodData.WoodTypeListCodec> registry = level.registryAccess().registryOrThrow(FarmerooniCodecs.PREFIX_WOOD);

        Optional<WoodData.WoodTypeListCodec> data =
                registry.getOptional(ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "prefix_wood"));

        if (data.isPresent()) {
            List<WoodData.WoodTypeCodec> list = data.get().types();
            for (WoodData.WoodTypeCodec woodTypeCodec : list) {
                String name = woodTypeCodec.name();
                return block.getName().toString().contains(name) &&
                        block.getName().toString().contains("leaves") &&
                        block.getName().toString().contains("sapling");
            }
        }
        return block.defaultBlockState().is(FarmerooniTags.Blocks.EXTRA_WOOD);
    }
}
