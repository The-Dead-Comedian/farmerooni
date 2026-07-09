package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Farmerooni.MOD_ID);

    public static final DeferredItem<Item> PUTRID_BOAT = register(
            "putrid_boat",
            () -> new BoatItem(
                    false,
                    FarmerooniBoatTypes.PUTRID_BOAT_PROXY.getValue(),
                    new Item.Properties().stacksTo(1)
            )
    );
    public static final DeferredItem<Item> PUTRID_CHEST_BOAT = register(
            "putrid_chest_boat",
            () -> new BoatItem(
                    true,
                    FarmerooniBoatTypes.PUTRID_BOAT_PROXY.getValue(),
                    new Item.Properties().stacksTo(1)
            )
    );

    public static final DeferredItem<Item> PUTRID_SIGN = register(
            "putrid_sign",
            () -> new SignItem(
                    new Item.Properties().stacksTo(16),
                    FarmerooniBlocks.PUTRID_SIGN.get(),
                    FarmerooniBlocks.PUTRID_WALL_SIGN.get())
    );
    public static final DeferredItem<Item> PUTRID_HANGING_SIGN = register(
            "putrid_hanging_sign",
            () -> new HangingSignItem(FarmerooniBlocks.PUTRID_HANGING_SIGN.get(),
                    FarmerooniBlocks.PUTRID_WALL_HANGING_SIGN.get(), new Item.Properties()
                    .stacksTo(16))
    );

    public static final DeferredItem<Item> TERMITE_SPAWN_EGG = register(
            "termite_spawn_egg",
            () -> new DeferredSpawnEggItem(FarmerooniEntities.TERMITE,
                  0xdecaa6,0xde864b, new Item.Properties())
    );

    private static <T extends Item> DeferredItem<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
