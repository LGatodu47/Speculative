package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.data.loot.IDataGenLoot;
import io.github.lgatodu47.speculative.data.tags.IHarvestableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SpeculativeOre extends OreBlock implements IDataGenLoot, IHarvestableBlock {
    public SpeculativeOre(float hardness, float resistance) {
        super(Properties.of(Material.STONE).strength(hardness, resistance).sound(SoundType.STONE).requiresCorrectToolForDrops());
    }

    protected UniformInt xpRange;

    public SpeculativeOre xp(int min, int max) {
        this.xpRange = UniformInt.of(min, max);
        return this;
    }

    protected Supplier<Item> itemDrop;
    protected NumberProvider countProvider;

    public SpeculativeOre drops(Supplier<Item> itemDrop) {
        return drops(itemDrop, ConstantValue.exactly(1));
    }

    public SpeculativeOre drops(Supplier<Item> itemDrop, NumberProvider countProvider) {
        this.itemDrop = itemDrop;
        this.countProvider = countProvider;
        return this;
    }

    protected ToolType harvestTool;
    protected TierType harvestTier;

    public SpeculativeOre harvests(@Nullable ToolType tool, @Nullable TierType tier) {
        this.harvestTool = tool;
        this.harvestTier = tier;
        return this;
    }

    @Override
    public SpeculativeOre disableDataGenLoot() {
        IDataGenLoot.super.disableDataGenLoot();
        return this;
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 && this.xpRange != null ? this.xpRange.sample(RANDOM) : 0;
    }

    @Override
    public LootTable.Builder makeLootTable() {
        return itemDrop == null ? Helper.createSingleItemTable(this) : Helper.createSingleItemTableWithSilkTouch(this, itemDrop.get(), countProvider);
    }

    @Override
    public TierType getTierType() {
        return harvestTier;
    }

    @Override
    public ToolType getToolType() {
        return harvestTool;
    }
}
