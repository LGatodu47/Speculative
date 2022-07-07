package io.github.lgatodu47.speculative.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class SpeculativeOre extends OreBlock {
    protected UniformInt xpRange;

    public SpeculativeOre(float hardness, float resistance) {
        super(Properties.of(Material.STONE).strength(hardness, resistance).sound(SoundType.STONE).requiresCorrectToolForDrops());
    }

    public SpeculativeOre xp(int min, int max) {
        xpRange = UniformInt.of(min, max);
        return this;
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.xpRange.sample(RANDOM) : 0;
    }
}
