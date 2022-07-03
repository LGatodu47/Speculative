package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.TourmalineFoliagePlacer;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.SpeculativeTrunkPlacer;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TwoLayerFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class TourmalineTree extends Tree {
    public static final BaseTreeFeatureConfig CONFIG = new BaseTreeFeatureConfig.Builder(
            new SimpleBlockStateProvider(SpeculativeBlocks.TOURMALINE_LOG.get().defaultBlockState()),
            new SimpleBlockStateProvider(SpeculativeBlocks.TOURMALINE_LEAVES.get().defaultBlockState()),
            new TourmalineFoliagePlacer(3, 0),
            new SpeculativeTrunkPlacer(7, 0, 2),
            new TwoLayerFeature(1, 0, 2))
            .ignoreVines().build();

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random randomIn, boolean largeHive) {
        return Feature.TREE.configured(CONFIG);
    }
}
