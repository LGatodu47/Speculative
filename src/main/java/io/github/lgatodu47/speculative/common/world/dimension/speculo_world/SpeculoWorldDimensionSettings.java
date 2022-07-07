package io.github.lgatodu47.speculative.common.world.dimension.speculo_world;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import net.minecraft.world.level.block.state.BlockState;

public class SpeculoWorldDimensionSettings {
    public int getBedrockFloorPosition() {
        return 0;
    }

    public int getBedrockRoofPosition() {
        return -10;
    }

    public int getSeaLevel() {
        return 63;
    }

    public BlockState getDefaultBlock() {
        return SpeculativeBlocks.SPECULO_STONE.get().defaultBlockState();
    }

    public BlockState getDefaultFluidBlock() {
        return SpeculativeFluids.SULFURIC_WATER.getBlock().get().defaultBlockState();
    }

    /*public NoiseGeneratorSettings createSettings() {
        return new NoiseGeneratorSettings(new StructureSettings(Optional.empty(), Collections.emptyMap()),
                new NoiseSettings(256,
                        new NoiseSamplingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D),
                        new NoiseSlideSettings(-10, 3, 0),
                        new NoiseSlideSettings(-30, 0, 0),
                        1, 2, 1.0D, -0.46875D, true, true, false, false),
                getDefaultBlock(), getDefaultFluidBlock(), getBedrockRoofPosition(), getBedrockFloorPosition(), getSeaLevel(), false);
    }*/
}
