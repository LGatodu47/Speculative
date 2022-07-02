package io.github.lgatodu47.speculative.common.world.biome;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeEntityTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

public abstract class SpeculoWorldBiome extends SpeculativeBiome {
    @Override
    protected BiomeGenerationSettings.Builder configureGeneration() {
        return new BiomeGenerationSettings.Builder().withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(
                SpeculativeBlocks.SPECULO_GRASS.get().getDefaultState(),
                SpeculativeBlocks.SPECULO_DIRT.get().getDefaultState(),
                Blocks.GRAVEL.getDefaultState()
        )));
    }

    @Override
    protected BiomeAmbience.Builder configureAmbience() {
        return new BiomeAmbience.Builder()
                .withGrassColor(12411136)
                .withFoliageColor(12411136)
                .withSkyColor(9194771)
                .setFogColor(9194771)
                .setWaterColor(5057024)
                .setWaterFogColor(3283968);
    }

    @Override
    protected MobSpawnInfo.Builder configureMobSpawns() {
        return new MobSpawnInfo.Builder()
                .withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(SpeculativeEntityTypes.SPECULO_PIG.get(), 1, 2, 4));
    }
}
