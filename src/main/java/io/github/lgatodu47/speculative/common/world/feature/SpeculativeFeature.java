package io.github.lgatodu47.speculative.common.world.feature;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpeculativeFeature<FC extends FeatureConfiguration> {
    private final String name;
    private final Lazy<Holder<ConfiguredFeature<FC, ?>>> configured;
    private final Lazy<Holder<PlacedFeature>> placed;
    @Nullable
    private GenerationStep.Decoration decorationStep = null;
    @Nullable
    private ResourceKey<Biome>[] biomes = null;

    public SpeculativeFeature(String name, Feature<FC> feature, FC config, PlacementModifier... modifiers) {
        this.name = new ResourceLocation(Speculative.MODID, name).toString();
        this.configured = Lazy.of(() -> FeatureUtils.register(this.name, feature, config));
        this.placed = Lazy.of(() -> PlacementUtils.register(this.name, configured.get(), modifiers));
    }

    public void register() {
        if(this.configured.get() == null) {
            throw new RuntimeException("Configured Feature with name '" + name + "' was not registered!");
        }
        if(this.placed.get() == null) {
            throw new RuntimeException("Placed Feature with name '" + name + "' was not registered!");
        }
    }

    @SafeVarargs
    public final SpeculativeFeature<FC> withBiomes(@Nonnull GenerationStep.Decoration decoration, @Nonnull ResourceKey<Biome>... biomes) {
        this.decorationStep = decoration;
        this.biomes = biomes;
        return this;
    }

    public void addToBiome(BiomeLoadingEvent event) {
        if(decorationStep == null || biomes == null || event.getName() == null) {
            return;
        }

        if(Stream.of(this.biomes).map(ResourceKey::location).anyMatch(event.getName()::equals)) {
            event.getGeneration().addFeature(this.decorationStep, getPlacedFeature());
        }
    }

    public Holder<ConfiguredFeature<FC, ?>> getConfiguredFeature() {
        return configured.get();
    }

    public Holder<PlacedFeature> getPlacedFeature() {
        return placed.get();
    }
}
