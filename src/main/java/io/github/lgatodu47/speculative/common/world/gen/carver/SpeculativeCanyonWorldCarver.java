package io.github.lgatodu47.speculative.common.world.gen.carver;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.carver.CanyonWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class SpeculativeCanyonWorldCarver extends CanyonWorldCarver {
    public SpeculativeCanyonWorldCarver(Codec<ProbabilityConfig> codec) {
        super(codec);
        setRegistryName("canyon_world_carver");
    }

    @Override
    protected boolean isCarvable(BlockState state) {
        return state.isIn(SpeculativeBlocks.Tags.CARVABLE_BLOCKS);
    }
}
