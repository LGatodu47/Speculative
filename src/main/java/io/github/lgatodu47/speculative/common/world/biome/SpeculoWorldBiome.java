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
        return new BiomeGenerationSettings.Builder().surfaceBuilder(SurfaceBuilder.DEFAULT.configured(new SurfaceBuilderConfig(
                SpeculativeBlocks.SPECULO_GRASS.get().defaultBlockState(),
                SpeculativeBlocks.SPECULO_DIRT.get().defaultBlockState(),
                Blocks.GRAVEL.defaultBlockState()
        )));
    }

    @Override
    protected BiomeAmbience.Builder configureAmbience() {
        return new BiomeAmbience.Builder()
                .grassColorOverride(12411136)
                .foliageColorOverride(12411136)
                .skyColor(9194771)
                .fogColor(9194771)
                .waterColor(5057024)
                .waterFogColor(3283968);
    }

    @Override
    protected MobSpawnInfo.Builder configureMobSpawns() {
        return new MobSpawnInfo.Builder()
                .addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(SpeculativeEntityTypes.SPECULO_PIG.get(), 1, 2, 4));
    }
}
