package io.github.lgatodu47.speculative.common.block;

import io.github.lgatodu47.speculative.data.loot.ISelfDropBlockLoot;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class SpeculativeDirtBlock extends Block /*implements ISelfDropBlockLoot*/ {
    public SpeculativeDirtBlock() {
        super(Properties.of(Material.DIRT, MaterialColor.DIRT).strength(0.5F).sound(SoundType.GRAVEL));
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(ToolActions.HOE_TILL.equals(toolAction)) {
            return Blocks.FARMLAND.defaultBlockState();
        } else if(ToolActions.SHOVEL_FLATTEN.equals(toolAction)) {
            return Blocks.DIRT_PATH.defaultBlockState();
        }

        return null;
    }
}
