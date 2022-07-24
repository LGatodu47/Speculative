package io.github.lgatodu47.speculative.data.tags;

import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.data.DataGenerationContext;
import io.github.lgatodu47.speculative.util.SpeculativeUtils;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import static io.github.lgatodu47.speculative.common.init.SpeculativeBlocks.*;

public class SpeculativeBlockTagsProvider extends SpeculativeTagsProvider<Block> {
    public SpeculativeBlockTagsProvider(DataGenerationContext ctx) {
        super(ctx, Registry.BLOCK);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        addObjects(SpeculativeBlocks.Tags.SPECULO_LOGS,
                SPECULO_LOG,
                STRIPPED_SPECULO_LOG,
                SPECULO_BARK,
                STRIPPED_SPECULO_BARK
        );
        addObjects(SpeculativeBlocks.Tags.TOURMALINE_LOGS,
                TOURMALINE_LOG,
                STRIPPED_TOURMALINE_LOG,
                TOURMALINE_BARK,
                STRIPPED_TOURMALINE_BARK
        );
        tag(SpeculativeBlocks.Tags.CARVABLE_BLOCKS).addTags(Tags.Blocks.STONE, Tags.Blocks.SAND, BlockTags.DIRT, Tags.Blocks.SANDSTONE);

        addObjects(BlockTags.NEEDS_IRON_TOOL,
                SPECULO_BLOCK,
                RADIOACTIVE_DIAMOND_BLOCK,
                GRAPHITE_BLOCK
        );

        addObjects(BlockTags.MINEABLE_WITH_PICKAXE,
                SPECULO_BLOCK,
                RADIOACTIVE_DIAMOND_BLOCK,
                GRAPHITE_BLOCK,
                SPECULO_STONE,
                SPECULO_COBBLESTONE,
                SPECULO_COBBLESTONE_STAIRS,
                SPECULO_COBBLESTONE_SLAB,
                SPECULO_COBBLESTONE_WALL,
                SPECULO_SANDSTONE,
                CUT_SPECULO_SANDSTONE,
                CHISELED_SPECULO_SANDSTONE,
                SPECULO_SANDSTONE_STAIRS,
                SPECULO_SANDSTONE_SLAB,
                SPECULO_SANDSTONE_WALL,
                GREENSTONE_LANTERN
        );

        addObjects(BlockTags.MINEABLE_WITH_AXE,
                SPECULO_PLANKS,
                SPECULO_WOOD_STAIRS,
                SPECULO_WOOD_SLAB,
                SPECULO_WOOD_FENCE,
                SPECULO_WOOD_FENCE_GATE,
                SPECULO_WOOD_TRAPDOOR,
                SPECULO_WOOD_STANDING_SIGN,
                SPECULO_WOOD_WALL_SIGN,
                SPECULO_WOOD_PRESSURE_PLATE,
                SPECULO_WOOD_DOOR,
                SPECULO_WOOD_BUTTON,
                TOURMALINE_PLANKS,
                TOURMALINE_WOOD_STAIRS,
                TOURMALINE_WOOD_SLAB,
                TOURMALINE_WOOD_FENCE,
                TOURMALINE_WOOD_FENCE_GATE,
                TOURMALINE_WOOD_TRAPDOOR,
                TOURMALINE_WOOD_STANDING_SIGN,
                TOURMALINE_WOOD_WALL_SIGN,
                TOURMALINE_WOOD_PRESSURE_PLATE,
                TOURMALINE_WOOD_DOOR,
                TOURMALINE_WOOD_BUTTON
        ).addTags(SpeculativeBlocks.Tags.SPECULO_LOGS, SpeculativeBlocks.Tags.TOURMALINE_LOGS);

        addObjects(BlockTags.MINEABLE_WITH_SHOVEL,
                SPECULO_GRASS,
                SPECULO_DIRT,
                SPECULO_SAND
        );

        addObjects(BlockTags.MINEABLE_WITH_HOE,
                SPECULO_LEAVES,
                TOURMALINE_LEAVES
        );

        addObjects(BlockTags.PLANKS,
                SPECULO_PLANKS,
                TOURMALINE_PLANKS
        );
        addObjects(BlockTags.WOODEN_BUTTONS,
                SPECULO_WOOD_BUTTON,
                TOURMALINE_WOOD_BUTTON
        );
        addObjects(BlockTags.WOODEN_DOORS,
                SPECULO_WOOD_DOOR,
                TOURMALINE_WOOD_DOOR
        );
        addObjects(BlockTags.WOODEN_STAIRS,
                SPECULO_WOOD_STAIRS,
                TOURMALINE_WOOD_STAIRS
        );
        addObjects(BlockTags.WOODEN_SLABS,
                SPECULO_WOOD_SLAB,
                TOURMALINE_WOOD_SLAB
        );
        addObjects(BlockTags.WOODEN_FENCES,
                SPECULO_WOOD_FENCE,
                TOURMALINE_WOOD_FENCE
        );
        addObjects(BlockTags.FENCE_GATES,
                SPECULO_WOOD_FENCE_GATE,
                TOURMALINE_WOOD_FENCE_GATE
        );
        addObjects(BlockTags.WOODEN_PRESSURE_PLATES,
                SPECULO_WOOD_PRESSURE_PLATE,
                TOURMALINE_WOOD_PRESSURE_PLATE
        );
        addObjects(BlockTags.WOODEN_TRAPDOORS,
                SPECULO_WOOD_TRAPDOOR,
                TOURMALINE_WOOD_TRAPDOOR
        );
        addObjects(BlockTags.SAPLINGS,
                SPECULO_TREE_SAPLING,
                TOURMALINE_TREE_SAPLING
        );
        tag(BlockTags.LOGS_THAT_BURN).addTags(SpeculativeBlocks.Tags.SPECULO_LOGS, SpeculativeBlocks.Tags.TOURMALINE_LOGS);
        addObjects(BlockTags.SAND, SPECULO_SAND);
        addObjects(BlockTags.STAIRS,
                SPECULO_COBBLESTONE_STAIRS,
                SPECULO_SANDSTONE_STAIRS
        );
        addObjects(BlockTags.SLABS,
                SPECULO_COBBLESTONE_SLAB,
                SPECULO_SANDSTONE_SLAB
        );
        addObjects(BlockTags.WALLS,
                SPECULO_COBBLESTONE_WALL,
                SPECULO_SANDSTONE_WALL
        );
        addObjects(BlockTags.LEAVES,
                SPECULO_LEAVES,
                TOURMALINE_LEAVES
        );
        addObjects(BlockTags.SMALL_FLOWERS, SPECULO_FLOWER);
        addObjects(BlockTags.DIRT,
                SPECULO_GRASS,
                SPECULO_DIRT
        );
        SpeculativeUtils.filterEntries(BLOCKS, FlowerPotBlock.class).forEach(block -> tag(BlockTags.FLOWER_POTS).add(block));
        addObjects(BlockTags.VALID_SPAWN,
                SPECULO_GRASS,
                SPECULO_DIRT,
                SPECULO_SAND
        );
        SpeculativeUtils.filterEntries(BLOCKS, StandingSignBlock.class).forEach(block -> tag(BlockTags.STANDING_SIGNS).add(block));
        SpeculativeUtils.filterEntries(BLOCKS, WallSignBlock.class).forEach(block -> tag(BlockTags.WALL_SIGNS).add(block));
        addObjects(BlockTags.BEACON_BASE_BLOCKS,
                SPECULO_BLOCK,
                RADIOACTIVE_DIAMOND_BLOCK,
                URANIUM_235_BLOCK
        );
        SpeculativeUtils.filterEntries(BLOCKS, TorchBlock.class).forEach(block -> tag(BlockTags.WALL_POST_OVERRIDE).add(block));
        addObjects(BlockTags.ANIMALS_SPAWNABLE_ON, SPECULO_GRASS);

        addFromTag(Tags.Blocks.FENCES_WOODEN, BlockTags.WOODEN_FENCES);
        addObjects(Tags.Blocks.FENCE_GATES_WOODEN,
                SPECULO_WOOD_FENCE_GATE,
                TOURMALINE_WOOD_FENCE_GATE
        );
        addObjects(Tags.Blocks.COBBLESTONE_NORMAL, SPECULO_COBBLESTONE);
        addObjects(Tags.Blocks.ORES_IN_GROUND_STONE,
                SPECULO_ORE,
                GRAPHITE_ORE,
                URANIUM_ORE
        );
        addObjects(Tags.Blocks.ORES,
                SPECULO_ORE,
                GRAPHITE_ORE,
                URANIUM_ORE,
                SPECULO_WORLD_SPECULO_ORE,
                RADIOACTIVE_DIAMOND_ORE
        );
        addObjects(Tags.Blocks.SAND_COLORLESS, SPECULO_SAND);
        addObjects(Tags.Blocks.SANDSTONE, SPECULO_SANDSTONE);
        addObjects(Tags.Blocks.STORAGE_BLOCKS,
                SPECULO_BLOCK,
                RADIOACTIVE_DIAMOND_BLOCK,
                GRAPHITE_BLOCK,
                URANIUM_238_BLOCK,
                URANIUM_235_BLOCK,
                URANIUM_234_BLOCK
        );

        BLOCKS.getEntries()
                .stream()
                .filter(RegistryObject::isPresent)
                .map(RegistryObject::get)
                .filter(SpeculativeUtils.<IDataGenTags<Block>>classHack(IDataGenTags.class)::isInstance)
                .forEach(block -> ((IDataGenTags<Block>) block).getTags().stream().map(this::tag).forEach(builder -> builder.add(block)));
    }

    @Override
    public String getName() {
        return "Speculative Block Tags";
    }
}
