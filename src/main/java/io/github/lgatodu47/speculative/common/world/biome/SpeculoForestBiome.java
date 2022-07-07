package io.github.lgatodu47.speculative.common.world.biome;

import io.github.lgatodu47.speculative.common.init.SpeculativeConfiguredFeatures;
import io.github.lgatodu47.speculative.common.world.feature.DefaultSpeculativeFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class SpeculoForestBiome extends SpeculoWorldBiome {
    @Override
    protected Biome.BiomeBuilder create() {
        return new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).temperature(0.7F).biomeCategory(Biome.BiomeCategory.FOREST).downfall(0.6F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = super.configureGeneration();
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SpeculativeConfiguredFeatures.TREES_SPECULO_FOREST.getPlacedFeature());
        DefaultSpeculativeFeatures.addSpeculoworldCarvers(builder);
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldFlowers(builder);
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
        return builder;
    }

    @Override
    public int getSpawnWeight() {
        return 20;
    }

    @Override
    public void addTypes(Set<BiomeDictionary.Type> typeSet) {
        typeSet.add(BiomeDictionary.Type.FOREST);
    }
}
