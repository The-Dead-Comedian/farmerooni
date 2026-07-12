package com.dead_comedian.farmerooni.blocks;

import com.dead_comedian.farmerooni.blocks.entities.TermiteNestBlockEntity;
import com.dead_comedian.farmerooni.registries.FarmerooniBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TermiteNestBlock extends BaseEntityBlock {

    // TO-DO: Once the Termite Nest has a slot system
    // ABANDONED state represents whether player can or can not hatch termite eggs nearby
    // (eggs can only hatch near abandoned nests)
    // If a not abandoned nest remains unpopulated (no more ants occupy its slot)
    // it is marked as abandoned
    // Abandoned nests can not become un abandoned
    // Mushroom spawning will also depend on being abandoned, but thats once the toggle works


    public static BooleanProperty ABANDONED = BooleanProperty.create("abandoned");

    public TermiteNestBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ABANDONED);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            player.openMenu((TermiteNestBlockEntity) entity, entity.getBlockPos());
        }


        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }


    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (level.getRandom().nextInt(30) == 0) {
            BlockPos blockpos1 = pos.offset(level.getRandom().nextInt(11) - 5,
                    level.getRandom().nextInt(8) - 2,
                    level.getRandom().nextInt(11) - 5);

            for (int k = 0; k < 5; ++k) {
                if (level.isEmptyBlock(blockpos1) && Blocks.BROWN_MUSHROOM.defaultBlockState().canSurvive(level, blockpos1)) {
                    pos = blockpos1;
                }

                blockpos1 = pos.offset(level.getRandom().nextInt(11) - 5,
                        level.getRandom().nextInt(8) - 5,
                        level.getRandom().nextInt(11) - 5);
            }

            if (level.isEmptyBlock(blockpos1) && Blocks.BROWN_MUSHROOM.defaultBlockState().canSurvive(level, blockpos1)) {
                level.setBlock(blockpos1, Blocks.BROWN_MUSHROOM.defaultBlockState(), 2);
            }
        }

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, FarmerooniBlockEntities.TERMITE_NEST_BLOCK_ENTITY.get(), (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TermiteNestBlockEntity(blockPos, blockState);
    }
}
