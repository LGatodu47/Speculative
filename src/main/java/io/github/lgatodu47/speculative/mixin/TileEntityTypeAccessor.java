package io.github.lgatodu47.speculative.mixin;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(TileEntityType.class)
public interface TileEntityTypeAccessor {
    @Accessor("validBlocks")
    void setValidBlocks(Set<Block> set);

    @Accessor("validBlocks")
    Set<Block> getValidBlocks();
}