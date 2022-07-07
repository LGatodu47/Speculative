package io.github.lgatodu47.speculative.common.world.feature;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BushPatchFeature extends Feature<BlockStateConfiguration> {
    public BushPatchFeature(Codec<BlockStateConfiguration> configFactoryIn) {
        super(configFactoryIn);
        setRegistryName("bush_patch");
    }

    @Override
    public boolean place(FeaturePlaceContext<BlockStateConfiguration> pContext) {
        return place(pContext.level(), pContext.chunkGenerator(), pContext.random(), pContext.origin(), pContext.config());
    }

    public boolean place(WorldGenLevel reader, ChunkGenerator generator, Random rand, BlockPos pos, BlockStateConfiguration config) {
        if (reader.getBlockState(pos.below()).getBlock().equals(SpeculativeBlocks.SPECULO_SAND.get()) && reader.isEmptyBlock(pos)) {
            List<BlockPos> poses = calcPoses(pos, rand.nextInt(3) + 1);
            poses.forEach(position -> reader.setBlock(position, config.state, 2));
            return true;
        }

        return false;
    }

    private static List<BlockPos> calcPoses(BlockPos origin, int scale) {
        List<BlockPos> posList = new ArrayList<>();
        BlockPos up = origin.above();

        posList.add(origin);

        for (Direction direction : Direction.values()) {
            if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
                continue;
            }

            for (int i = 0; i < scale; i++) {
                posList.add(origin.relative(direction, i + 1));
                posList.add(up.relative(direction, i));
            }
        }

        return posList;
    }
}
