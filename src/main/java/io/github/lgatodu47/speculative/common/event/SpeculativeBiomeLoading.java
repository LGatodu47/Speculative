package io.github.lgatodu47.speculative.common.event;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.stream.Stream;

public class SpeculativeBiomeLoading {
    public static void addFeatures(BiomeLoadingEvent event) {
        generateOre(event, 3, 8, 25, SpeculativeBlocks.SPECULO_ORE.get().getDefaultState(), Biomes.JUNGLE);
        generateOre(event, 5, 6, 42, SpeculativeBlocks.URANIUM_ORE.get().getDefaultState(), Biomes.MOUNTAINS, Biomes.DESERT);
        generateOre(event, 7, 8, 50, SpeculativeBlocks.URANIUM_ORE.get().getDefaultState(), Biomes.BADLANDS);
    }

    @SafeVarargs
    private static void generateOre(BiomeLoadingEvent event, int count, int size, int maximumHeight, BlockState state, RegistryKey<Biome>... biomes) {
        if(event.getName() == null) {
            return;
        }

        if (Stream.of(biomes).map(RegistryKey::getLocation).anyMatch(event.getName()::equals)) {
            event.getGeneration().withFeature(
                    GenerationStage.Decoration.UNDERGROUND_ORES,
                    Feature.ORE.withConfiguration(
                            new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, state, size)
                    ).count(count).range(maximumHeight)
            );
        }
    }
}
