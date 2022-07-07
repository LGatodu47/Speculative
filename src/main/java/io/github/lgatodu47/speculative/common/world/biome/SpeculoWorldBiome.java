package io.github.lgatodu47.speculative.common.world.biome;

import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public abstract class SpeculoWorldBiome extends SpeculativeBiome {
    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        return new BiomeGenerationSettings.Builder();
    }

    @Override
    protected BiomeSpecialEffects.Builder configureEffects() {
        return new BiomeSpecialEffects.Builder()
                .grassColorOverride(12411136)
                .foliageColorOverride(12411136)
                .skyColor(9194771)
                .fogColor(9194771)
                .waterColor(5057024)
                .waterFogColor(3283968);
    }

    @Override
    protected MobSpawnSettings.Builder configureMobSpawns() {
        return new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(SpeculativeEntityTypes.SPECULO_PIG.get(), 1, 2, 4));
    }
}
