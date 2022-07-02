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
    public static final Lazy<SurfaceBuilderConfig> DESERT_SURFACE = Lazy.of(() -> new SurfaceBuilderConfig(SpeculativeBlocks.SPECULO_SAND.get().getDefaultState(), SpeculativeBlocks.SPECULO_SAND.get().getDefaultState(), SpeculativeBlocks.SPECULO_BLOCK.get().getDefaultState()));
    public static final Lazy<ConfiguredFeature<?, ?>> BUSHES = Lazy.of(() -> Feature.RANDOM_PATCH
            .withConfiguration(new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(SpeculativeBlocks.MANGO_BUSH.get().getDefaultState()), new SimpleBlockPlacer())
                    .tries(32)
                    .xSpread(3)
                    .ySpread(2)
                    .zSpread(3)
                    .whitelist(ImmutableSet.of(SpeculativeBlocks.SPECULO_SAND.get(), SpeculativeBlocks.MANGO_BUSH.get())).build())
            .withPlacement(Placement.CHANCE.configure(new ChanceConfig(40)))
            .withPlacement(Placement.HEIGHTMAP.configure(NoPlacementConfig.INSTANCE))
            .square());
    public static final Lazy<ConfiguredFeature<?, ?>> UNSTABLE_WATER_LAKES = Lazy.of(() -> SpeculativeFeatures.OASIS
            .withConfiguration(new BlockStateFeatureConfig(SpeculativeFluids.UNSTABLE_WATER.getBlock().get().getDefaultState()))
            .withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(16))));

    @Override
    protected Biome.Builder create() {
        return new Biome.Builder().category(Biome.Category.DESERT).precipitation(Biome.RainType.NONE).depth(0.0625F).scale(0.025F).temperature(0.8F).downfall(0.0F);
    }

    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        BiomeGenerationSettings.Builder builder = new SpeculativeGenerationSettingsBuilder().withLazyStructure(SpeculativeStructures.Configured.CONFIGURED_SPECULO_PYRAMID);
        builder.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(DESERT_SURFACE.get()));
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, BUSHES.get());
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, UNSTABLE_WATER_LAKES.get());
        DefaultSpeculativeFeatures.addSpeculoworldOres(builder);
        DefaultSpeculativeFeatures.addSpeculoworldCarvers(builder);
        return builder;
    }

    @Override
    protected BiomeAmbience.Builder configureAmbience() {
        return super.configureAmbience().setFogColor(16766346).withSkyColor(16766346);
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
