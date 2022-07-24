package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeConfiguredFeatures;
import io.github.lgatodu47.speculative.common.world.gen.foliageplacer.TourmalineFoliagePlacer;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Random;

public class TourmalineTreeGrower extends AbstractTreeGrower {
    public static final Lazy<TreeConfiguration> CONFIG = Lazy.of(() -> new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(SpeculativeBlocks.TOURMALINE_LOG.get()),
            new StraightTrunkPlacer(7, 0, 2),
            BlockStateProvider.simple(SpeculativeBlocks.TOURMALINE_LEAVES.get()),
            new TourmalineFoliagePlacer(3, 0),
            new TwoLayersFeatureSize(1, 0, 2)
    ).ignoreVines().dirt(BlockStateProvider.simple(SpeculativeBlocks.SPECULO_DIRT.get())).build());

    @Nullable
    @Override
    protected Holder<ConfiguredFeature<TreeConfiguration, ?>> getConfiguredFeature(Random randomIn, boolean largeHive) {
        return SpeculativeConfiguredFeatures.TOURMALINE_TREE.getConfiguredFeature();
    }
}
