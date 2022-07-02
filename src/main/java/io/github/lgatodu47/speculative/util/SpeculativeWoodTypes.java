package io.github.lgatodu47.speculative.util;

import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import io.github.lgatodu47.speculative.mixin.TileEntityTypeAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntityType;

import java.util.HashSet;
import java.util.Set;

public class SpeculativeWoodTypes {
    public static final WoodType SPECULO_WOOD = create("speculo_wood");
    public static final WoodType TOURMALINE_WOOD = create("tourmaline_wood");

    private static WoodType create(String name) {
        return WoodType.create(Speculative.MODID.concat(":").concat(name));
    }

    public static void registerValidBlocks() {
        Set<Block> initial = ((TileEntityTypeAccessor) TileEntityType.SIGN).getValidBlocks();
        Set<Block> result = new HashSet<>(initial);
        result.add(SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN.get());
        result.add(SpeculativeBlocks.SPECULO_WOOD_WALL_SIGN.get());
        result.add(SpeculativeBlocks.TOURMALINE_WOOD_STANDING_SIGN.get());
        result.add(SpeculativeBlocks.TOURMALINE_WOOD_WALL_SIGN.get());
        ((TileEntityTypeAccessor) TileEntityType.SIGN).setValidBlocks(result);
    }
}
