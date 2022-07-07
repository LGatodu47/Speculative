package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.common.init.SpeculativeConfiguredFeatures;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorldCarvers;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class DefaultSpeculativeFeatures {
    public static void addSpeculoworldCarvers(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, SpeculativeWorldCarvers.Configured.CAVE);
        builder.addCarver(GenerationStep.Carving.AIR, SpeculativeWorldCarvers.Configured.CANYON);
    }

    public static void addSpeculoMountainsCarvers(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, SpeculativeWorldCarvers.Configured.CAVE);
        builder.addCarver(GenerationStep.Carving.AIR, SpeculativeWorldCarvers.Configured.CANYON);
    }

    public static void addSpeculoworldOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SpeculativeConfiguredFeatures.SPECULO_WORLD_SPECULO_ORE.getPlacedFeature());
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, SpeculativeConfiguredFeatures.RADIOACTIVE_DIAMOND_ORE.getPlacedFeature());
    }

    public static void addSpeculoworldFlowers(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SpeculativeConfiguredFeatures.SPECULO_FLOWER.getPlacedFeature());
    }

    public static void addSulfuricWaterLakes(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SpeculativeConfiguredFeatures.SULFURIC_WATER_LAKE.getPlacedFeature());
    }
}
