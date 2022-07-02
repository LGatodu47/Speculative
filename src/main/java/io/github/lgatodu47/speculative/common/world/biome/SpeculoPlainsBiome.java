package io.github.lgatodu47.speculative.common.world.biome;

import com.google.common.collect.ImmutableList;
import io.github.lgatodu47.speculative.common.world.feature.DefaultSpeculativeFeatures;
import io.github.lgatodu47.speculative.common.world.feature.SpeculoTree;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class SpeculoPlainsBiome extends SpeculoWorldBiome {
    @Override
    protected Biome.Builder create() {
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).temperature(0.8F).category(Biome.Category.PLAINS).depth(0.125F).scale(0.05F).downfall(0.4F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = super.configureGeneration();
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.TREE.withConfiguration(SpeculoTree.SPECULO_FANCY_TREE_CONFIG).withChance(0.33333334F)), Feature.TREE.withConfiguration(SpeculoTree.SPECULO_TREE_CONFIG))).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
        DefaultSpeculativeFeatures.addSpeculoworldCarvers(builder);
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldFlowers(builder);
        DefaultSpeculativeFeatures.addSulfuricWaterLakes(builder);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.FLOWER_TALL_GRASS_PLACEMENT).square().withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 0, 7))));
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).withPlacement(Placement.COUNT_NOISE.configure(new NoiseDependant(-0.8D, 5, 10))));
        DefaultBiomeFeatures.withFrozenTopLayer(builder);
        return builder;
    }

    @Override
    public int getSpawnWeight() {
        return 30;
    }

    @Override
    public void addTypes(Set<BiomeDictionary.Type> typeSet) {
        typeSet.add(BiomeDictionary.Type.PLAINS);
    }
}
