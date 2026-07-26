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

public class FarmerooniSensorTypes {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
            DeferredRegister.create(Registries.SENSOR_TYPE, Farmerooni.MOD_ID);

    public static final Supplier<SensorType<NestValidSensor>> NEST_VALID_SENSOR =
            SENSOR_TYPES.register("nest_valid_sensor",
                    () -> new SensorType<>(NestValidSensor::new));

    public static final Supplier<SensorType<NearbyTermitesSensor>> TILFS_NEAR_ME_SENSOR =
            SENSOR_TYPES.register("nearby_termites_sensor",
                    () -> new SensorType<>(NearbyTermitesSensor::new));



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
        SENSOR_TYPES.register(eventBus);
        SCHEDULES.register(eventBus);

    }


}