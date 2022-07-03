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
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class SpeculoForestBiome extends SpeculoWorldBiome {
    @Override
    protected Biome.Builder create() {
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).temperature(0.7F).biomeCategory(Biome.Category.FOREST).depth(0.105F).scale(0.205F).downfall(0.6F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = super.configureGeneration();
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configured(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.TREE.configured(SpeculoTree.SPECULO_FANCY_TREE_CONFIG).weighted(0.33333334F)), Feature.TREE.configured(SpeculoTree.SPECULO_TREE_CONFIG))).decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
        DefaultSpeculativeFeatures.addSpeculoworldCarvers(builder);
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldFlowers(builder);
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_PATCH.configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).count(2));
        DefaultBiomeFeatures.addSurfaceFreezing(builder);
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
