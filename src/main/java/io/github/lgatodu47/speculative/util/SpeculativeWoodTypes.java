package io.github.lgatodu47.speculative.util;

import com.google.common.collect.ImmutableSet;
import io.github.lgatodu47.speculative.Speculative;
import io.github.lgatodu47.speculative.common.init.SpeculativeBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static net.minecraft.world.level.block.state.properties.WoodType.register;

public class SpeculativeWoodTypes {
    private static final Set<Block> SIGN_BLOCKS = new HashSet<>();
    public static final Set<Block> SIGN_BLOCKS_VIEW = Collections.unmodifiableSet(SIGN_BLOCKS);

    public static final WoodType SPECULO_WOOD = register(create("speculo_wood"));
    public static final WoodType TOURMALINE_WOOD = register(create("tourmaline_wood"));

    private static WoodType create(String name) {
        return WoodType.create(Speculative.MODID.concat(":").concat(name));
    }

    public static Set<WoodType> values() {
        return ImmutableSet.of(SPECULO_WOOD, TOURMALINE_WOOD);
    }

    public static void registerValidBlocks() {
        SIGN_BLOCKS.add(SpeculativeBlocks.SPECULO_WOOD_STANDING_SIGN.get());
        SIGN_BLOCKS.add(SpeculativeBlocks.SPECULO_WOOD_WALL_SIGN.get());
        SIGN_BLOCKS.add(SpeculativeBlocks.TOURMALINE_WOOD_STANDING_SIGN.get());
        SIGN_BLOCKS.add(SpeculativeBlocks.TOURMALINE_WOOD_WALL_SIGN.get());
    }
}
