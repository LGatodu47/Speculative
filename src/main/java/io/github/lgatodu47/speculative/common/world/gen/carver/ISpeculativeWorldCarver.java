package io.github.lgatodu47.speculative.common.world.gen.carver;

import io.github.lgatodu47.speculative.util.SpeculativeReflectionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.material.Fluids;

public interface ISpeculativeWorldCarver {
    static BlockState getCarveState(CarvingContext ctx, CarverConfiguration config, BlockPos pos, Aquifer aquifer) {
        if (pos.getY() <= config.lavaLevel.resolveY(ctx)) {
            return Fluids.LAVA.defaultFluidState().createLegacyBlock();
        }
        return config.debugSettings.isDebugMode() ? getDebugState(config, Blocks.AIR.defaultBlockState()) : Blocks.AIR.defaultBlockState();
    }

    private static BlockState getDebugState(CarverConfiguration config, BlockState state) {
        return SpeculativeReflectionHelper.getStaticMethod(WorldCarver.class, "m_159381_", CarverConfiguration.class, BlockState.class).invoke(BlockState.class, config, state).orElse(state);
    }
}
