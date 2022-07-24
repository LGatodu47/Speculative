package io.github.lgatodu47.speculative.data.loot;

import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

import static io.github.lgatodu47.speculative.common.init.SpeculativeBlocks.*;

public class SpeculativeBlockLoot extends BlockLoot implements SpeculativeLootTableProvider.ILootTableAdder {
    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

    @Override
    public void addLootTables(SpeculativeLootTableProvider.ILootTableList list) {
        createLootTables();
        this.lootTables.forEach(list::add);

        BLOCKS.getEntries()
                .stream()
                .filter(RegistryObject::isPresent)
                .map(RegistryObject::get)
                .filter(IDataGenLoot.class::isInstance)
                .map(IDataGenLoot.class::cast)
                .filter(IDataGenLootSettings::isDataGenLootEnabled)
                .forEach(block -> {
                    LootTable.Builder builder = block.makeLootTable();
                    if(builder == null) builder = new LootTable.Builder();
                    list.add(block.getLootTable(), builder);
                });
    }

    private void createLootTables() {
        selfDrop(
                SPECULO_BLOCK,
                RADIOACTIVE_DIAMOND_BLOCK,
                GRAPHITE_BLOCK,
                SPECULO_FLOWER,
                SPECULO_TREE_SAPLING,
                TOURMALINE_TREE_SAPLING,
                SPECULO_LOG,
                STRIPPED_SPECULO_LOG,
                SPECULO_BARK,
                STRIPPED_SPECULO_BARK,
                SPECULO_PLANKS,
                SPECULO_WOOD_STAIRS,
                SPECULO_WOOD_SLAB,
                SPECULO_WOOD_FENCE,
                SPECULO_WOOD_FENCE_GATE,
                SPECULO_WOOD_TRAPDOOR,
                SPECULO_WOOD_PRESSURE_PLATE,
                SPECULO_WOOD_BUTTON,
                TOURMALINE_LOG,
                STRIPPED_TOURMALINE_LOG,
                TOURMALINE_BARK,
                STRIPPED_TOURMALINE_BARK,
                TOURMALINE_PLANKS,
                TOURMALINE_WOOD_STAIRS,
                TOURMALINE_WOOD_SLAB,
                TOURMALINE_WOOD_FENCE,
                TOURMALINE_WOOD_FENCE_GATE,
                TOURMALINE_WOOD_TRAPDOOR,
                TOURMALINE_WOOD_PRESSURE_PLATE,
                TOURMALINE_WOOD_BUTTON,
                SPECULO_DIRT,
                SPECULO_COBBLESTONE,
                SPECULO_COBBLESTONE_STAIRS,
                SPECULO_COBBLESTONE_SLAB,
                SPECULO_COBBLESTONE_WALL,
                SPECULO_SAND,
                SPECULO_SANDSTONE,
                CUT_SPECULO_SANDSTONE,
                CHISELED_SPECULO_SANDSTONE,
                SPECULO_SANDSTONE_STAIRS,
                SPECULO_SANDSTONE_SLAB,
                SPECULO_SANDSTONE_WALL,
                GREENSTONE_LANTERN
        );

        SpeculativeUtils.filterEntries(BLOCKS, FlowerPotBlock.class).forEach(this::dropPottedContents);

        add(SPECULO_LEAVES.get(), block -> createLeavesDrops(block, SPECULO_TREE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        add(TOURMALINE_LEAVES.get(), block -> createLeavesDrops(block, TOURMALINE_TREE_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        add(SPECULO_WOOD_STANDING_SIGN.get(), createSingleItemTable(SpeculativeItems.SPECULO_WOOD_SIGN.get()));
        add(TOURMALINE_WOOD_STANDING_SIGN.get(), createSingleItemTable(SpeculativeItems.TOURMALINE_WOOD_SIGN.get()));

        add(SPECULO_WOOD_DOOR.get(), BlockLoot::createDoorTable);
        add(TOURMALINE_WOOD_DOOR.get(), BlockLoot::createDoorTable);

        add(SPECULO_STONE.get(), block -> createSingleItemTableWithSilkTouch(block, SPECULO_COBBLESTONE.get()));

        add(URANIUM_ORE.get(), block -> new LootTable.Builder().withPool(new LootPool.Builder()
                .add(LootItem.lootTableItem(SpeculativeItems.URANIUM_238.get()).setWeight(100).setQuality(1))
                .add(LootItem.lootTableItem(SpeculativeItems.URANIUM_235.get()).setWeight(5).setQuality(10))
                .add(LootItem.lootTableItem(SpeculativeItems.URANIUM_234.get()).setWeight(1).setQuality(50))
        ));
    }

    @SafeVarargs
    private void selfDrop(RegistryObject<Block>... blocks) {
        for (RegistryObject<Block> block : blocks) {
            block.ifPresent(this::dropSelf);
        }
    }

    @Override
    protected void add(Block pBlock, LootTable.Builder pLootTableBuilder) {
        this.lootTables.put(pBlock.getLootTable(), pLootTableBuilder);
    }
}
