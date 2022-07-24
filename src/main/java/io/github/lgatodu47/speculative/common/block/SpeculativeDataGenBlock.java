package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.data.loot.IDataGenLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.function.Function;

public class SpeculativeDataGenBlock extends Block implements IDataGenLoot {
    protected Function<Block, LootTable.Builder> lootTableBuilder;

    public SpeculativeDataGenBlock(Properties properties) {
        super(properties);
    }

    public SpeculativeDataGenBlock(Properties properties, Function<Block, LootTable.Builder> lootTableBuilder) {
        super(properties);
        this.lootTableBuilder = lootTableBuilder;
    }

    public SpeculativeDataGenBlock withLootTable(Function<Block, LootTable.Builder> lootTableBuilder) {
        this.lootTableBuilder = lootTableBuilder;
        return this;
    }

    @Override
    public boolean isDataGenLootEnabled() {
        return this.lootTableBuilder != null && IDataGenLoot.super.isDataGenLootEnabled();
    }

    @Override
    public LootTable.Builder makeLootTable() {
        if(this.lootTableBuilder == null) return null;
        return this.lootTableBuilder.apply(this);
    }
}
