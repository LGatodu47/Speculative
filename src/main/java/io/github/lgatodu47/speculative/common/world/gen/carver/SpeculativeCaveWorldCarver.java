package io.github.lgatodu47.speculative.common.world.gen.carver;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class SpeculativeCaveWorldCarver extends CaveWorldCarver {
    public SpeculativeCaveWorldCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
        super(codec, maxHeight);
        setRegistryName("cave_world_carver");
    }

    @Override
    protected boolean canReplaceBlock(BlockState state) {
        return state.is(SpeculativeBlocks.Tags.CARVABLE_BLOCKS);
    }
}
