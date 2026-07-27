package com.dead_comedian.farmerooni.entities.ai.sensor_type;

import com.dead_comedian.farmerooni.entities.TermiteEntity;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.NestData;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class NearbyTermitesSensor extends Sensor<TermiteEntity> {

    @Override
    protected void doTick(ServerLevel level, TermiteEntity thisone) {
        Brain<?> brain = thisone.getBrain();

        Optional<NestData> nestinfo =
                brain.getMemory(FarmerooniMemoryModules.NEST_DATA.get());

        if (nestinfo.isEmpty()) {
            //homeless nebby, it should prob not pick a fight lmao
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
            return;
        }

        NestData mine = nestinfo.get();

        List<TermiteEntity> theopps = level.getEntitiesOfClass(
                TermiteEntity.class,
                thisone.getBoundingBox().inflate(32),
                opp -> {
                    if (opp == thisone) return false;

                    Optional<NestData> oppinfo = opp
                            .getBrain()
                            .getMemory(FarmerooniMemoryModules.NEST_DATA.get());


                    return oppinfo.isEmpty() || (mine.colony() != null && !mine.colony().equals(oppinfo.get().colony()));
                }
        );

        if (theopps.isEmpty()) {
            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);

            return;
        }
        theopps.removeIf(e -> !e.isAlive() || e.isRemoved());

        if (!theopps.isEmpty()) brain.setMemory(MemoryModuleType.ATTACK_TARGET, theopps.getFirst());
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of(
        );
    }
}
