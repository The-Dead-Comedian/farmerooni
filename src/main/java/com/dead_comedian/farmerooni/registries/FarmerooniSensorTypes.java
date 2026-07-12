package com.dead_comedian.farmerooni.registries;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.ai.sensor_type.NestValidSensor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FarmerooniSensorTypes {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES =
            DeferredRegister.create(Registries.SENSOR_TYPE, Farmerooni.MOD_ID);

    public static final Supplier<SensorType<NestValidSensor>> NEST_VALID_SENSOR =
            SENSOR_TYPES.register("nest_valid_sensor",
                    () -> new SensorType<>(NestValidSensor::new));


    public static void init(IEventBus eventBus) {
        SENSOR_TYPES.register(eventBus);
    }


}