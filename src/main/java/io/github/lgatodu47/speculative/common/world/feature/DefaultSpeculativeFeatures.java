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
    public static final ConfiguredPlacement<?> SPECULO_ORE_CONFIG = Placement.RANGE.configure(new TopSolidRangeConfig(10, 0, 35)).count(4);
    public static final ConfiguredPlacement<?> RADIOACTIVE_DIAMOND_ORE_CONFIG = Placement.RANGE.configure(new TopSolidRangeConfig(5, 0, 28)).count(3);
    public static final BlockClusterFeatureConfig SPECULO_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SpeculativeBlocks.SPECULO_FLOWER.get().getDefaultState()), new SimpleBlockPlacer())).tries(64).build();
    public static final BlockStateFeatureConfig SULFURIC_WATER_CONFIG = new BlockStateFeatureConfig(SpeculativeFluids.SULFURIC_WATER.getBlock().get().getDefaultState());

    public static void addSpeculoworldCarvers(BiomeGenerationSettings.Builder builder) {
        builder.withCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CAVES_WORLD_CARVER.func_242761_a(new ProbabilityConfig(0.14285715F)));
        builder.withCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CANYONS_WORLD_CARVER.func_242761_a(new ProbabilityConfig(0.02F)));
    }

    public static void addSpeculoMountainsCarvers(BiomeGenerationSettings.Builder builder) {
        builder.withCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CAVES_WORLD_CARVER.func_242761_a(new ProbabilityConfig(0.456F)));
        builder.withCarver(GenerationStage.Carving.AIR, SpeculativeWorldCarvers.SPECULO_CANYONS_WORLD_CARVER.func_242761_a(new ProbabilityConfig(0.2568F)));
    }

    public static void addSpeculoworldOres(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(SpeculativeFillerBlockTypes.SPECULO_STONE, SpeculativeBlocks.SPECULO_ORE_W.get().getDefaultState(), 10)).withPlacement(SPECULO_ORE_CONFIG));
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(SpeculativeFillerBlockTypes.SPECULO_STONE, SpeculativeBlocks.RADIOACTIVE_DIAMOND_ORE.get().getDefaultState(), 8)).withPlacement(RADIOACTIVE_DIAMOND_ORE_CONFIG));
    }

    public static void addSpeculoworldFlowers(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.FLOWER.withConfiguration(SPECULO_FLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.FLOWER_TALL_GRASS_PLACEMENT).square().withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 15, 4))));
    }

    public static void addSulfuricWaterLakes(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, SpeculativeFeatures.MOD_LAKES.withConfiguration(SULFURIC_WATER_CONFIG).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(6))));
    }
}
