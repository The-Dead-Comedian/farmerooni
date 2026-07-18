package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.Unicorn;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.horse.Horse;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniEntities {


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Farmerooni.MOD_ID);


    //    MOBS
    public static final Supplier<EntityType<TermiteEntity>> TERMITE =
            ENTITY_TYPES.register("termite", () -> EntityType.Builder.of(TermiteEntity::new, MobCategory.CREATURE)
                    .sized(0.4f, 0.2f).build("termite"));

    public static final Supplier<EntityType<Unicorn>> UNICORN =
            ENTITY_TYPES.register("unicorn", () -> EntityType.Builder.of(Unicorn::new, MobCategory.CREATURE)
                    .sized(1.5f, 1.5f).build("unicorn"));


    public static void init(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
