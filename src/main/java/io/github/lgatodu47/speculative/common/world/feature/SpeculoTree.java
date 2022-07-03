package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.SpeculativeTrunkPlacer;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;

import java.util.OptionalInt;
import java.util.Random;

public class SpeculoTree extends Tree {
    public static final BaseTreeFeatureConfig SPECULO_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(SpeculativeBlocks.SPECULO_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(SpeculativeBlocks.SPECULO_LEAVES.get().defaultBlockState()),
            new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3),
            new SpeculativeTrunkPlacer(5, 4, 0),
            new TwoLayerFeature(1, 0, 1)).ignoreVines().build());
    public static final BaseTreeFeatureConfig SPECULO_FANCY_TREE_CONFIG = new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(SpeculativeBlocks.SPECULO_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(SpeculativeBlocks.SPECULO_LEAVES.get().defaultBlockState()),
            new FancyFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(4), 4),
            new FancyTrunkPlacer(3, 11, 0),
            new TwoLayerFeature(0, 0, 0, OptionalInt.of(4))).ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING).build();

    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean p_225546_2_) {
        return Feature.TREE.configured(SPECULO_TREE_CONFIG);
    }
}
