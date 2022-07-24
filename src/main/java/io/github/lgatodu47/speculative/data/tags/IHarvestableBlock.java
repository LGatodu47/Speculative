package io.github.lgatodu47.speculative.data.tags;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public interface IHarvestableBlock extends IDataGenTags<Block> {
    @Override
    default Set<TagKey<Block>> getTags() {
        Set<TagKey<Block>> set = new HashSet<>();
        if(getTierType() != null) {
            set.add(getTierType().tag());
        }
        if(getToolType() != null) {
            set.add(getToolType().tag());
        }
        return set;
    }

    @Nullable
    TierType getTierType();

    @Nullable
    ToolType getToolType();

    record TierType(TagKey<Block> tag) {
        public static final TierType STONE = new TierType(BlockTags.NEEDS_STONE_TOOL);
        public static final TierType IRON = new TierType(BlockTags.NEEDS_IRON_TOOL);
        public static final TierType DIAMOND = new TierType(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    record ToolType(TagKey<Block> tag) {
        public static final ToolType AXE = new ToolType(BlockTags.MINEABLE_WITH_AXE);
        public static final ToolType PICKAXE = new ToolType(BlockTags.MINEABLE_WITH_PICKAXE);
        public static final ToolType SHOVEL = new ToolType(BlockTags.MINEABLE_WITH_SHOVEL);
        public static final ToolType HOE = new ToolType(BlockTags.MINEABLE_WITH_HOE);
    }
}
