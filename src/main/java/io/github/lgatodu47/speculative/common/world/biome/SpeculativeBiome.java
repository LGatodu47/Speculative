package io.github.lgatodu47.speculative.common.world.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Set;

public abstract class SpeculativeBiome {
    public final Biome build() {
        return create().generationSettings(configureGeneration().build()).specialEffects(configureEffects().build()).mobSpawnSettings(configureMobSpawns().build()).build();
    }

    protected abstract Biome.BiomeBuilder create();

    protected abstract BiomeGenerationSettings.Builder configureGeneration();

    protected abstract BiomeSpecialEffects.Builder configureEffects();

    protected abstract MobSpawnSettings.Builder configureMobSpawns();

    public abstract int getSpawnWeight();

    public abstract void addTypes(Set<BiomeDictionary.Type> typeSet);
}
