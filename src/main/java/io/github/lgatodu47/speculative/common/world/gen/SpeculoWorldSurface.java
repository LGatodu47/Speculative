package io.github.lgatodu47.speculative.common.world.gen;

import io.github.lgatodu47.speculative.common.init.SpeculativeBiomes;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class SpeculoWorldSurface {
    private static final RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    private static final RuleSource STONE = makeStateRule(SpeculativeBlocks.SPECULO_STONE.get());
    private static final RuleSource DIRT = makeStateRule(SpeculativeBlocks.SPECULO_DIRT.get());
    private static final RuleSource GRASS_BLOCK = makeStateRule(SpeculativeBlocks.SPECULO_GRASS.get());
    private static final RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);
    private static final RuleSource SAND = makeStateRule(SpeculativeBlocks.SPECULO_SAND.get());
    private static final RuleSource SANDSTONE = makeStateRule(SpeculativeBlocks.SPECULO_SANDSTONE.get());
    private static final RuleSource WATER = makeStateRule(Blocks.WATER);

    private static RuleSource makeStateRule(Block block) {
        return state(block.defaultBlockState());
    }

    public static RuleSource create() {
        ConditionSource water1 = waterBlockCheck(-1, 0);
        ConditionSource waterSurface = waterBlockCheck(0, 0);
        ConditionSource water6m = waterStartCheck(-6, -1);
        RuleSource grassRule = sequence(ifTrue(waterSurface, GRASS_BLOCK), DIRT);
        RuleSource desertRule = sequence(ifTrue(ON_CEILING, SANDSTONE), SAND);
        RuleSource gravelRule = sequence(ifTrue(ON_CEILING, STONE), GRAVEL);
        ConditionSource desert = isBiome(SpeculativeBiomes.SPECULO_DESERT.getKey());

        RuleSource baseLayer = sequence(
                SurfaceRules.ifTrue(desert,
                        desertRule));

        RuleSource underGroundLayer = sequence(
                baseLayer,
                DIRT
        );

        RuleSource nearSurfaceLayer = sequence(
                baseLayer,
                grassRule
        );

        RuleSource result = ifTrue(abovePreliminarySurface(), sequence(
                ifTrue(ON_FLOOR,
                        ifTrue(water1,
                                nearSurfaceLayer)),
                ifTrue(water6m,
                        sequence(
                                ifTrue(UNDER_FLOOR,
                                        underGroundLayer),
                                ifTrue(desert,
                                        ifTrue(VERY_DEEP_UNDER_FLOOR,
                                                SANDSTONE)))),
                ifTrue(ON_FLOOR,
                        gravelRule)));

        return sequence(
                ifTrue(SurfaceRules.verticalGradient("speculo_world:bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                result
        );
    }
}
