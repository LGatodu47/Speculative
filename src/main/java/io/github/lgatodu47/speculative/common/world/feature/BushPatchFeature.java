package io.github.lgatodu47.speculative.common.world.feature;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BushPatchFeature extends Feature<BlockStateFeatureConfig> {
    public BushPatchFeature(Codec<BlockStateFeatureConfig> configFactoryIn) {
        super(configFactoryIn);
        setRegistryName("bush_patch");
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateFeatureConfig config) {
        if (reader.getBlockState(pos.down()).getBlock().equals(SpeculativeBlocks.SPECULO_SAND.get()) && reader.isAirBlock(pos)) {
            List<BlockPos> poses = calcPoses(pos, rand.nextInt(3) + 1);
            poses.forEach(position -> reader.setBlockState(position, config.state, 2));
            return true;
        }

        return false;
    }

    private static List<BlockPos> calcPoses(BlockPos origin, int scale) {
        List<BlockPos> posList = new ArrayList<>();
        BlockPos up = origin.up();

        posList.add(origin);

        for (Direction direction : Direction.values()) {
            if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
                continue;
            }

            for (int i = 0; i < scale; i++) {
                posList.add(origin.offset(direction, i + 1));
                posList.add(up.offset(direction, i));
            }
        }

        return posList;
    }
}
