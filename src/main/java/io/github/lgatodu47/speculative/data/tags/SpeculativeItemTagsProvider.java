package io.github.lgatodu47.speculative.data.tags;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.common.init.SpeculativeItems;
import io.github.lgatodu47.speculative.data.DataGenerationContext;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

import static io.github.lgatodu47.speculative.common.init.SpeculativeBlocks.getItem;
import static io.github.lgatodu47.speculative.common.init.SpeculativeItems.*;

public class SpeculativeItemTagsProvider extends SpeculativeTagsProvider<Item> {
    private final Function<TagKey<Block>, Tag.Builder> blockTags;

    public SpeculativeItemTagsProvider(DataGenerationContext ctx, SpeculativeBlockTagsProvider blockTags) {
        super(ctx, Registry.ITEM);
        this.blockTags = blockTags::getOrCreateRawBuilder;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        addObjects(SpeculativeItems.Tags.FOUND_IN_SPECULO_HOUSE,
                ORANGE_FRUIT,
                SPECULO_GEM,
                SPECULOOS,
                SPECULO_PORKCHOP,
                getItem(SpeculativeBlocks.SPECULO_LOG),
                RADIOACTIVE_DIAMOND
        );
        copy(SpeculativeBlocks.Tags.SPECULO_LOGS, SpeculativeItems.Tags.SPECULO_LOGS);
        copy(SpeculativeBlocks.Tags.TOURMALINE_LOGS, SpeculativeItems.Tags.TOURMALINE_LOGS);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.SAND, ItemTags.SAND);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(BlockTags.DIRT, ItemTags.DIRT);

        addObjects(ItemTags.BOATS,
                SPECULO_WOOD_BOAT,
                TOURMALINE_WOOD_BOAT
        );
        addObjects(ItemTags.SIGNS,
                SPECULO_WOOD_SIGN,
                TOURMALINE_WOOD_SIGN
        );
        addObjects(ItemTags.BEACON_PAYMENT_ITEMS,
                SPECULO_GEM,
                RADIOACTIVE_DIAMOND,
                URANINITE_INGOT
        );
        addObjects(ItemTags.STONE_TOOL_MATERIALS, getItem(SpeculativeBlocks.SPECULO_COBBLESTONE));
        addObjects(ItemTags.STONE_CRAFTING_MATERIALS, getItem(SpeculativeBlocks.SPECULO_COBBLESTONE));
        copy(Tags.Blocks.COBBLESTONE_NORMAL, Tags.Items.COBBLESTONE_NORMAL);
        copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);
        addObjects(Tags.Items.GEMS,
                SPECULO_GEM,
                RADIOACTIVE_DIAMOND
        );
        addObjects(Tags.Items.INGOTS,
                GRAPHITE_INGOT,
                URANINITE_INGOT
        );
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.SAND_COLORLESS, Tags.Items.SAND_COLORLESS);
        copy(Tags.Blocks.SANDSTONE, Tags.Items.SANDSTONE);
        copy(Tags.Blocks.STONE, Tags.Items.STONE);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        ITEMS.getEntries().stream()
                .filter(RegistryObject::isPresent)
                .map(RegistryObject::get)
                .filter(SpeculativeUtils.<IDataGenTags<Item>>classHack(IDataGenTags.class)::isInstance)
                .forEach(item -> ((IDataGenTags<Item>) item).getTags().stream().map(this::tag).forEach(builder -> builder.add(item)));
    }

    protected void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        this.blockTags.apply(blockTag).getEntries().forEach(getOrCreateRawBuilder(itemTag)::add);
    }

    @Override
    public String getName() {
        return "Speculative Item Tags";
    }
}
