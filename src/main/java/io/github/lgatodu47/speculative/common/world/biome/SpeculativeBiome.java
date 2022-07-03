package io.github.lgatodu47.speculative.common.world.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public abstract class SpeculativeBiome {
    public final Biome build() {
        return create().generationSettings(configureGeneration().build()).specialEffects(configureAmbience().build()).mobSpawnSettings(configureMobSpawns().build()).build();
    }

    protected abstract Biome.Builder create();

    protected abstract BiomeGenerationSettings.Builder configureGeneration();

    protected abstract BiomeAmbience.Builder configureAmbience();

    protected abstract MobSpawnInfo.Builder configureMobSpawns();

    public abstract int getSpawnWeight();

    public abstract void addTypes(Set<BiomeDictionary.Type> typeSet);
}
