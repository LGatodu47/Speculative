package io.github.lgatodu47.speculative.common.world.gen;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class OverWorldSurfaceHelper {
    private static final SurfaceRules.RuleSource AIR = makeStateRule(Blocks.AIR);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final SurfaceRules.RuleSource WHITE_TERRACOTTA = makeStateRule(Blocks.WHITE_TERRACOTTA);
    private static final SurfaceRules.RuleSource ORANGE_TERRACOTTA = makeStateRule(Blocks.ORANGE_TERRACOTTA);
    private static final SurfaceRules.RuleSource TERRACOTTA = makeStateRule(Blocks.TERRACOTTA);
    private static final SurfaceRules.RuleSource RED_SAND = makeStateRule(Blocks.RED_SAND);
    private static final SurfaceRules.RuleSource RED_SANDSTONE = makeStateRule(Blocks.RED_SANDSTONE);
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE);
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource PODZOL = makeStateRule(Blocks.PODZOL);
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource MYCELIUM = makeStateRule(Blocks.MYCELIUM);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource CALCITE = makeStateRule(Blocks.CALCITE);
    private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource SANDSTONE = makeStateRule(Blocks.SANDSTONE);
    private static final SurfaceRules.RuleSource PACKED_ICE = makeStateRule(Blocks.PACKED_ICE);
    private static final SurfaceRules.RuleSource SNOW_BLOCK = makeStateRule(Blocks.SNOW_BLOCK);
    private static final SurfaceRules.RuleSource POWDER_SNOW = makeStateRule(Blocks.POWDER_SNOW);
    private static final SurfaceRules.RuleSource ICE = makeStateRule(Blocks.ICE);
    private static final SurfaceRules.RuleSource WATER = makeStateRule(Blocks.WATER);

    private static SurfaceRules.RuleSource makeStateRule(Block pBlock) {
        return SurfaceRules.state(pBlock.defaultBlockState());
    }

    // Basic overworld
    public static SurfaceRules.RuleSource overworld() {
        return overworldLike(true, false, true);
    }

    /**
     * Creates the surface rule for a world similar to the overworld.
     * Documented version of {@link net.minecraft.data.worldgen.SurfaceRuleData#overworldLike(boolean, boolean, boolean)}
     *
     * @param abovePreliminarySurface If it should be above preliminary surface (if the world has simply a fixed ground and sky).
     * @param bedrockRoof If it should have a bedrock roof (if caves generation).
     * @param bedrockFloor If it should have a floor (if fixed ground).
     * @return the Rule describing how to create the overworld surface.
     */
    private static SurfaceRules.RuleSource overworldLike(boolean abovePreliminarySurface, boolean bedrockRoof, boolean bedrockFloor) {
        SurfaceRules.ConditionSource y97 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(97), 2);
        SurfaceRules.ConditionSource y256 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(256), 0);
        SurfaceRules.ConditionSource y63m = SurfaceRules.yStartCheck(VerticalAnchor.absolute(63), -1);
        SurfaceRules.ConditionSource y74 = SurfaceRules.yStartCheck(VerticalAnchor.absolute(74), 1);
        SurfaceRules.ConditionSource y62 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(62), 0);
        SurfaceRules.ConditionSource y63 = SurfaceRules.yBlockCheck(VerticalAnchor.absolute(63), 0);
        SurfaceRules.ConditionSource water1 = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.ConditionSource waterSurface = SurfaceRules.waterBlockCheck(0, 0);
        SurfaceRules.ConditionSource water6m = SurfaceRules.waterStartCheck(-6, -1);
        SurfaceRules.ConditionSource hole = SurfaceRules.hole();
        SurfaceRules.ConditionSource frozenOcean = SurfaceRules.isBiome(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN);
        SurfaceRules.ConditionSource steep = SurfaceRules.steep();
        SurfaceRules.RuleSource grassRule = SurfaceRules.sequence(SurfaceRules.ifTrue(waterSurface, GRASS_BLOCK), DIRT);
        SurfaceRules.RuleSource desertRule = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, SANDSTONE), SAND);
        SurfaceRules.RuleSource gravelRule = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE), GRAVEL);
        SurfaceRules.ConditionSource sandyBiomes = SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.BEACH, Biomes.SNOWY_BEACH);
        SurfaceRules.ConditionSource desert = SurfaceRules.isBiome(Biomes.DESERT);

        SurfaceRules.RuleSource baseLayer = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.STONY_PEAKS),
                        SurfaceRules.sequence(
                                // Adds calcite on calcite noise
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.CALCITE, -0.0125D, 0.0125D),
                                        CALCITE),
                                STONE)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.STONY_SHORE),
                        // Adds gravel on gravel noise
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.GRAVEL, -0.05D, 0.05D),
                                        gravelRule),
                                STONE)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_HILLS),
                        // Stone in altitude
                        SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D),
                                STONE)),
                // Desert surface
                SurfaceRules.ifTrue(sandyBiomes,
                        desertRule),
                SurfaceRules.ifTrue(desert,
                        desertRule),
                // Dripstone Caves base
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.DRIPSTONE_CAVES),
                        STONE));

        // Small powder snow on surface
        SurfaceRules.RuleSource powderSnow = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.45D, 0.58D),
                SurfaceRules.ifTrue(waterSurface,
                        POWDER_SNOW));
        // Big powder snow on surface
        SurfaceRules.RuleSource powderSnowLarge = SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.6D),
                SurfaceRules.ifTrue(waterSurface,
                        POWDER_SNOW));

        SurfaceRules.RuleSource underGroundLayer = SurfaceRules.sequence(
                // Frozen peaks surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FROZEN_PEAKS),
                        SurfaceRules.sequence(
                                // Mountain edge
                                SurfaceRules.ifTrue(steep,
                                        PACKED_ICE),
                                // Packed ice noise
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, -0.5D, 0.2D),
                                        PACKED_ICE),
                                // Ice noise
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, -0.0625D, 0.025D),
                                        ICE),
                                // Snow on surface
                                SurfaceRules.ifTrue(waterSurface,
                                        SNOW_BLOCK))),
                // Snowy slopes surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SNOWY_SLOPES),
                        SurfaceRules.sequence(
                                // Mountain edge
                                SurfaceRules.ifTrue(steep,
                                        STONE),
                                // else powder snow noise
                                powderSnow,
                                // Else snow on surface
                                SurfaceRules.ifTrue(waterSurface, SNOW_BLOCK))),
                // Jagged peaks surface is just stone
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.JAGGED_PEAKS),
                        STONE),
                // Grove surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.GROVE),
                        SurfaceRules.sequence(
                                // Powder snow noise
                                powderSnow,
                                // Else dirt
                                DIRT)),
                // first layer
                baseLayer,
                // Windswept savanna
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA),
                        // Put stone in higher surface
                        SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D),
                                STONE)),
                // Windswept gravelly hills
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        SurfaceRules.sequence(
                                // Gravel above
                                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D),
                                        gravelRule),
                                // Then stone
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D),
                                        STONE),
                                // Then dirt
                                SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D),
                                        DIRT),
                                // otherwise gravel
                                gravelRule)),
                // Otherwise dirt everywhere
                DIRT);

        SurfaceRules.RuleSource nearSurfaceLayer = SurfaceRules.sequence(
                // Frozen peaks surface but with different noise
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FROZEN_PEAKS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(steep,
                                        PACKED_ICE),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.PACKED_ICE, 0.0D, 0.2D),
                                        PACKED_ICE),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0D, 0.025D),
                                        ICE),
                                SurfaceRules.ifTrue(waterSurface,
                                        SNOW_BLOCK))),
                // Snowy slopes surface but with large powder snow
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SNOWY_SLOPES),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(steep,
                                        STONE),
                                powderSnowLarge,
                                SurfaceRules.ifTrue(waterSurface,
                                        SNOW_BLOCK))),
                // Jagged peaks but with snow
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.JAGGED_PEAKS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(steep,
                                        STONE),
                                SurfaceRules.ifTrue(waterSurface,
                                        SNOW_BLOCK))),
                // Grove but with large powder snow
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.GROVE),
                        SurfaceRules.sequence(
                                powderSnowLarge,
                                SurfaceRules.ifTrue(waterSurface,
                                        SNOW_BLOCK))),
                // First layer
                baseLayer,
                // Windswept savanna coarse dirt
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_SAVANNA),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D),
                                        STONE),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(-0.5D),
                                        COARSE_DIRT))),
                // Windswept gravelly hills but with grass
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WINDSWEPT_GRAVELLY_HILLS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(2.0D),
                                        gravelRule),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.0D),
                                        STONE),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(-1.0D),
                                        grassRule),
                                gravelRule)),
                // Mega taiga surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(surfaceNoiseAbove(1.75D),
                                        COARSE_DIRT),
                                SurfaceRules.ifTrue(surfaceNoiseAbove(-0.95D), PODZOL))),
                // Ice spikes surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.ICE_SPIKES),
                        SurfaceRules.ifTrue(waterSurface,
                                SNOW_BLOCK)),
                // Mushroom fields surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.MUSHROOM_FIELDS),
                        MYCELIUM),
                // Else grass
                grassRule);

        SurfaceRules.ConditionSource surfaceNegative = SurfaceRules.noiseCondition(Noises.SURFACE, -0.909D, -0.5454D);
        SurfaceRules.ConditionSource surfaceMid = SurfaceRules.noiseCondition(Noises.SURFACE, -0.1818D, 0.1818D);
        SurfaceRules.ConditionSource surfacePositive = SurfaceRules.noiseCondition(Noises.SURFACE, 0.5454D, 0.909D);

        SurfaceRules.RuleSource result = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                // Wooded badlands ground
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WOODED_BADLANDS),
                                        SurfaceRules.ifTrue(y97,
                                                SurfaceRules.sequence(
                                                        SurfaceRules.ifTrue(surfaceNegative,
                                                                COARSE_DIRT),
                                                        SurfaceRules.ifTrue(surfaceMid,
                                                                COARSE_DIRT),
                                                        SurfaceRules.ifTrue(surfacePositive,
                                                                COARSE_DIRT),
                                                        grassRule))),
                                // Swamp water
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.SWAMP),
                                        SurfaceRules.ifTrue(y62,
                                                // under sea level
                                                SurfaceRules.ifTrue(SurfaceRules.not(y63),
                                                        SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.0D),
                                                                WATER)))))),
                // Badlands surface
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                        SurfaceRules.sequence(
                                                // Terracotta layers and red sandstone
                                                SurfaceRules.ifTrue(y256,
                                                        ORANGE_TERRACOTTA),
                                                SurfaceRules.ifTrue(y74,
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(surfaceNegative,
                                                                        TERRACOTTA),
                                                                SurfaceRules.ifTrue(surfaceMid,
                                                                        TERRACOTTA),
                                                                SurfaceRules.ifTrue(surfacePositive,
                                                                        TERRACOTTA),
                                                                SurfaceRules.bandlands())),
                                                SurfaceRules.ifTrue(water1,
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(SurfaceRules.ON_CEILING,
                                                                        RED_SANDSTONE),
                                                                RED_SAND)),
                                                SurfaceRules.ifTrue(SurfaceRules.not(hole),
                                                        ORANGE_TERRACOTTA),
                                                SurfaceRules.ifTrue(water6m,
                                                        WHITE_TERRACOTTA),
                                                gravelRule)),
                                // Orange terracotta (base) between y63 and y74
                                SurfaceRules.ifTrue(y63m,
                                        SurfaceRules.sequence(
                                                SurfaceRules.ifTrue(y63,
                                                        SurfaceRules.ifTrue(SurfaceRules.not(y74),
                                                                ORANGE_TERRACOTTA)),
                                                // otherwise base badlands
                                                SurfaceRules.bandlands())),
                                // underwater terracotta
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                        SurfaceRules.ifTrue(water6m,
                                                WHITE_TERRACOTTA)))),
                // Near surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(water1,
                                SurfaceRules.sequence(
                                        SurfaceRules.ifTrue(frozenOcean,
                                                SurfaceRules.ifTrue(hole,
                                                        SurfaceRules.sequence(
                                                                SurfaceRules.ifTrue(waterSurface,
                                                                        AIR),
                                                                SurfaceRules.ifTrue(SurfaceRules.temperature(),
                                                                        ICE),
                                                                WATER))),
                                        nearSurfaceLayer))),
                // Underground
                SurfaceRules.ifTrue(water6m,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                        SurfaceRules.ifTrue(frozenOcean,
                                                SurfaceRules.ifTrue(hole, WATER))),
                                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                                        underGroundLayer),
                                SurfaceRules.ifTrue(sandyBiomes,
                                        SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR,
                                                SANDSTONE)),
                                SurfaceRules.ifTrue(desert,
                                        SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR,
                                                SANDSTONE)))),
                // Floor
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FROZEN_PEAKS, Biomes.JAGGED_PEAKS),
                                        STONE),
                                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN),
                                        desertRule),
                                gravelRule)));

        ImmutableList.Builder<SurfaceRules.RuleSource> list = ImmutableList.builder();
        if (bedrockRoof) {
            list.add(SurfaceRules.ifTrue(SurfaceRules.not(SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())), BEDROCK));
        }

        if (bedrockFloor) {
            list.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK));
        }

        SurfaceRules.RuleSource aboveSurface = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), result);
        list.add(abovePreliminarySurface ? aboveSurface : result);
        list.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), DEEPSLATE));
        return SurfaceRules.sequence(list.build().toArray(SurfaceRules.RuleSource[]::new));
    }

    private static SurfaceRules.ConditionSource surfaceNoiseAbove(double p_194809_) {
        return SurfaceRules.noiseCondition(Noises.SURFACE, p_194809_ / 8.25D, Double.MAX_VALUE);
    }
}
