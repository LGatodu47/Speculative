package io.github.lgatodu47.speculative.common.world.biome;

import io.github.lgatodu47.speculative.common.init.SpeculativeConfiguredFeatures;
import io.github.lgatodu47.speculative.common.world.feature.DefaultSpeculativeFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public class SpeculoDesertBiome extends SpeculoWorldBiome {
    @Override
    protected Biome.BiomeBuilder create() {
        return new Biome.BiomeBuilder().biomeCategory(Biome.BiomeCategory.DESERT).precipitation(Biome.Precipitation.NONE).temperature(0.8F).downfall(0.0F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = super.configureGeneration();
        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, SpeculativeConfiguredFeatures.MANGO_BUSH.getPlacedFeature());
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, SpeculativeConfiguredFeatures.OASIS.getPlacedFeature());
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldCarvers(builder);
        return builder;
    }

    @Override
    protected BiomeSpecialEffects.Builder configureEffects() {
        return super.configureEffects().fogColor(16766346).skyColor(16766346);
    }

    @Override
    public int getSpawnWeight() {
        return 15;
    }

    @Override
    public void addTypes(Set<BiomeDictionary.Type> typeSet) {
        typeSet.add(BiomeDictionary.Type.DRY);
    }
}
