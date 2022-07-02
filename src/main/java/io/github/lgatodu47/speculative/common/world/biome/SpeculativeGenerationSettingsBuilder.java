package io.github.lgatodu47.speculative.common.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.function.Supplier;

public class SpeculativeGenerationSettingsBuilder extends BiomeGenerationSettings.Builder {
    public SpeculativeGenerationSettingsBuilder withLazyFeature(GenerationStage.Decoration stage, Supplier<ConfiguredFeature<?, ?>> feature) {
        return (SpeculativeGenerationSettingsBuilder) withFeature(stage.ordinal(), feature);
    }

    public SpeculativeGenerationSettingsBuilder withLazyCarver(GenerationStage.Carving stage, Supplier<ConfiguredCarver<?>> carver) {
        this.carvers.computeIfAbsent(stage, (s) -> Lists.newArrayList()).add(carver);
        return this;
    }

    public SpeculativeGenerationSettingsBuilder withLazyStructure(Supplier<StructureFeature<?, ?>> structure) {
        this.structures.add(structure);
        return this;
    }

    public static SpeculativeGenerationSettingsBuilder get(BiomeGenerationSettings.Builder builder) {
        return (SpeculativeGenerationSettingsBuilder) builder;
    }
}
