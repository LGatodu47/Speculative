package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.common.util.Lazy;

import java.util.OptionalInt;
import java.util.Random;

public class SpeculoTreeGrower extends AbstractTreeGrower {
    public static final Lazy<TreeConfiguration> SPECULO_TREE_CONFIG = Lazy.of(() -> new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(SpeculativeBlocks.SPECULO_LOG.get()),
            new StraightTrunkPlacer(5, 4, 0),
            BlockStateProvider.simple(SpeculativeBlocks.SPECULO_LEAVES.get()),
            new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
            new TwoLayersFeatureSize(1, 0, 1)
    ).ignoreVines().dirt(BlockStateProvider.simple(SpeculativeBlocks.SPECULO_DIRT.get())).build());
    public static final Lazy<TreeConfiguration> FANCY_SPECULO_TREE_CONFIG = Lazy.of(() -> new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(SpeculativeBlocks.SPECULO_LOG.get()),
            new FancyTrunkPlacer(3, 11, 0),
            BlockStateProvider.simple(SpeculativeBlocks.SPECULO_LEAVES.get()),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
    ).ignoreVines().dirt(BlockStateProvider.simple(SpeculativeBlocks.SPECULO_DIRT.get())).build());

    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random randomIn, boolean pLargeHive) {
        return SpeculativeConfiguredFeatures.SPECULO_TREE.getConfiguredFeature();
    }
}
