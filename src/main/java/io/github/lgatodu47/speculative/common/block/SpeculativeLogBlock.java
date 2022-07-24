package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.Speculative;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SpeculativeLogBlock extends RotatedPillarBlock/* implements ISelfDropBlockLoot*/ {
    private final Supplier<Block> strippedVariant;

    public SpeculativeLogBlock(Supplier<Block> strippedVariant, Properties properties) {
        super(properties);
        this.strippedVariant = strippedVariant;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        Block stripped = strippedVariant.get();
        if(stripped == null) {
            Speculative.LOGGER.error(getRegistryName() + " is missing a stripped variant! Class 'SpeculativeLogBlock' shouldn't be used for a log that cannot be stripped!");
            return null;
        }
        if(ToolActions.AXE_STRIP.equals(toolAction)) {
            return stripped.defaultBlockState();
        }

        return null;
    }
}
