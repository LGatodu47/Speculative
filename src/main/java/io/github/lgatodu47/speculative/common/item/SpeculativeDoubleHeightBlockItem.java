package io.github.lgatodu47.speculative.common.item;

import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class SpeculativeDoubleHeightBlockItem extends DoubleHighBlockItem {
    private final Supplier<Block> block;

    public SpeculativeDoubleHeightBlockItem(Supplier<Block> block, Properties props) {
        super(null, props);
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return block.get();
    }
}
