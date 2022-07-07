package io.github.lgatodu47.speculative.common.init;

import com.google.common.collect.ImmutableList;
import io.github.lgatodu47.speculative.common.world.feature.SpeculativeFeature;
import io.github.lgatodu47.speculative.common.world.feature.SpeculoTreeGrower;
import io.github.lgatodu47.speculative.common.world.feature.TourmalineTreeGrower;
import io.github.lgatodu47.speculative.util.SpeculativeFillerBlockTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public final class SpeculativeConfiguredFeatures {
    private static final Set<SpeculativeFeature<?>> SPECULATIVE_FEATURES = new HashSet<>();

    public static final SpeculativeFeature<OreConfiguration> SPECULO_ORE = overworldOre("speculo_ore", 3, 8, 25, SpeculativeBlocks.SPECULO_ORE.get().defaultBlockState(), Biomes.JUNGLE);
    public static final SpeculativeFeature<OreConfiguration> URANIUM_ORE = overworldOre("uranium_ore", 5, 6, 42, SpeculativeBlocks.URANIUM_ORE.get().defaultBlockState(), Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.DESERT);
    public static final SpeculativeFeature<OreConfiguration> URANIUM_ORE_BADLANDS = overworldOre("uranium_ore_badlands", 7, 8, 50, SpeculativeBlocks.URANIUM_ORE.get().defaultBlockState(), Biomes.BADLANDS);
    public static final SpeculativeFeature<OreConfiguration> SPECULO_WORLD_SPECULO_ORE = speculoWorldOre("speculo_world_speculo_ore", 4, 10, 35, SpeculativeBlocks.SPECULO_ORE_W.get().defaultBlockState());
    public static final SpeculativeFeature<OreConfiguration> RADIOACTIVE_DIAMOND_ORE = speculoWorldOre("radioactive_diamond_ore", 3, 8, 28, SpeculativeBlocks.RADIOACTIVE_DIAMOND_ORE.get().defaultBlockState());

    public static final SpeculativeFeature<RandomPatchConfiguration> SPECULO_FLOWER = register("speculo_flower",
            Feature.FLOWER,
            patch(64, SpeculativeBlocks.SPECULO_FLOWER.get()),
            NoiseThresholdCountPlacement.of(-0.8D, 15, 4),
            RarityFilter.onAverageOnceEvery(32),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            BiomeFilter.biome());
    public static final SpeculativeFeature<RandomPatchConfiguration> MANGO_BUSH = register("mango_bush",
            Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 3, 2, PlacementUtils.filtered(Feature.SIMPLE_BLOCK,
                    new SimpleBlockConfiguration(BlockStateProvider.simple(SpeculativeBlocks.MANGO_BUSH.get())),
                    BlockPredicate.allOf(BlockPredicate.matchesBlock(Blocks.AIR, BlockPos.ZERO), BlockPredicate.anyOf(BlockPredicate.solid(new Vec3i(0, -1, 0)), BlockPredicate.matchesBlock(SpeculativeBlocks.MANGO_BUSH.get(), new Vec3i(0, -1, 0)))))),
            RarityFilter.onAverageOnceEvery(40),
            PlacementUtils.HEIGHTMAP,
            InSquarePlacement.spread(),
            BiomeFilter.biome());

    public static final SpeculativeFeature<BlockStateConfiguration> SULFURIC_WATER_LAKE = register("sulfuric_water_lake",
            SpeculativeFeatures.MOD_LAKES,
            new BlockStateConfiguration(SpeculativeFluids.SULFURIC_WATER.getBlock().get().defaultBlockState()),
            RarityFilter.onAverageOnceEvery(6),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.insideWorld(new BlockPos(0, -5, 0)), 32),
            BiomeFilter.biome());
    public static final SpeculativeFeature<BlockStateConfiguration> OASIS = register("oasis",
            SpeculativeFeatures.OASIS,
            new BlockStateConfiguration(SpeculativeFluids.UNSTABLE_WATER.getBlock().get().defaultBlockState()),
            RarityFilter.onAverageOnceEvery(16),
            InSquarePlacement.spread(),
            HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(0), VerticalAnchor.top())),
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.insideWorld(new BlockPos(0, -5, 0)), 32),
            BiomeFilter.biome());

    public static final SpeculativeFeature<TreeConfiguration> SPECULO_TREE = register("speculo_tree", Feature.TREE, SpeculoTreeGrower.SPECULO_TREE_CONFIG);
    public static final SpeculativeFeature<TreeConfiguration> FANCY_SPECULO_TREE = register("fancy_speculo_tree", Feature.TREE, SpeculoTreeGrower.FANCY_SPECULO_TREE_CONFIG);
    public static final SpeculativeFeature<TreeConfiguration> TOURMALINE_TREE = register("tourmaline_tree", Feature.TREE, TourmalineTreeGrower.CONFIG);

    public static final SpeculativeFeature<RandomFeatureConfiguration> TREES_SPECULO_PLAINS = register("trees_speculo_plains",
            Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(SpeculativeConfiguredFeatures.FANCY_SPECULO_TREE.getPlacedFeature(), 0.33333334F)), SpeculativeConfiguredFeatures.SPECULO_TREE.getPlacedFeature()),
            PlacementUtils.countExtra(0, 0.05F, 1),
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
            BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(SpeculativeBlocks.SPECULO_TREE_SAPLING.get().defaultBlockState(), BlockPos.ZERO)),
            BiomeFilter.biome());
    public static final SpeculativeFeature<RandomFeatureConfiguration> TREES_SPECULO_FOREST = register("trees_speculo_forest",
            Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(ImmutableList.of(new WeightedPlacedFeature(SpeculativeConfiguredFeatures.FANCY_SPECULO_TREE.getPlacedFeature(), 0.33333334F)), SpeculativeConfiguredFeatures.SPECULO_TREE.getPlacedFeature()),
            PlacementUtils.countExtra(10, 0.1F, 1),
            InSquarePlacement.spread(),
            SurfaceWaterDepthFilter.forMaxDepth(0),
            PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
            BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(SpeculativeBlocks.SPECULO_TREE_SAPLING.get().defaultBlockState(), BlockPos.ZERO)),
            BiomeFilter.biome());

    @SafeVarargs
    private static SpeculativeFeature<OreConfiguration> overworldOre(String name, int count, int size, int maximumHeight, BlockState state, ResourceKey<Biome>... biomes) {
        return ore(name, OreFeatures.NATURAL_STONE, count, size, maximumHeight, state, biomes);
    }

    @SafeVarargs
    private static SpeculativeFeature<OreConfiguration> speculoWorldOre(String name, int count, int size, int maximumHeight, BlockState state, ResourceKey<Biome>... biomes) {
        return ore(name, SpeculativeFillerBlockTypes.SPECULO_STONE, count, size, maximumHeight, state, biomes);
    }

    @SafeVarargs
    private static SpeculativeFeature<OreConfiguration> ore(String name, RuleTest matchFor, int count, int size, int maximumHeight, BlockState state, ResourceKey<Biome>... biomes) {
        return register(name,
                Feature.ORE,
                new OreConfiguration(matchFor, state, size),
                CountPlacement.of(count),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(maximumHeight), VerticalAnchor.top())
        ).withBiomes(GenerationStep.Decoration.UNDERGROUND_ORES, biomes);
    }

    private static <FC extends FeatureConfiguration> SpeculativeFeature<FC> register(String name, Feature<FC> feature, FC config, PlacementModifier... modifiers) {
        SpeculativeFeature<FC> speculativeFeature = new SpeculativeFeature<>(name, feature, config, modifiers);
        SPECULATIVE_FEATURES.add(speculativeFeature);
        return speculativeFeature;
    }

    private static RandomPatchConfiguration patch(int tries, Block block) {
        return FeatureUtils.simpleRandomPatchConfiguration(tries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block))));
    }

    public static void onBiomeLoad(BiomeLoadingEvent event) {
        SPECULATIVE_FEATURES.forEach(feature -> feature.addToBiome(event));
    }

    public static void init() {
        SPECULATIVE_FEATURES.forEach(SpeculativeFeature::register);
    }
}
