package io.github.lgatodu47.speculative.util;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public class SpeculativeFillerBlockTypes {
    public static final RuleTest SPECULO_STONE = new LazyBlockMatchTest(SpeculativeBlocks.SPECULO_STONE);
}
