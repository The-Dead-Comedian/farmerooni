package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FarmerooniItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Farmerooni.MOD_ID);

    // Ingredients
//
//    public static final DeferredItem<Item> FLOUR = ITEMS.register("flour",
//            () -> new Item(new Item.Properties()));

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
