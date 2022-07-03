package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeFeatures;
import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorldCarvers;
import io.github.lgatodu47.speculative.util.SpeculativeFillerBlockTypes;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;

public class DefaultSpeculativeFeatures {
    public static final ConfiguredPlacement<?> SPECULO_ORE_CONFIG = Placement.RANGE.configured(new TopSolidRangeConfig(10, 0, 35)).count(4);
    public static final ConfiguredPlacement<?> RADIOACTIVE_DIAMOND_ORE_CONFIG = Placement.RANGE.configured(new TopSolidRangeConfig(5, 0, 28)).count(3);
    public static final BlockClusterFeatureConfig SPECULO_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SpeculativeBlocks.SPECULO_FLOWER.get().defaultBlockState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockStateFeatureConfig SULFURIC_WATER_CONFIG = new BlockStateFeatureConfig(SpeculativeFluids.SULFURIC_WATER.getBlock().get().defaultBlockState());

    public static void addSpeculoworldCarvers(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CAVES_WORLD_CARVER.configured(new ProbabilityConfig(0.14285715F)));
        builder.addCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CANYONS_WORLD_CARVER.configured(new ProbabilityConfig(0.02F)));
    }

    public static void addSpeculoMountainsCarvers(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CAVES_WORLD_CARVER.configured(new ProbabilityConfig(0.456F)));
        builder.addCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CANYONS_WORLD_CARVER.configured(new ProbabilityConfig(0.2568F)));
    }

    public static void addSpeculoworldOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(SpeculativeFillerBlockTypes.SPECULO_STONE, SpeculativeBlocks.SPECULO_ORE_W.get().defaultBlockState(), 10)).decorated(SPECULO_ORE_CONFIG));
        builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(new OreFeatureConfig(SpeculativeFillerBlockTypes.SPECULO_STONE, SpeculativeBlocks.RADIOACTIVE_DIAMOND_ORE.get().defaultBlockState(), 8)).decorated(RADIOACTIVE_DIAMOND_ORE_CONFIG));
    }

    public static void addSpeculoworldFlowers(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.configured(SPECULO_FLOWER_CONFIG).decorated(Features.Placements.ADD_32).decorated(Features.Placements.HEIGHTMAP).squared().decorated(Placement.COUNT_NOISE.configured(new NoiseDependant(-0.8D, 15, 4))));
    }

    public static void addSulfuricWaterLakes(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, SpeculativeFeatures.MOD_LAKES.configured(SULFURIC_WATER_CONFIG).decorated(Placement.WATER_LAKE.configured(new ChanceConfig(6))));
    }
}
