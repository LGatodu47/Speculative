package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.common.block.entity.ISpeculativeTickingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public interface ISpeculativeEntityBlock extends EntityBlock {
    BlockEntityType<?> getBlockEntityType();

    @Nullable
    @Override
    default <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return ISpeculativeTickingBlockEntity.getTicker(type, getBlockEntityType());
    }
}
