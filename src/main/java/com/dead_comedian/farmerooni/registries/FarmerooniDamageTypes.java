package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class FarmerooniDamageTypes {
    public static final ResourceKey<DamageType> POKE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Farmerooni.MOD_ID, "poke"));
}
