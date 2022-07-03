package io.github.lgatodu47.speculative.common.world.biome;

import com.google.common.collect.ImmutableSet;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeFeatures;
import io.github.lgatodu47.speculative.common.init.SpeculativeFluids;
import io.github.lgatodu47.speculative.common.init.SpeculativeStructures;
import io.github.lgatodu47.speculative.common.world.feature.DefaultSpeculativeFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.Lazy;

import java.util.Set;

public class SpeculoDesertBiome extends SpeculoWorldBiome {
    public static final Lazy<SurfaceBuilderConfig> DESERT_SURFACE = Lazy.of(() -> new SurfaceBuilderConfig(SpeculativeBlocks.SPECULO_SAND.get().defaultBlockState(), SpeculativeBlocks.SPECULO_SAND.get().defaultBlockState(), SpeculativeBlocks.SPECULO_BLOCK.get().defaultBlockState()));
    public static final Lazy<ConfiguredFeature<?, ?>> BUSHES = Lazy.of(() -> Feature.RANDOM_PATCH
            .configured(new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(SpeculativeBlocks.MANGO_BUSH.get().defaultBlockState()), new SimpleBlockPlacer())
                    .tries(32)
                    .xspread(3)
                    .yspread(2)
                    .zspread(3)
                    .whitelist(ImmutableSet.of(SpeculativeBlocks.SPECULO_SAND.get(), SpeculativeBlocks.MANGO_BUSH.get())).build())
            .decorated(Placement.CHANCE.configured(new ChanceConfig(40)))
            .decorated(Placement.HEIGHTMAP.configured(NoPlacementConfig.INSTANCE))
            .squared());
    public static final Lazy<ConfiguredFeature<?, ?>> UNSTABLE_WATER_LAKES = Lazy.of(() -> SpeculativeFeatures.OASIS
            .configured(new BlockStateFeatureConfig(SpeculativeFluids.UNSTABLE_WATER.getBlock().get().defaultBlockState()))
            .decorated(Placement.WATER_LAKE.configured(new ChanceConfig(16))));

    @Override
    protected Biome.Builder create() {
        return new Biome.Builder().biomeCategory(Biome.Category.DESERT).precipitation(Biome.RainType.NONE).depth(0.0625F).scale(0.025F).temperature(0.8F).downfall(0.0F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = new SpeculativeGenerationSettingsBuilder().withLazyStructure(SpeculativeStructures.Configured.CONFIGURED_SPECULO_PYRAMID);
        builder.surfaceBuilder(SurfaceBuilder.DEFAULT.configured(DESERT_SURFACE.get()));
        builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BUSHES.get());
        builder.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UNSTABLE_WATER_LAKES.get());
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldCarvers(builder);
        return builder;
    }

    @Override
    protected BiomeAmbience.Builder configureAmbience() {
        return super.configureAmbience().fogColor(16766346).skyColor(16766346);
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
