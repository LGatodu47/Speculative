package io.github.lgatodu47.speculative.common.world.biome;

import io.github.lgatodu47.speculative.common.world.feature.DefaultSpeculativeFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class SpeculoMountainsBiome extends SpeculoWorldBiome {
    @Override
    protected Biome.Builder create() {
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).temperature(0.3F).category(Biome.Category.EXTREME_HILLS).depth(1.25F).scale(0.75F).downfall(0.2F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = super.configureGeneration();
        DefaultSpeculativeFeatures.addSpeculoMountainsCarvers(builder);
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldFlowers(builder);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT));
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        return builder;
    }

    @Override
    public int getSpawnWeight() {
        return 10;
    }

    @Override
    public void addTypes(Set<BiomeDictionary.Type> typeSet) {
        typeSet.add(BiomeDictionary.Type.MOUNTAIN);
    }
}
