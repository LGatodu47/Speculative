package io.github.lgatodu47.speculative.util;

import com.mojang.serialization.Codec;
import io.github.lgatodu47.speculative.common.init.SpeculativeWorldGenRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTestType;

import java.util.Random;
import java.util.function.Supplier;

public class LazyBlockMatchTest extends RuleTest {
    public static final Codec<LazyBlockMatchTest> CODEC = Registry.BLOCK.byNameCodec().fieldOf("block").xmap(block -> new LazyBlockMatchTest(() -> block), (inst) -> inst.sup.get()).codec();

    private final Supplier<Block> sup;

    public LazyBlockMatchTest(Supplier<Block> sup) {
        this.sup = sup;
    }

    @Override
    public boolean test(BlockState pState, Random pRandom) {
        return pState.is(sup.get());
    }

    @Override
    protected RuleTestType<?> getType() {
        return SpeculativeWorldGenRegistries.RuleTestTypes.LAZY_BLOCK_MATCH.get();
    }
}
