package com.dead_comedian.farmerooni.entities.ai.sensor_type;

import com.dead_comedian.farmerooni.Farmerooni;
import com.dead_comedian.farmerooni.entities.Termite;
import com.dead_comedian.farmerooni.entities.ai.data_stuff.NestData;
import com.dead_comedian.farmerooni.registries.FarmerooniMemoryModules;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class NearestCropSensor extends Sensor<Rabbit> {

    public void doTick(ServerLevel level, Rabbit entity) {


        // pls no override if it already exists, UNLESS its been removed

        Optional<BlockPos> cropPos = entity.getBrain().getMemory(FarmerooniMemoryModules.NEAREST_CROP.get());

        if (cropPos.isPresent()) {
            if (level.getBlockState(cropPos.get()).getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(level.getBlockState(cropPos.get()))) {
                return;
            } else {
                entity.getBrain().eraseMemory(FarmerooniMemoryModules.NEAREST_CROP.get());
            }
        }

        List<BlockPos> list = Lists.newArrayList();
        for (int j = -20; j <= 20; ++j) {
            for (int k = 10; k >= -10; --k) {
                for (int l = -20; l <= 20; ++l) {
                    BlockPos blockPos = entity.blockPosition().offset(j, k, l);
                    if (level.getBlockState(blockPos).getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(level.getBlockState(blockPos))) {
                        list.add(blockPos);
                    }
                }
            }
        }

        // first y then dist sq
        list.sort(
                Comparator
                        .comparingInt(
                                (BlockPos pos) -> pos.getY())
                        .thenComparingDouble(
                                (BlockPos pos) -> Vec3.atCenterOf(pos).distanceToSqr(entity.position())));

        if (list.isEmpty()) {
            entity.getBrain().eraseMemory(FarmerooniMemoryModules.NEAREST_CROP.get());
            return;
        }

        Farmerooni.LOGGER.info("Nearest crop found {}", list.get(0));
        entity.getBrain().setMemory(FarmerooniMemoryModules.NEAREST_CROP.get(), list.get(0));
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of(
        );
    }
}
