package io.github.lgatodu47.speculative.common.block.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;

public interface ISpeculativeTickingBlockEntity {
    @Nullable
    static <T extends BlockEntity> BlockEntityTicker<T> getTicker(BlockEntityType<T> toCheck, BlockEntityType<?> type) {
        return toCheck.equals(type) ? (level, pos, state, blockEntity) -> {
            if(blockEntity instanceof ISpeculativeTickingBlockEntity ticking) {
                ticking.tick(level);
            }
        } : null;
    }

    void tick(Level level);
}
