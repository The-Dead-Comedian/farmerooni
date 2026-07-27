package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.ai.sensor_type.NearbyTermitesSensor;
import com.dead_comedian.farmerooni.entities.ai.sensor_type.NestValidSensor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniSchedules {

    public static final DeferredRegister<Schedule> SCHEDULES =
            DeferredRegister.create(Registries.SCHEDULE, Farmerooni.MOD_ID);

    public static final Supplier<Schedule> TERMITESCHDEULE =
            SCHEDULES.register(
                    "termite",
                    () -> new ScheduleBuilder(new Schedule())
                        .changeActivityAt(0, Activity.IDLE)
                        .changeActivityAt(1000, Activity.INVESTIGATE)
                        .changeActivityAt(12000, Activity.IDLE)
                        .build()
                    );


    public static void init(IEventBus eventBus) {
        SCHEDULES.register(eventBus);
    }

}