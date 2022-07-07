package io.github.lgatodu47.speculative.common.world.gen.carver;

import io.github.lgatodu47.speculative.util.MixinAccessibleObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;

import javax.annotation.Nullable;

public interface AccessibleWorldCarver extends MixinAccessibleObject {
    @Nullable
    default BlockState accessed_getCarveState(CarvingContext ctx, CarverConfiguration config, BlockPos pos, Aquifer aquifer) {
        return null;
    }
}
